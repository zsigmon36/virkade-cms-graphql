package com.virkade.cms.communication;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class VirkadeEmailService {
	
	private Session session;
	private MimeMessage message;
	private static Properties props = new Properties();
	private static Authenticator auth;
	
	/**
	 * @return the session
	 */
	public Session getSession() {
		return session;
	}

	public VirkadeEmailService() throws IOException, MessagingException{
		if (props.isEmpty()) {
			InputStream stream = ClassLoader.getSystemResourceAsStream("email.cfg.properties");
			props.load(stream);
		}
		if (!props.isEmpty() && auth == null) {
			auth = getAuth(String.valueOf(props.get("mail.smtp.user")), String.valueOf(props.get("mail.smtp.password")));
		}
		Session session = Session.getInstance(props, auth);
		this.session = session;
		MimeMessage msg = new MimeMessage(session);
		msg.setSender(new InternetAddress(String.valueOf(props.get("mail.smtp.from"))));
		msg.setFrom(new InternetAddress(String.valueOf(props.get("mail.smtp.from"))));
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
