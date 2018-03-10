package com.virkade.cms.model;

import java.util.Date;
import java.util.List;

public class User {
	private long userId;
	private long typeId;
	private long addressId;
	private long statusId;
	private List<PlaySession> session;
	private String emailAddress;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String gender;
	private int age;
	private int height;
	private int weight;
	private float idp;
	private boolean tcAgree;
	private boolean liabilityAgree;
	private boolean emailVerified;
	private boolean playedBefore;
	private Date lastLogin;
	private boolean reServices;
	private boolean canContact;
	private Audit audit;
	/**
	 * @return the userId
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

	/**
	 * @return the typeId
	 */
	public long getTypeId() {
		return typeId;
	}

	/**
	 * @param typeId
	 *            the typeId to set
	 */
	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}

	/**
	 * @return the addressId
	 */
	public long getAddressId() {
		return addressId;
	}

	/**
	 * @param addressId
	 *            the addressId to set
	 */
	public void setAddressId(long addressId) {
		this.addressId = addressId;
	}

	/**
	 * @return the statusId
	 */
	public long getStatusId() {
		return statusId;
	}

	/**
	 * @param statusId
	 *            the statusId to set
	 */
	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}

	/**
	 * @return the session
	 */
	public List<PlaySession> getSession() {
		return session;
	}

	/**
	 * @param session the session to set
	 */
	public void setSession(List<PlaySession> session) {
		this.session = session;
	}

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress
	 *            the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender
	 *            the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age
	 *            the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * @param weight
	 *            the weight to set
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}

	/**
	 * @return the idp
	 */
	public float getIdp() {
		return idp;
	}

	/**
	 * @param idp
	 *            the idp to set
	 */
	public void setIdp(float idp) {
		this.idp = idp;
	}

	/**
	 * @return the tcAgree
	 */
	public boolean isTcAgree() {
		return tcAgree;
	}

	/**
	 * @param tcAgree
	 *            the tcAgree to set
	 */
	public void setTcAgree(boolean tcAgree) {
		this.tcAgree = tcAgree;
	}

	/**
	 * @return the liabilityAgree
	 */
	public boolean isLiabilityAgree() {
		return liabilityAgree;
	}

	/**
	 * @param liabilityAgree
	 *            the liabilityAgree to set
	 */
	public void setLiabilityAgree(boolean liabilityAgree) {
		this.liabilityAgree = liabilityAgree;
	}

	/**
	 * @return the emailVerified
	 */
	public boolean isEmailVerified() {
		return emailVerified;
	}

	/**
	 * @param emailVerified
	 *            the emailVerified to set
	 */
	public void setEmailVerified(boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	/**
	 * @return the playedBefore
	 */
	public boolean isPlayedBefore() {
		return playedBefore;
	}

	/**
	 * @param playedBefore
	 *            the playedBefore to set
	 */
	public void setPlayedBefore(boolean playedBefore) {
		this.playedBefore = playedBefore;
	}

	/**
	 * @return the lastLogin
	 */
	public Date getLastLogin() {
		return lastLogin;
	}

	/**
	 * @param lastLogin
	 *            the lastLogin to set
	 */
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	/**
	 * @return the reServices
	 */
	public boolean isReServices() {
		return reServices;
	}

	/**
	 * @param reServices
	 *            the reServices to set
	 */
	public void setReServices(boolean reServices) {
		this.reServices = reServices;
	}

	/**
	 * @return the canContact
	 */
	public boolean canContact() {
		return canContact;
	}

	/**
	 * @param canContact
	 *            the canContact to set
	 */
	public void setCanContact(boolean canContact) {
		this.canContact = canContact;
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
	 * @param audit the audit to set
	 */
	public void setAudit(Audit audit) {
		this.audit = audit;
	}
		
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [userId=" + userId + ", typeId=" + typeId + ", addressId=" + addressId + ", statusId=" + statusId
				+ ", emailAddress=" + emailAddress + ", userName=" + userName + ", password=" + password
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", gender=" + gender + ", age=" + age
				+ ", height=" + height + ", weight=" + weight + ", idp=" + idp + ", tcAgree=" + tcAgree
				+ ", liabilityAgree=" + liabilityAgree + ", emailVerified=" + emailVerified + ", playedBefore="
				+ playedBefore + ", lastLogin=" + lastLogin + ", reServices=" + reServices + ", canContact="
				+ canContact + ", createdAt=" + audit.getCreatedAt() + ", updatedAt=" + audit.getUpdatedAt() + ", createdBy=" + audit.getCreatedBy()
				+ ", updatedBy=" + audit.getUpdatedBy() + "]";
	}

}
