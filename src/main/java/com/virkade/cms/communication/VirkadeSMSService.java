package com.virkade.cms.communication;

import javax.mail.MessagingException;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import com.virkade.cms.PropsUtil;

public class VirkadeSMSService {

	private static String sid = String.valueOf(PropsUtil.get(PropsUtil.SMS_ACCOUNT_SID));
	private static String authToken = String.valueOf(PropsUtil.get(PropsUtil.SMS_AUTH_TOKEN));
	private static String fromPhoneNumber = String.valueOf(PropsUtil.get(PropsUtil.SMS_PHONE_FROM));
	private static String messagingServiceSid = String.valueOf(PropsUtil.get(PropsUtil.SMS_MSG_SERVICE_SID));

	VirkadeSMSService() {
		Twilio.init(sid, authToken);
	}

	public void send(String toPhoneNumber, String msg) throws MessagingException {
		PhoneNumber to = new PhoneNumber(toPhoneNumber);
		PhoneNumber from = new PhoneNumber(fromPhoneNumber);
		MessageCreator messageCreator = Message.creator(to, from, msg);
		if (messagingServiceSid != null && messagingServiceSid != ""){
			messageCreator = Message.creator(to, messagingServiceSid, msg);
		}
		Message message = messageCreator.create();
		if (message.getErrorCode() != null) {
			throw new MessagingException("error code: " + String.valueOf(message.getErrorCode()) + " error message:" + message.getErrorMessage());
		}
	}
}
