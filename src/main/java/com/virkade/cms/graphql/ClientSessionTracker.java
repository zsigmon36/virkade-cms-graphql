package com.virkade.cms.graphql;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.apache.log4j.Logger;

import com.virkade.cms.auth.AuthData;
import com.virkade.cms.auth.AuthToken;
import com.virkade.cms.hibernate.dao.UserDAO;
import com.virkade.cms.model.User;

public class ClientSessionTracker {
	private static final Logger LOG = Logger.getLogger(ClientSessionTracker.class); 
	private static Map<String, AuthToken> activeClientSessions;
	
	private ClientSessionTracker() {	
	}
	
	public static Map<String, AuthToken> getClientSessionTracker() {
		if (activeClientSessions == null) {
			activeClientSessions = new HashMap<String, AuthToken>();
		}
		return activeClientSessions;
	}
	
	public static void addNewActiveSession(String userName, AuthToken authToken) {
		getClientSessionTracker().put(userName, authToken);
	}
	
	public static void purgeActiveSession(String userName) throws Exception {
		AuthToken activeSession = getClientSessionTracker().get(userName);
		if (activeSession == null) {
			throw new Exception("no active session found with userName="+userName);
		}
		getClientSessionTracker().remove(userName);
		LOG.info("active session removed for userName"+userName+" with authToken="+activeSession);
		
	}
	
	public static boolean isValidActiveClientSession(String userName, String authToken) {
		boolean results = false;
		AuthToken activeSession = getClientSessionTracker().get(userName);
		if (activeSession == null) {
			LOG.info("no active session found for userName"+userName);
			return results;
		}
		LOG.info("active session found for userName"+userName+" the authToken on record is="+activeSession);
		if (activeSession.equals(authToken)) {
			results = true;
		} else {
			LOG.info("active session found for userName"+userName+" with authToken="+activeSession+" does not match the authToken="+authToken+" given by the client request");
		}
		return results;
	}
	
	public static AuthToken clientSignIn(AuthData authData) throws Exception {
		if (authData == null) {
			throw new Exception("AuthData cannot be null for this request");
		}
		AuthToken activeSessionToken = getClientSessionTracker().get(authData.getUserName());
		if (activeSessionToken == null) {
			AuthToken authToken = createSession(authData.getUserName(), authData.getPassword());
			if (authToken == null) {
				throw new Exception("could not create the client auth session");
			}
			return authToken;
		}else {
			LOG.info("active session found for userName "+authData.getUserName()+" the authToken on record is="+ activeSessionToken.toString());
			return activeSessionToken;
		}

	}
		
	private static AuthToken createSession(String userName, String password) {
		User user;
		AuthToken authToken = null;
		try {
			user = UserDAO.lookupUser(userName);
			if (user.getPassword().equals(password)) {
				String token = createSessionToken();
				authToken = new AuthToken(userName , token);
				addNewActiveSession(user.getUserName(), authToken);
			} else {
				throw new Exception("Incorrect passord given for userName="+userName);
			}
		} catch (Exception e) {
			LOG.error(e);
		}
		return authToken;
	}

	private static String createSessionToken() throws NoSuchAlgorithmException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(256);
		SecretKey rawKey = keyGenerator.generateKey();
		String key = rawKey.getEncoded().toString();
		return key;
	}
}
