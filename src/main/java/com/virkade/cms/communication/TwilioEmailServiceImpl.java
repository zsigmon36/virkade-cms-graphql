package com.virkade.cms.communication;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

import java.io.IOException;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.virkade.cms.PropsUtil;

public class TwilioEmailServiceImpl implements EmailService {

	private Session session;
	private MimeMessage message;
	private static Authenticator auth;

	/**
	 * @return the session
	 */
	public Session getSession() {
		return session;
	}

	public TwilioEmailServiceImpl() throws IOException, MessagingException {
		Session session = Session.getInstance(PropsUtil.getProps(), auth);
		this.session = session;
		MimeMessage msg = new MimeMessage(session);
		msg.setSender(new InternetAddress(PropsUtil.getString(PropsUtil.MAIL_SMTP_FROM)));
		msg.setFrom(new InternetAddress(PropsUtil.getString(PropsUtil.MAIL_SMTP_FROM)));
		this.message = msg;
	}

	public MimeMessage getBaseJavaMailMessage() {
		return this.message;
	}

	public void send(MimeMessage msg) throws MessagingException, IOException {

		Address[] fromAddress = msg.getFrom();
		Address[] toAddress = msg.getRecipients(RecipientType.TO);
		Email from = new Email(fromAddress[0].toString());
		Email to = new Email(toAddress[0].toString());
		Content content = new Content("text/plain", String.valueOf(msg.getContent()));
		Mail mail = new Mail(from, msg.getSubject(), to, content);

		SendGrid sg = new SendGrid(PropsUtil.getString(PropsUtil.EMAIL_API_KEY));
		Request request = new Request();
		request.setMethod(Method.POST);
		request.setEndpoint("mail/send");
		request.setBody(mail.build());
		Response response = sg.api(request);
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());
		System.out.println(response.getHeaders());
	}
}
