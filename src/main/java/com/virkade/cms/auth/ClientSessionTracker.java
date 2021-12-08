package com.virkade.cms.auth;

import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.virkade.cms.communication.EmailUtil;
import com.virkade.cms.hibernate.dao.UserDAO;
import com.virkade.cms.model.User;

public class ClientSessionTracker {
	private static final Logger LOG = Logger.getLogger(ClientSessionTracker.class);
	private static final long RECOVERY_TOKEN_LIFE = 300000; // in millis, 5 min
	private static final long ACTIVE_TOKEN_LIFE = 3600000; // in millis, 1 hour
	private static Map<String, AuthToken> activeClientSessions;
	private static Map<String, AuthToken> recoveryClientSessions;

	private ClientSessionTracker() {
	}

	protected static Map<String, AuthToken> getActiveClientSessions() {
		if (activeClientSessions == null) {
			activeClientSessions = new HashMap<String, AuthToken>();
		}
		return activeClientSessions;
	}

	protected static Map<String, AuthToken> getRecoveryClientSessions() {
		if (recoveryClientSessions == null) {
			recoveryClientSessions = new HashMap<String, AuthToken>();
		}
		return recoveryClientSessions;
	}

	protected static void addNewSession(String key, AuthToken authToken, boolean isRecovery) {
		if (isRecovery) {
			getRecoveryClientSessions().put(key, authToken);
		} else {
			getActiveClientSessions().put(key, authToken);
		}
	}

	public static void purgeSession(String key, boolean isRecovery) throws Exception {
		if (isRecovery) {
			AuthToken recoverySession = getRecoveryClientSessions().get(key);
			if (recoverySession == null) {
				throw new Exception("no active session found with userName=" + key);
			}
			getRecoveryClientSessions().remove(key);
			LOG.info("active session removed for userName=" + key + " with authToken=" + recoverySession);
		} else {
			AuthToken activeSession = getActiveClientSessions().get(key);
			if (activeSession == null) {
				throw new Exception("no active session found with userName=" + key);
			}
			getActiveClientSessions().remove(key);
			LOG.info("active session removed for userName=" + key + " with authToken=" + activeSession);
		}
	}

	public static boolean isValidClientSession(String username, String token, boolean isRecovery) throws Exception {
		boolean results = false;
		if (isRecovery) {
			AuthToken recoverySession = getRecoveryClientSessions().get(token);
			if (recoverySession == null) {
				LOG.info("no recovery session found for passcode=" + token);
				return results;
			}
			LOG.info("recovery session found for passcode=" + token + " the authToken on record is=" + recoverySession);
			long curTime = Calendar.getInstance().getTimeInMillis();
			long createdTime = recoverySession.getCreatedDate().getTime();
			long diffMillis = curTime - createdTime;
			if (diffMillis > RECOVERY_TOKEN_LIFE) {
				LOG.info("recovery session expired for passcode=" + token);
				purgeSession(token, true);
				return results;
			}
			if (recoverySession.getUsername().equals(username)) {
				results = true;
			} else {
				LOG.info("active session found for passcode=" + token + " with authToken=" + recoverySession + " does not match the username=" + username + " given by the client request");
			}
		} else {
			AuthToken activeSession = getActiveClientSessions().get(username);
			if (activeSession == null) {
				LOG.info("no active session found for username=" + username);
				return results;
			}
			long curTime = Calendar.getInstance().getTimeInMillis();
			long createdTime = activeSession.getCreatedDate().getTime();
			long diffMillis = curTime - createdTime;
			if (diffMillis > ACTIVE_TOKEN_LIFE) {
				LOG.info("active session expired for username=" + username);
				purgeSession(username, false);
				return results;
			}
			LOG.debug("active session found for userName=" + username + " the authToken on record is=" + activeSession);
			if (activeSession.getToken().equals(token)) {
				results = true;
			} else {
				LOG.info("active session found for userName=" + username + " with authToken=" + activeSession + " does not match the authToken=" + token + " given by the client request");
			}
		}
		return results;
	}

	public static AuthToken recoverySignIn(AuthData authData) throws Exception {
		if (authData == null) {
			throw new Exception("AuthData cannot be null for this request");
		}
		if (authData.getUsername() == null || authData.getSecurityAnswer() == null) {
			throw new Exception("Username and Security Answer cannot be null for this request");
		}
		AuthToken authToken = createRecoverySession(authData.getUsername(), authData.getSecurityAnswer());
		if (authToken == null) {
			throw new Exception("could not create the recovery session");
		}
		return authToken;
	}

	public static AuthToken clientSignIn(AuthData authData, boolean validated) throws Exception {
		if (authData == null) {
			throw new Exception("AuthData cannot be null for this request");
		}
		AuthToken activeSessionToken = getActiveClientSessions().get(authData.getUsername());
		if (activeSessionToken != null) {
			LOG.debug("active session found for userName=" + authData.getUsername());
			purgeSession(activeSessionToken.getUsername(), false);
		} 
		LOG.info("creating fresh session");
		activeSessionToken = createActiveSession(authData.getUsername(), authData.getPassword(), validated);		
		if (activeSessionToken == null) {
			throw new Exception("could not create the client auth session");
		}
		return activeSessionToken;
	}

	private static AuthToken createActiveSession(String username, String password, boolean validated) throws Exception {
		User user = new User();
		user.setUsername(username);
		AuthToken authToken = null;
		user = UserDAO.fetch(user);
		if (user==null) {
			throw new Exception("no user found for username=" + username);
		} else if (validated && !user.isAccountVerified()) {
			throw new Exception("account is not yet verified, please check with support");
		}
		LOG.debug("Checking " + user.getUsername() + "'s encoded password in the database matches the client provided password");
		if (VirkadeEncryptor.isMatch(user.getPassword(), password)) {
			String token = createSessionToken();
			authToken = new AuthToken(username, token);
			addNewSession(user.getUsername(), authToken, false);
			LOG.debug("Session token created and added to system");
		} else {
			throw new Exception("Incorrect passord given for username=" + username);
		}
		return authToken;
	}

	private static AuthToken createRecoverySession(String username, String securityA) throws Exception {
		User user = new User();
		user.setUsername(username);
		AuthToken authToken = null;
		user = UserDAO.fetch(user);
		LOG.debug("Checking " + user.getSecurityAnswer() + "'s encoded security answer in the database matches the client provided security answer");
		if (VirkadeEncryptor.isMatch(user.getSecurityAnswer(), securityA)) {
			String token = String.valueOf((int) (Math.random() * 1000000));
			authToken = new AuthToken(username, token);
			addNewSession(token, authToken, true);
			EmailUtil.sendSimpleMail(user.getEmailAddress(), "Password Reset Passcode: " + token, "Enter this passcode with the new password in the client form \nPasscode will expire in 3 minutes \nPasscode: " + token);
			LOG.debug("recovery session created and added to system");
		} else {
			throw new Exception("Incorrect security answer given for userName=" + username);
		}

		return authToken;
	}

	private static String createSessionToken() throws NoSuchAlgorithmException {
		return VirkadeEncryptor.createRandomToken();
	}
}
