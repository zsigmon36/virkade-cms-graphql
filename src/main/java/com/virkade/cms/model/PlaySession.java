package com.virkade.cms.model;

import java.util.Date;
import java.util.List;

public class PlaySession {

	private long sessionId;
	private User user;
	private Location location;
	private List<Activity> activities;
	private Date startDate;
	private Date endDate;
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
	}

	/**
	 * @return the userId
	 */
	public long getUserId() {
		return user.getUserId();
	}

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return user.getEmailAddress();
	}

	/**
	 * @return the Username
	 */
	public String getUsername() {
		return user.getUsername();
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return user.getFirstName();
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return user.getLastName();
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

}
