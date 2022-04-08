package com.virkade.cms.graphql;

import java.nio.file.AccessDeniedException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.virkade.cms.PropsUtil;
import com.virkade.cms.auth.AuthData;
import com.virkade.cms.auth.PermissionType;
import com.virkade.cms.hibernate.dao.ActivityDAO;
import com.virkade.cms.hibernate.dao.ConstantsDAO;
import com.virkade.cms.hibernate.dao.DocumentDAO;
import com.virkade.cms.hibernate.dao.LocationDAO;
import com.virkade.cms.hibernate.dao.PlaySessionDAO;
import com.virkade.cms.hibernate.dao.StateDAO;
import com.virkade.cms.hibernate.dao.TypeDAO;
import com.virkade.cms.hibernate.dao.UserDAO;
import com.virkade.cms.model.Activity;
import com.virkade.cms.model.Country;
import com.virkade.cms.model.Document;
import com.virkade.cms.model.InputAddress;
import com.virkade.cms.model.Location;
import com.virkade.cms.model.PlaySession;
import com.virkade.cms.model.State;
import com.virkade.cms.model.User;
import com.virkade.cms.model.search.UserSearchFilter;

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
			playSessions = PlaySessionDAO.getUserSessions(user, dateRequested);
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
	
	public Document getLatestDocumentByType(String typeCode, DataFetchingEnvironment env) {
		//AuthContext context = env.getContext();
		//User curSessionUser = context.getAuthUser();
		Document doc = DocumentDAO.fetchLatestDocumentByType(typeCode);
		return doc;
	}
	
	public List<Activity> getAllActivities(DataFetchingEnvironment env) {
		//AuthContext context = env.getContext();
		//User curSessionUser = context.getAuthUser();
		List<Activity> activity = ActivityDAO.fetchAll();
		return activity;
	}
	
	public List<Location> getAllLocations(DataFetchingEnvironment env) {
		//AuthContext context = env.getContext();
		//User curSessionUser = context.getAuthUser();
		List<Location> location = LocationDAO.fetchAll();
		return location;
	}
	
	public Location getLocation(long locationId, DataFetchingEnvironment env) {
		//AuthContext context = env.getContext();
		//User curSessionUser = context.getAuthUser();
		Location location = LocationDAO.fetchById(locationId);
		return location;
	}
	
	public Activity getActivity(long activityId, DataFetchingEnvironment env) {
		//AuthContext context = env.getContext();
		//User curSessionUser = context.getAuthUser();
		Activity activity = ActivityDAO.fetchById(activityId);
		return activity;
	}
	
	public List<PlaySession> getDefaultAvailableSessions(Timestamp dateRequested, Long locationId, Long activityId, DataFetchingEnvironment env) throws Exception {
		return getAvailableSessions(dateRequested, locationId, activityId, env);
	}
	
	public List<PlaySession> getAvailableSessions(Timestamp dateRequested, Long locationId, Long activityId, DataFetchingEnvironment env) throws Exception {
		//AuthContext context = env.getContext();
		//User curSessionUser = context.getAuthUser();
		Location location = null;
		Activity activity = null;
		if (activityId == null || activityId == 0) {
			activity = ActivityDAO.getDefault();
		} else {
			activity = ActivityDAO.fetchById(activityId);
		}
		if (locationId == null || locationId == 0) {
			location = LocationDAO.getDefault();
		} else {
			location = LocationDAO.fetchById(locationId);
		}
		if (location == null || activity == null) {
			throw new Exception("location or activity not found,  if you are looking for the default then pass null values for location and activity");
		}
		List<PlaySession> sessions = new ArrayList<>();
		for ( int i = 0; i < PropsUtil.getPlaySessionOptionsJsonArray().length(); i++ ) {
			JSONObject curObj = PropsUtil.getPlaySessionOptionsJsonArray().getJSONObject(i);
			 sessions.addAll(PlaySessionDAO.getAvailableSessions(dateRequested, location, activity, curObj.getInt(PropsUtil.LENGTH_KEY), curObj.getInt(PropsUtil.GAP_KEY)));
		}
		return sessions;
	}
	
	public List<PlaySession> getPendingSessions(Timestamp dateRequested, Long locationId, Long activityId, Boolean payed, DataFetchingEnvironment env) throws Exception {
		//AuthContext context = env.getContext();
		//User curSessionUser = context.getAuthUser();
		Location location = null;
		Activity activity = null;
		if (activityId == null || activityId == 0) {
			activity = ActivityDAO.getDefault();
		} else {
			activity = ActivityDAO.fetchById(activityId);
		}
		if (locationId == null || locationId == 0) {
			location = LocationDAO.getDefault();
		} else {
			location = LocationDAO.fetchById(locationId);
		}
		if (location == null || activity == null) {
			throw new Exception("location or activity not found,  if you are looking for the default then pass null values for location and activity");
		}
		List<PlaySession> sessions = PlaySessionDAO.getAllSessionsToday(location, activity, payed);
		return sessions;
	}
	
	public List<PlaySession> getAllSessions(Timestamp dateRequested, Long locationId, Long activityId, Boolean payed, DataFetchingEnvironment env) throws Exception {
		//AuthContext context = env.getContext();
		//User curSessionUser = context.getAuthUser();
		Location location = null;
		Activity activity = null;
		if (activityId == null || activityId == 0) {
			activity = ActivityDAO.getDefault();
		} else {
			activity = ActivityDAO.fetchById(activityId);
		}
		if (locationId == null || locationId == 0) {
			location = LocationDAO.getDefault();
		} else {
			location = LocationDAO.fetchById(locationId);
		}
		if (location == null || activity == null) {
			throw new Exception("location or activity not found,  if you are looking for the default then pass null values for location and activity");
		}
		//default 1 day prior
		if (dateRequested == null) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
			dateRequested = new Timestamp(cal.getTimeInMillis());
		}
		
		List<PlaySession> sessions = PlaySessionDAO.getAllSessions(dateRequested, location, activity, payed);
		return sessions;
	}
	
	public List<PlaySession> getAllUserSessions(Timestamp dateRequested, Long userId, Long locationId, Long activityId, Boolean payed, DataFetchingEnvironment env) throws Exception {
		Location location = null;
		Activity activity = null;
		User user = null;
		if (userId == null || userId <= 0) {
			throw new Exception("user id must be valid to get the sessions for that user");
		}
		user = UserDAO.getById(userId);
		
		if (activityId == null || activityId == 0) {
			activity = ActivityDAO.getDefault();
		} else {
			activity = ActivityDAO.fetchById(activityId);
		}
		if (locationId == null || locationId == 0) {
			location = LocationDAO.getDefault();
		} else {
			location = LocationDAO.fetchById(locationId);
		}
		if (location == null || activity == null) {
			throw new Exception("location or activity not found,  if you are looking for the default then pass null values for location and activity");
		}
		//default 1 day prior
		if (dateRequested == null) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
			dateRequested = new Timestamp(cal.getTimeInMillis());
		}
		
		List<PlaySession> sessions = PlaySessionDAO.getAllUserSessions(dateRequested, user, location, activity, payed);
		return sessions;
	}
	
	
	public PlaySession getSessionById(long sessionId, DataFetchingEnvironment env) throws Exception {
		//AuthContext context = env.getContext();
		//User curSessionUser = context.getAuthUser();
		PlaySession session = PlaySessionDAO.getById(sessionId);
		return session;
	}
	
	public List<User> searchUsers(String firstName, String lastName, String emailAddress, String username, InputAddress inputAddress, int count, int offset, DataFetchingEnvironment env) throws AccessDeniedException{
		if (!AuthData.checkPermission(env, null, PermissionType.ADMIN)) {
			throw new AccessDeniedException("You must be logged in as admin to perform this function");
		}
		//this object checks nulls and empty
		UserSearchFilter userSearchFilter = new UserSearchFilter(firstName, lastName, emailAddress, username, 
				inputAddress.getStreet(), inputAddress.getStateId(), inputAddress.getCity(), inputAddress.getPostalCode());
		
		return UserDAO.searchUsers(userSearchFilter, count, offset);
	}
	
	public boolean checkSession(DataFetchingEnvironment env) throws Exception {
		AuthContext context = env.getContext();
		User curUser = context.getAuthUser();
		if (curUser.getType().getTypeId() == TypeDAO.fetchByCode(ConstantsDAO.GUEST_CODE).getTypeId()) {
			return false;
		}
		return true;
	}
}