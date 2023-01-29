package com.virkade.cms.communication;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class CommUtil {

	private static final Logger LOG = Logger.getLogger(CommUtil.class);

	private CommUtil() {
	}

	public static void sendSimpleMail(String to, String subject, String message) {

		Runnable emailThread = new Runnable() {
			public void run() {
				try {
					EmailService service = new GmailEmailServiceImpl();
					MimeMessage msg = service.getBaseJavaMailMessage();
					msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
					msg.setSubject(subject);
					msg.setText(message);
					service.send(msg);
				} catch (MessagingException | IOException | GeneralSecurityException e) {
					LOG.error("Could not send the email for "+to, e);
				}
			}
		};
		emailThread.run();
	}
	
	public static void sendSimpleSMS(String toPhoneNumber, String message) {

		Runnable emailThread = new Runnable() {
			public void run() {
				try {
					VirkadeSMSService service = new VirkadeSMSService();
					service.send(toPhoneNumber, message);
				} catch (Exception e) {
					LOG.error("Could not send the SMS for "+toPhoneNumber, e);
				}
			}
		};
		emailThread.run();
	}
}
