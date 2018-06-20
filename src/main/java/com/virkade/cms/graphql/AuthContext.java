/**
 * 
 */
package com.virkade.cms.graphql;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.virkade.cms.auth.ClientSessionTracker;
import com.virkade.cms.hibernate.dao.UserDAO;
import com.virkade.cms.model.User;

import graphql.servlet.GraphQLContext;

/**
 * @author SIGMON-MAIN
 *
 */
public class AuthContext extends GraphQLContext {

	private User currentAuthUser;
	
	public AuthContext(String userName, String token, Optional<HttpServletRequest> request, Optional<HttpServletResponse> response) {
		super(request, response);
		boolean validSession = ClientSessionTracker.isValidActiveClientSession(userName, token);
		if (validSession) {
			this.currentAuthUser = UserDAO.fetchByUserName(userName);
		} else {
			this.currentAuthUser = UserDAO.fetchByUserName(UserDAO.GUEST_USER_NAME);
		}
	
	}
	
	public User getAuthUser() {
		return currentAuthUser;
	}

}