package com.virkade.cms.graphql;

import java.sql.Timestamp;
import java.util.List;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.virkade.cms.hibernate.dao.ActivityDAO;
import com.virkade.cms.hibernate.dao.ConstantsDAO;
import com.virkade.cms.hibernate.dao.LocationDAO;
import com.virkade.cms.hibernate.dao.SessionDAO;
import com.virkade.cms.hibernate.dao.StateDAO;
import com.virkade.cms.hibernate.dao.TypeDAO;
import com.virkade.cms.hibernate.dao.UserDAO;
import com.virkade.cms.model.Activity;
import com.virkade.cms.model.Comment;
import com.virkade.cms.model.Country;
import com.virkade.cms.model.InputUser;
import com.virkade.cms.model.Legal;
import com.virkade.cms.model.Location;
import com.virkade.cms.model.Phone;
import com.virkade.cms.model.PlaySession;
import com.virkade.cms.model.State;
import com.virkade.cms.model.Type;
import com.virkade.cms.model.User;

import graphql.schema.DataFetchingEnvironment;

public class Query implements GraphQLRootResolver {

	public List<User> allUsers() {
		return UserDAO.fetchAll();
	}

	public User getUserByUsername(String username) {
		User user = new User();
		user.setUsername(username);
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

	public List<PlaySession> getUserSessions(User user) throws Exception {
		List<PlaySession> playSessions = user.getSessions();
		return playSessions;
	}

	public List<PlaySession> getUserSessions(String username, Timestamp dateRequested) throws Exception {
		User user = UserDAO.getByUsername(username);
		List<PlaySession> playSessions = null;
		if (dateRequested != null) {
			playSessions = SessionDAO.getUserSessions(user, dateRequested);
		} else {
			playSessions = getUserSessions(user);
		}
		return playSessions;
	}

	
	public List<Country> getCountries() {
		return null;
	}

	public List<State> getAllStates(DataFetchingEnvironment env) {
		//AuthContext context = env.getContext();
		//User curSessionUser = context.getAuthUser();
		List<State> states = StateDAO.fetchAll();
		return states;
	}
	
	public List<PlaySession> getAvailableSessions(Timestamp dateRequested, String locationName, String activityName, DataFetchingEnvironment env) throws Exception {
		//AuthContext context = env.getContext();
		//User curSessionUser = context.getAuthUser();
		Location location = null;
		Activity activity = null;
		if (activityName == null || activityName == "") {
			activity = ActivityDAO.getDefault();
		} else {
			activity = ActivityDAO.fetchByName(activityName);
		}
		if (locationName == null || locationName == "") {
			location = LocationDAO.getDefault();
		} else {
			LocationDAO.fetchByName(locationName);
		}
		if (location == null || activity == null) {
			throw new Exception("location or activity not found,  if you are looking for the default then pass null values for location and activity");
		}
		List<PlaySession> sessions = SessionDAO.getAvailableSessions(dateRequested, location, activity);
		return sessions;
	}
	
	public List<PlaySession> getPendingSessions(Timestamp dateRequested, String locationName, String activityName, DataFetchingEnvironment env) throws Exception {
		//AuthContext context = env.getContext();
		//User curSessionUser = context.getAuthUser();
		Location location = null;
		Activity activity = null;
		if (activityName == null || activityName == "") {
			activity = ActivityDAO.getDefault();
		} else {
			activity = ActivityDAO.fetchByName(activityName);
		}
		if (locationName == null || locationName == "") {
			location = LocationDAO.getDefault();
		} else {
			location = LocationDAO.fetchByName(locationName);
		}
		if (location == null || activity == null) {
			throw new Exception("location or activity not found,  if you are looking for the default then pass null values for location and activity");
		}
		List<PlaySession> sessions = SessionDAO.getAllSessionsToday(location, activity);
		return sessions;
	}
	
	public boolean checkSession(DataFetchingEnvironment env) throws Exception {
		AuthContext context = env.getContext();
		User curUser = context.getAuthUser();
		if (curUser.getType().getTypeId() == TypeDAO.fetchByCode(ConstantsDAO.GUEST_CODE).getTypeId()) {
			return false;
		}
		return true;
	}


	public Type getTypeByCode(String code) {
		return null;
	}

	public List<Comment> getUserComments(String username) {
		return null;
	}

	public List<Legal> getLegalsByUser(InputUser user) {
		return null;
	}

	public List<Phone> getPhonesByUser(InputUser user) {
		return null;
	}

	public List<Phone> getPhonesByUsername(String username) {
		return null;
	}
}