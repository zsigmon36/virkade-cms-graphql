package com.virkade.cms.data.manipulator;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import com.virkade.cms.model.Activity;
import com.virkade.cms.model.Location;
import com.virkade.cms.model.OperatingHours;
import com.virkade.cms.model.PlaySession;

public class PlaySessionCalculator {

	private static final Logger LOG = Logger.getLogger(PlaySessionCalculator.class);
	private static SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor();

	{
		executor.setConcurrencyLimit(4);
	}

	public static List<PlaySession> getAvailableSessions(Timestamp curTime, Timestamp closeTime, List<PlaySession> currentSessions, OperatingHours opHours, Location location, Activity activity, int playSessionLength, int playSessionMinGap) throws InterruptedException {
		LOG.info("getting available sessions for location:"+location.getName()+ " and activity:"+activity.getName());
		List<PlaySession> availableSessions = new ArrayList<PlaySession>();
		List<PlaySessionGap> gaps = getCurrentGaps(curTime, currentSessions, opHours);

		// calculate gap options
		List<Future<?>> futures = new ArrayList<Future<?>>();
		for (PlaySessionGap gap : gaps) {
			
			PSGThread curThread = new PSGThread(gap, availableSessions, location, activity, playSessionLength, playSessionMinGap);
			Future<?> future = executor.submit(curThread);
			futures.add(future);
		}
		int loop = 0;
		while (!futures.isEmpty()) {
			if (loop > 0) {
				Thread.sleep(100);
			}
			if (++loop > 1000)
				break;
			List<Future<?>> shouldRemove = new ArrayList<Future<?>>();
			for (Future<?> future : futures) {
				if (future.isDone()) {
					shouldRemove.add(future);
				}
			}
			futures.removeAll(shouldRemove);
		}

		if (!futures.isEmpty()) {
			LOG.warn("There was an issue getting all the available times, returning what we have");
		}
		if (!availableSessions.isEmpty()) {
			availableSessions.sort(new PlaySessionComparator());
		}
		return availableSessions;
	}

	private static List<PlaySessionGap> getCurrentGaps(Timestamp curTime, List<PlaySession> currentSessions, OperatingHours opHours) {
		Timestamp previousSessionEnd = curTime;

		List<PlaySessionGap> playSessionGaps = new ArrayList<>();

		for (PlaySession curSession : currentSessions) {
			if (previousSessionEnd.after(curSession.getStartDate())) {
				LOG.info("The current time is in the middle of active session");
				previousSessionEnd = curSession.getEndDate();
				continue;
			}
			PlaySessionGap sessionGap = new PlaySessionGap((!previousSessionEnd.equals(curTime)), previousSessionEnd, curSession.getStartDate(), true);
			playSessionGaps.add(sessionGap);
			previousSessionEnd = curSession.getEndDate();
		}

		// previous end time is less than end of day
		if (previousSessionEnd.before(opHours.getEndAt())) {
			PlaySessionGap sessionGap = new PlaySessionGap((!previousSessionEnd.equals(curTime)), previousSessionEnd, opHours.getEndAt(), false);
			playSessionGaps.add(sessionGap);
		}
		return playSessionGaps;
	}
}
