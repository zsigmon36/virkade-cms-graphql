package com.virkade.cms.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.virkade.cms.hibernate.dao.ActivityDAO;
import com.virkade.cms.hibernate.dao.AddressDAO;
import com.virkade.cms.hibernate.dao.LocationDAO;
import com.virkade.cms.hibernate.dao.StatusDAO;
import com.virkade.cms.hibernate.dao.TypeDAO;
import com.virkade.cms.hibernate.dao.UserDAO;

public class PlaySession extends VirkadeModel{

	private long sessionId;
	private User user;
	private Location location;
	private List<Activity> activities;
	private Timestamp startDate;
	private Timestamp endDate;
	private boolean payed;
	private long userId;
	private String emailAddress;
	private String username;
	private String firstName;
	private String lastName;
	private Audit audit;

	public PlaySession() {

	}

	/**
	 * @return the sessionId
	 */
	public long getSessionId() {
		return sessionId;
	}

	/**
	 * @param sessionId
	 *            the sessionId to set
	 */
	public void setSessionId(long sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * @return the activities
	 */
	public List<Activity> getActivities() {
		return activities;
	}

	/**
	 * @param activities
	 *            the activities to set
	 */
	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
		this.userId = user.getUserId();
		this.emailAddress = user.getEmailAddress();
		this.username = user.getUsername();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
	}

	/**
	 * @return the userId
	 */
	public long getUserId() {
		return this.userId;
	}

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return this.emailAddress;
	}

	/**
	 * @return the Username
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * @return the startDate
	 */
	public Timestamp getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Timestamp getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public boolean isPayed() {
		return payed;
	}

	public void setPayed(boolean payed) {
		this.payed = payed;
	}

	/**
	 * @return the audit
	 */
	public Audit getAudit() {
		if (this.audit == null) {
			this.audit = new Audit();
		}
		return audit;
	}

	/**
	 * @param audit
	 *            the audit to set
	 */
	public void setAudit(Audit audit) {
		this.audit = audit;
	}

	@Override
	public String toString() {
		return "PlaySession [sessionId=" + sessionId + ", user=" + user + ", location=" + location + ", activities=" + activities + ", startDate=" + startDate + ", endDate=" + endDate + ", payed="
				+ payed + ", userId=" + userId + ", emailAddress=" + emailAddress + ", username=" + username + ", firstName=" + firstName + ", lastName=" + lastName + ", audit=" + audit + "]";
	}
	
	static PlaySession convertInput(InputPlaySession inputPlaySession) {
		PlaySession session = new PlaySession();
		List<Activity> activities = new ArrayList<>();
		Activity activity = ActivityDAO.fetchByName(inputPlaySession.getActivityName());
		activities.add(activity);
		session.setActivities(activities);
		session.setEndDate(inputPlaySession.getEndDate());
		session.setStartDate(inputPlaySession.getStartDate());
		session.setLocation(LocationDAO.fetchByName(inputPlaySession.getLocationName()));
		session.setPayed(inputPlaySession.isPayed());
		session.setUser(UserDAO.getByUsername(inputPlaySession.getUsername()));
		return session;
	}

}
