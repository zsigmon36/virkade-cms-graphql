package com.virkade.cms.data.manipulator;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import com.virkade.cms.PropsUtil;
import com.virkade.cms.model.Activity;
import com.virkade.cms.model.Location;
import com.virkade.cms.model.PlaySession;

/**
 * @author sigmon
 * 
 *         Desc: Play session generator thread, this thread is used to provide the available session for a given gap
 */
public class PSGThread implements Runnable {

	private static final Logger LOG = Logger.getLogger(PSGThread.class);
	private PlaySessionGap gap;
	private List<PlaySession> availableSessions;
	private Location location;
	private Activity activity;

	private Calendar cal = Calendar.getInstance();
	private static JSONObject FIRST_SESSION_OPTION_JSON = PropsUtil.getFirstPlaySessionOptionJson();
	private static final int SESSION_GAP_BUFFER = PropsUtil.getDefaultSessionBuffer();
	private static int SESSION_LENGTH = 30;
	private static int MINIMUM_SESSION_GAP = 5;

	public PSGThread(PlaySessionGap gap, List<PlaySession> availableSessions, Location location, Activity activity, int playSessionLength, int playSessionMinGap) {
		this.gap = gap;
		this.availableSessions = availableSessions;
		this.activity = activity;
		this.location = location;
		if (playSessionLength > 0) {
			SESSION_LENGTH = playSessionLength;
		} else {
			try {
				SESSION_LENGTH = FIRST_SESSION_OPTION_JSON.getInt(PropsUtil.LENGTH_KEY);
			} catch (JSONException e) {
				LOG.warn("Could not get the play session length from props, reverting to class default: " + SESSION_LENGTH);
			}
		}
		if (playSessionMinGap > 0) {
			MINIMUM_SESSION_GAP = playSessionMinGap;
		} else {
			try {
				MINIMUM_SESSION_GAP = FIRST_SESSION_OPTION_JSON.getInt(PropsUtil.GAP_KEY);
			} catch (JSONException e) {
				LOG.warn("Could not get the play session gap from props, reverting to class default: " + MINIMUM_SESSION_GAP);
			}
		}
	}

	@Override
	public void run() {
		cleanupGapTiming();
		LOG.debug(String.format("session gap cleanup done start=%s and end=%s", this.gap.getStartDate(), this.gap.getEndDate()));
		calculateSessionOptions();
		LOG.debug(String.format("session options calculated, %s available", this.availableSessions.size()));

		LOG.debug(String.format("PSGThread [ id:%s, name:%s ] - done calculating, exiting", Thread.currentThread().getId(), Thread.currentThread().getName()));

	}

	private void cleanupGapTiming() {
		// set start time to the configured acceptable round times
		cal.setTimeInMillis(this.gap.getStartDate().getTime());
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);

		// set the min gap rounded start time
		int startGapMins = cal.get(Calendar.MINUTE);
		if (!gap.isBookedBefore()) {
			startGapMins += SESSION_GAP_BUFFER;
		}
		int startRemainGapMins = startGapMins % MINIMUM_SESSION_GAP;
		int roundStartTime = startGapMins;
		if (startRemainGapMins != 0) {
			roundStartTime = (MINIMUM_SESSION_GAP - startRemainGapMins) + startGapMins;
		}
		cal.set(Calendar.MINUTE, roundStartTime);
		this.gap.setStartDate(new Timestamp(cal.getTimeInMillis()));

		// force end gap rounded, although this should really not have to be done ever,
		// if it does happen there is problem elsewhere
		cal.setTimeInMillis(this.gap.getEndDate().getTime());

		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		int endGapMins = cal.get(Calendar.MINUTE);
		int endRemainGapMins = endGapMins % MINIMUM_SESSION_GAP;
		int roundEndTime = endGapMins;
		if (endRemainGapMins != 0) {
			LOG.warn("there was an uneven gap end time, should not be the case.  Check the session creation procedure");
			roundEndTime = (MINIMUM_SESSION_GAP - endRemainGapMins) + endGapMins;
		}
		cal.set(Calendar.MINUTE, roundEndTime);
		this.gap.setEndDate(new Timestamp(cal.getTimeInMillis()));

	}

	private void calculateSessionOptions() {
		Timestamp gapStart = this.gap.getStartDate();
		Timestamp gapEnd = this.gap.getEndDate();

		long gapStartMillis = gapStart.getTime();
		long gapEndMillis = gapEnd.getTime();

		double timeDiffMin = Math.floor((((double) (gapEndMillis - gapStartMillis) / 1000) / 60));

		int maxSessionsPossible = (int) Math.floor(timeDiffMin / PSGThread.SESSION_LENGTH);
		LOG.debug(String.format("most sessions possible in this gap start=%s, end=%s is %s", gapStart, gapEnd, maxSessionsPossible));

		int sessionGapRemain = (int) (timeDiffMin % PSGThread.SESSION_LENGTH);
		int numOffset = (int) Math.floor(sessionGapRemain / PSGThread.MINIMUM_SESSION_GAP);
		LOG.debug(String.format("the session gap remainder is=%s, this will make the number of offsets=%s", sessionGapRemain, numOffset));

		do {
			int curOffset = PSGThread.MINIMUM_SESSION_GAP * numOffset;
			for (int i = 0; i < maxSessionsPossible; i++) {
				PlaySession session = new PlaySession();
				session.setActivity(this.activity);
				session.setLocation(this.location);

				int offsetMillis = (curOffset * 60 * 1000);
				long curSessionStartMilllis = gapStartMillis + ((i * PSGThread.SESSION_LENGTH) * 60 * 1000) + offsetMillis;
				session.setStartDate(new Timestamp(curSessionStartMilllis));

				long curSessionEndMilllis = curSessionStartMilllis + (PSGThread.SESSION_LENGTH * 60 * 1000);
				session.setEndDate(new Timestamp(curSessionEndMilllis));
				this.availableSessions.add(session);
			}
		} while (numOffset-- > 0);

	}
}
