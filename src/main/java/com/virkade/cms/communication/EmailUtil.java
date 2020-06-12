package com.virkade.cms.communication;

import java.io.IOException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {

	private static final Logger LOG = Logger.getLogger(EmailUtil.class);

	private EmailUtil() {
	}

	public static void sendSimpleMail(String to, String subject, String message) {

		Runnable emailThread = new Runnable() {
			public void run() {
				try {
					VirkadeEmailService service = new VirkadeEmailService();
					MimeMessage msg = service.getJavaMailMessage();
					msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
					msg.setSubject(subject);
					msg.setText(message);
					service.send(msg);
				} catch (MessagingException | IOException e) {
					LOG.error("Could not send the email for "+to, e);
				}
			}
		};
		emailThread.run();
	}
}
