package com.virkade.cms.model;

import java.sql.Timestamp;

import com.virkade.cms.hibernate.dao.ActivityDAO;
import com.virkade.cms.hibernate.dao.LocationDAO;
import com.virkade.cms.hibernate.dao.TransactionDAO;
import com.virkade.cms.hibernate.dao.UserDAO;

public class PlaySession extends VirkadeModel{

	private long sessionId;
	private Transaction transaction;
	private User user;
	private Location location;
	private Activity activity;
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

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
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
	 * @return the activity
	 */
	public Activity getActivity() {
		return activity;
	}

	/**
	 * @param activity
	 *            the activity to set
	 */
	public void setActivity(Activity activity) {
		this.activity = activity;
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
		return "PlaySession [sessionId=" + sessionId + ", transactionId=" + transaction.getTransactionId() + ", user=" + user + ", location=" + location + ", activity=" + activity + ", startDate=" + startDate + ", endDate=" + endDate + ", payed="
				+ payed + ", userId=" + userId + ", emailAddress=" + emailAddress + ", username=" + username + ", firstName=" + firstName + ", lastName=" + lastName + ", audit=" + audit + "]";
	}
	
	static PlaySession convertInput(InputPlaySession inputPlaySession) {
		PlaySession session = new PlaySession();
		Transaction transaction = TransactionDAO.fetchById(inputPlaySession.getTransactionId());
		session.setTransaction(transaction);
		session.setSessionId(inputPlaySession.getSessionId());
		Activity activity = ActivityDAO.fetchByName(inputPlaySession.getActivityName());
		session.setActivity(activity);
		session.setEndDate(inputPlaySession.getEndDate());
		session.setStartDate(inputPlaySession.getStartDate());
		session.setLocation(LocationDAO.fetchByName(inputPlaySession.getLocationName(), true));
		session.setPayed(inputPlaySession.isPayed());
		session.setUser(UserDAO.getByUsername(inputPlaySession.getUsername()));
		session.setSessionId(inputPlaySession.getSessionId());
		return session;
	}

}
