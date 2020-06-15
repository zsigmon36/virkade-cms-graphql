/**
 * 
 */
package com.virkade.cms.graphql;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.virkade.cms.auth.ClientSessionTracker;
import com.virkade.cms.hibernate.dao.ConstantsDAO;
import com.virkade.cms.hibernate.dao.UserDAO;
import com.virkade.cms.model.User;

import graphql.servlet.GraphQLContext;

/**
 * @author SIGMON-MAIN
 *
 */
public class AuthContext extends GraphQLContext {

	private static final Logger LOG = Logger.getLogger(AuthContext.class);
	private User currentAuthUser;
	
	public AuthContext(String username, String token, Optional<HttpServletRequest> request, Optional<HttpServletResponse> response) {
		super(request, response);
		boolean validSession = false;
		try {
			validSession = ClientSessionTracker.isValidClientSession(username, token, false);
		} catch (Exception e) {
			LOG.error(e);
		}
		if (validSession) {
			this.currentAuthUser = UserDAO.fetchByUsername(username);
		} else {
			this.currentAuthUser = UserDAO.fetchByUsername(ConstantsDAO.GUEST_USER_NAME);
		}
	
	}
	
	public User getAuthUser() {
		return currentAuthUser;
	}

}
