package com.virkade.cms.communication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.virkade.cms.hibernate.dao.UserDAO;
import com.virkade.cms.model.PlaySession;
import com.virkade.cms.model.User;

public class SessionNotificationThread implements Runnable {
	private static final Logger LOG = Logger.getLogger(SessionNotificationThread.class);
	
	private static final long firstInterval = 1000*60*10;
	private static final long secondInterval = 1000*60*5;
	
	@Override
	public void run() {
		LOG.debug("running session notification thread");
		List<PlaySession> removeWarn1 = new ArrayList<>();
		List<PlaySession> removeWarn2 = new ArrayList<>();
		List<PlaySession> removeStartCheck = new ArrayList<>();
		
		for (PlaySession curSession : SessionNotificationBean.firstWarningList) {
			if (new Date().getTime() >= curSession.getStartDate().getTime() - firstInterval  ) {
				LOG.info("sending first notfication email for sessionId:"+curSession.getSessionId()+" to email:"+curSession.getEmailAddress());
				EmailUtil.sendSimpleMail(curSession.getEmailAddress(), "10 min warning", "less than 10 minutes until your play session begins");
				removeWarn1.add(curSession);
			}
		}
		SessionNotificationBean.firstWarningList.removeAll(removeWarn1);
		
		for (PlaySession curSession : SessionNotificationBean.secondWarningList) {
			if (new Date().getTime() >= curSession.getStartDate().getTime() - secondInterval) {
				LOG.info("sending second notfication email for sessionId:"+curSession.getSessionId()+" to email:"+curSession.getEmailAddress());
				EmailUtil.sendSimpleMail(curSession.getEmailAddress(), "5 min warning", "less than 5 minutes until your play session begins");
				removeWarn2.add(curSession);
			}
			
		}
		SessionNotificationBean.secondWarningList.removeAll(removeWarn2);
		
		for (PlaySession curSession : SessionNotificationBean.startingList) {
			if (new Date().getTime() >= curSession.getStartDate().getTime()) {
				User user = curSession.getUser();
				if (!user.isPlayedBefore()) {
					LOG.info("setting played before to true for username:"+curSession.getUsername());
					user.setPlayedBefore(true);
					UserDAO.createUpdate(user, true);
				}
				removeStartCheck.add(curSession);
			}
		}
		SessionNotificationBean.startingList.removeAll(removeStartCheck);
	}
}
