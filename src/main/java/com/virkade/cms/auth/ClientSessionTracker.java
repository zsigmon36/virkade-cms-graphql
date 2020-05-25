package com.virkade.cms.auth;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.virkade.cms.hibernate.dao.UserDAO;
import com.virkade.cms.model.User;

public class ClientSessionTracker {
	private static final Logger LOG = Logger.getLogger(ClientSessionTracker.class); 
	private static Map<String, AuthToken> activeClientSessions;
	
	private ClientSessionTracker() {	
	}
	
	protected static Map<String, AuthToken> getClientSessionTracker() {
		if (activeClientSessions == null) {
			activeClientSessions = new HashMap<String, AuthToken>();
		}
		return activeClientSessions;
	}
	
	public static void addNewActiveSession(String username, AuthToken authToken) {
		getClientSessionTracker().put(username, authToken);
	}
	
	public static void purgeActiveSession(String username) throws Exception {
		AuthToken activeSession = getClientSessionTracker().get(username);
		if (activeSession == null) {
			throw new Exception("no active session found with userName="+username);
		}
		getClientSessionTracker().remove(username);
		LOG.info("active session removed for userName="+username+" with authToken="+activeSession);
		
	}
	
	public static boolean isValidActiveClientSession(String username, String authToken) {
		boolean results = false;
		AuthToken activeSession = getClientSessionTracker().get(username);
		if (activeSession == null) {
			LOG.info("no active session found for userName="+username);
			return results;
		}
		LOG.info("active session found for userName="+username+" the authToken on record is="+activeSession);
		if (activeSession.getToken().equals(authToken)) {
			results = true;
		} else {
			LOG.info("active session found for userName="+username+" with authToken="+activeSession+" does not match the authToken="+authToken+" given by the client request");
		}
		return results;
	}
	
	public static AuthToken clientSignIn(AuthData authData) throws Exception {
		if (authData == null) {
			throw new Exception("AuthData cannot be null for this request");
		}
		AuthToken activeSessionToken = getClientSessionTracker().get(authData.getUsername());
		if (activeSessionToken == null) {
			AuthToken authToken = createSession(authData.getUsername(), authData.getPassword());
			if (authToken == null) {
				throw new Exception("could not create the client auth session");
			}
			return authToken;
		}else {
			LOG.info("active session found for userName="+authData.getUsername()+" the authToken on record is="+ activeSessionToken.getToken());
			return activeSessionToken;
		}

	}
		
	private static AuthToken createSession(String username, String password) {
		User user = new User();
		user.setUsername(username);
		AuthToken authToken = null;
		try {
			user = UserDAO.fetch(user);
			LOG.debug("Checking "+user.getUsername()+"'s encoded password in the database matches the client provided password");
			if (VirkadeEncryptor.isMatch(user.getPassword(), password)) {
				String token = createSessionToken();
				authToken = new AuthToken(username , token);
				addNewActiveSession(user.getUsername(), authToken);
				LOG.debug("Session token created and added to system");
			} else {
				throw new Exception("Incorrect passord given for userName="+username);
			}
		} catch (Exception e) {
			LOG.error("Counld not create the session for the userId="+user.getUserId(),e);
		}
		return authToken;
	}

	private static String createSessionToken() throws NoSuchAlgorithmException {
		return VirkadeEncryptor.createRandomToken();
	}
}
