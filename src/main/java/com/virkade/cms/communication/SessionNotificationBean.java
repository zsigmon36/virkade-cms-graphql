package com.virkade.cms.communication;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.virkade.cms.model.PlaySession;

public class SessionNotificationBean {

	static List<PlaySession> firstWarningList = new Vector<>();
	static  List<PlaySession> secondWarningList = new Vector<>();
	static  List<PlaySession> startingList = new Vector<>();
	private static boolean started = false;
	private static ScheduledExecutorService threadExec = null;
	
	private SessionNotificationBean() {
	}
	
	public static void addSessionNotification(PlaySession session) {
		firstWarningList.add(session);
		secondWarningList.add(session);
		startingList.add(session);
		if (!started) {
			run();
		}
	}
	
	public static boolean existsSessionNotification(PlaySession session) {
		boolean result = firstWarningList.contains(session) && secondWarningList.contains(session) && startingList.contains(session);
		return result;
	}
	
	public static void removeSessionNotification(PlaySession session) {
		firstWarningList.remove(session);
		secondWarningList.remove(session);
		startingList.remove(session);
	}
	
	public static void clear() {
		firstWarningList.clear();
		secondWarningList.clear();
		startingList.clear();
		threadExec.shutdown();
		started = false;
	}
	
	private static void run(){
		started = true;
		if (threadExec == null) {
			threadExec = Executors.newScheduledThreadPool(1);
		}
		threadExec.scheduleAtFixedRate(new SessionNotificationThread(), 0, 1, TimeUnit.MINUTES);
	}
	
}
