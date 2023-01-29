package com.virkade.cms.communication;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.codec.binary.Base64;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.virkade.cms.PropsUtil;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

public class GmailEmailServiceImpl implements EmailService{

  private static final String APPLICATION_NAME = "VirKade-CMS-GraphQL";
  private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
  private static final String TOKENS_DIRECTORY_PATH = "tokens";
  private static final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_SEND);
  private static Credential creds;
  private Session session;
  private MimeMessage message;
  private static Gmail service;

	public GmailEmailServiceImpl() throws IOException, MessagingException, GeneralSecurityException{
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		if (creds == null) {
			creds = getCreds(HTTP_TRANSPORT);
		}
    	service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, creds)
        	.setApplicationName(APPLICATION_NAME)
        	.build();

		Session session = Session.getInstance(PropsUtil.getProps(), null);
		this.session = session;
		MimeMessage msg = new MimeMessage(session);
		msg.setSender(new InternetAddress(String.valueOf(PropsUtil.get(PropsUtil.MAIL_SMTP_FROM))));
		msg.setFrom(new InternetAddress(String.valueOf(PropsUtil.get(PropsUtil.MAIL_SMTP_FROM))));
		this.message = msg;
	}

	/**
	 * @return the session
	 */
	public Session getSession() {
		return session;
	}
	
	public MimeMessage getBaseJavaMailMessage() {
	    return this.message;
	}

	public void send(MimeMessage msg) throws MessagingException, IOException {
		// Encode and wrap the MIME message into a gmail message
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		msg.writeTo(buffer);
		byte[] rawMessageBytes = buffer.toByteArray();
		String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);
		Message message = new Message();
		message.setRaw(encodedEmail);
		message = service.users().messages().send("me", message).execute();
	  }
	
	private static Credential getCreds(NetHttpTransport HTTP_TRANSPORT) throws IOException {
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(PropsUtil.getDefaultGoogleSecret()));
		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
			HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
			.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
			.setAccessType("offline")
			.build();
		LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
		Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
		//returns an authorized Credential object.
		return credential;
	}	
}
