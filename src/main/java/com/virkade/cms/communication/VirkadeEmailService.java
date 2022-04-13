package com.virkade.cms.communication;

import java.io.IOException;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.virkade.cms.PropsUtil;

public class VirkadeEmailService {
	
	private Session session;
	private MimeMessage message;
	private static Authenticator auth;
	
	/**
	 * @return the session
	 */
	public Session getSession() {
		return session;
	}

	public VirkadeEmailService() throws IOException, MessagingException{
		if (auth == null) {
			auth = getAuth(String.valueOf(PropsUtil.get(PropsUtil.MAIL_SMTP_USER)), String.valueOf(PropsUtil.get(PropsUtil.MAIL_SMTP_PASSWORD)));
		}
		Session session = Session.getInstance(PropsUtil.getProps(), auth);
		this.session = session;
		MimeMessage msg = new MimeMessage(session);
		msg.setSender(new InternetAddress(String.valueOf(PropsUtil.get(PropsUtil.MAIL_SMTP_FROM))));
		msg.setFrom(new InternetAddress(String.valueOf(PropsUtil.get(PropsUtil.MAIL_SMTP_FROM))));
		this.message = msg;
	}
	
	public MimeMessage getJavaMailMessage() {
	    return this.message;
	}

	public void send(MimeMessage msg) throws MessagingException {
		Transport.send(msg);
	}
	
	private static Authenticator getAuth(String user, String password) {
		return new Authenticator() {
			//override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		};
	}	
}
