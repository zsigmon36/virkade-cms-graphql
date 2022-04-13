package com.virkade.cms.communication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.virkade.cms.hibernate.dao.ConstantsDAO;
import com.virkade.cms.hibernate.dao.UserDAO;
import com.virkade.cms.model.Phone;
import com.virkade.cms.model.PlaySession;
import com.virkade.cms.model.User;

public class SessionNotificationThread implements Runnable {
	private static final Logger LOG = Logger.getLogger(SessionNotificationThread.class);

	private static final int firstIntervalMin = 10;
	private static final int secondIntervalMin = 5;
	private static final long firstIntervalMillis = 1000 * 60 * firstIntervalMin;
	private static final long secondIntervalMillis = 1000 * 60 * secondIntervalMin;

	@Override
	public void run() {
		LOG.debug("running session notification thread");
		List<PlaySession> removeWarn1 = new ArrayList<>();
		List<PlaySession> removeWarn2 = new ArrayList<>();
		List<PlaySession> removeStartCheck = new ArrayList<>();

		for (PlaySession curSession : SessionNotificationBean.firstWarningList) {
			if (new Date().getTime() >= curSession.getStartDate().getTime() - firstIntervalMillis) {
				LOG.info("sending first notfication email for sessionId:" + curSession.getSessionId() + " to email:" + curSession.getEmailAddress());
				CommUtil.sendSimpleMail(curSession.getEmailAddress(), String.format("%s min warning", firstIntervalMin),
						String.format("less than %s minutes until your play session begins", firstIntervalMin));
				for (Phone number : curSession.getUser().getPhoneNumbers()) {
					if (number.getType().getCode().equals(ConstantsDAO.MOBILE_PHONE)) {
						CommUtil.sendSimpleSMS(number.getCountryCode() + number.getNumber(),
								String.format("%s min warning \nless than %s minutes until your play session begins", firstIntervalMin, firstIntervalMin));
					}
				}
				removeWarn1.add(curSession);
			}
		}
		SessionNotificationBean.firstWarningList.removeAll(removeWarn1);

		for (PlaySession curSession : SessionNotificationBean.secondWarningList) {
			if (new Date().getTime() >= curSession.getStartDate().getTime() - secondIntervalMillis) {
				LOG.info("sending second notfication email for sessionId:" + curSession.getSessionId() + " to email:" + curSession.getEmailAddress());
				CommUtil.sendSimpleMail(curSession.getEmailAddress(), String.format("%s min warning", secondIntervalMin),
						String.format("less than %s minutes until your play session begins", secondIntervalMin));
				for (Phone number : curSession.getUser().getPhoneNumbers()) {
					if (number.getType().getCode().equals(ConstantsDAO.MOBILE_PHONE)) {
						CommUtil.sendSimpleSMS(number.getCountryCode() + number.getNumber(),
								String.format("%s min warning \nless than %s minutes until your play session begins", secondIntervalMin, secondIntervalMin));
					}
				}
				removeWarn2.add(curSession);
			}

		}
		SessionNotificationBean.secondWarningList.removeAll(removeWarn2);

		for (PlaySession curSession : SessionNotificationBean.startingList) {
			if (new Date().getTime() >= curSession.getStartDate().getTime()) {
				User user = curSession.getUser();
				if (!user.isPlayedBefore()) {
					LOG.info("setting played before to true for username:" + curSession.getUsername());
					user.setPlayedBefore(true);
					UserDAO.createUpdate(user, true);
				}
				removeStartCheck.add(curSession);
			}
		}
		SessionNotificationBean.startingList.removeAll(removeStartCheck);
	}
}
