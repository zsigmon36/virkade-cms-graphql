package com.virkade.cms.communication;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

public interface EmailService {
	
	/**
	 * @return the session
	 */
	public Session getSession();
	
	public MimeMessage getBaseJavaMailMessage();
	
    public void send(MimeMessage msg) throws MessagingException, IOException;
}
