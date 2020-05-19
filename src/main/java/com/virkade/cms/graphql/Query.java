package com.virkade.cms.graphql;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.virkade.cms.hibernate.dao.PlaySessionDAO;
import com.virkade.cms.hibernate.dao.StateDAO;
import com.virkade.cms.hibernate.dao.UserDAO;
import com.virkade.cms.hibernate.utilities.HibernateUtilities;
import com.virkade.cms.model.Comment;
import com.virkade.cms.model.Country;
import com.virkade.cms.model.InputUser;
import com.virkade.cms.model.Legal;
import com.virkade.cms.model.Phone;
import com.virkade.cms.model.PlaySession;
import com.virkade.cms.model.State;
import com.virkade.cms.model.Type;
import com.virkade.cms.model.User;

public class Query implements GraphQLRootResolver {

	public List<User> allUsers() {
		return UserDAO.fetchAll();
	}

	public User getUserByUserName(String userName) {
		User user = new User();
		user.setUserName(userName);
		return UserDAO.fetch(user);
	}

	public List<User> getUsersByEmailAddress(String emailAddress) {
		return UserDAO.fetchByEmail(emailAddress);
	}

	public User getUserById(Long userId) {
		User user = new User();
		user.setUserId(userId);
		user = UserDAO.fetch(user);
		return user;
	}

	public List<PlaySession> getUserSessions(String username) throws Exception {
		List<PlaySession> playSessions = PlaySessionDAO.fetchUserSessions(username);
		return playSessions;
	}

	public List<Country> getCountries() {
		return null;
	}

	public List<State> getStates() {
		List<State> states = StateDAO.fetchAll();
		return states;
	}

	public Type getTypeByCode(String code) {
		return null;
	}

	public List<Comment> getUserComments(String userName) {
		return null;
	}

	public List<Legal> getLegalsByUser(InputUser user) {
		return null;
	}

	public List<Phone> getPhonesByUser(InputUser user) {
		return null;
	}

	public List<Phone> getPhonesByUserName(String userName) {
		return null;
	}
}