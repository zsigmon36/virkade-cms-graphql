package com.virkade.cms.model;

import java.sql.Date;

public class InputUser {
	private long userId;
	private String typeCode;
	private long addressId;
	private long statusId;
	private String emailAddress;
	private String username;
	private String password;
	private String securityQuestion;
	private String securityAnswer;
	private String firstName;
	private String lastName;
	private String gender;
	private int age;
	private int height;
	private int weight;
	private float idp;
	private Date birthday;
	private Boolean tcAgree;
	private Boolean liableAgree;
	private Boolean emailVerified;
	private Boolean playedBefore;
	private Boolean reServices;
	private Boolean canContact;	
	
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
	 * @return the typeCode
	 */
	public String getTypeCode() {
		return typeCode;
	}
	/**
	 * @param typeId the typeId to set
	 */
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	/**
	 * @return the addressId
	 */
	public long getAddressId() {
		return addressId;
	}
	/**
	 * @param addressId the addressId to set
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
	 * @param statusId the statusId to set
	 */
	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}
	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}
	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the securityQuestion
	 */
	public String getSecurityQuestion() {
		return securityQuestion;
	}

	/**
	 * @param securityQuestion the securityQuestion to set
	 */
	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}

	/**
	 * @return the securityAnswer
	 */
	public String getSecurityAnswer() {
		return securityAnswer;
	}

	/**
	 * @param securityAnswer the securityAnswer to set
	 */
	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
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
	 * @param lastName the lastName to set
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
	 * @param gender the gender to set
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
	 * @param age the age to set
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
	 * @param height the height to set
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
	 * @param weight the weight to set
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
	 * @param idp the idp to set
	 */
	public void setIdp(float idp) {
		this.idp = idp;
	}
	/**
	 * @return the birthday
	 */
	public Date getBirthday() {
		return birthday;
	}
	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	/**
	 * @return the tcAgree
	 */
	public Boolean isTcAgree() {
		return tcAgree;
	}
	/**
	 * @param tcAgree the tcAgree to set
	 */
	public void setTcAgree(Boolean tcAgree) {
		this.tcAgree = tcAgree;
	}
	/**
	 * @return the liabilityAgree
	 */
	public Boolean isLiableAgree() {
		return liableAgree;
	}
	/**
	 * @param liabilityAgree the liabilityAgree to set
	 */
	public void setLiableAgree(Boolean liableAgree) {
		this.liableAgree = liableAgree;
	}
	/**
	 * @return the emailVerified
	 */
	public Boolean isEmailVerified() {
		return emailVerified;
	}
	/**
	 * @param emailVerified the emailVerified to set
	 */
	public void setEmailVerified(Boolean emailVerified) {
		this.emailVerified = emailVerified;
	}
	/**
	 * @return the playedBefore
	 */
	public Boolean isPlayedBefore() {
		return playedBefore;
	}
	/**
	 * @param playedBefore the playedBefore to set
	 */
	public void setPlayedBefore(Boolean playedBefore) {
		this.playedBefore = playedBefore;
	}
	/**
	 * @return the reServices
	 */
	public Boolean isReServices() {
		return reServices;
	}
	/**
	 * @param reServices the reServices to set
	 */
	public void setReServices(Boolean reServices) {
		this.reServices = reServices;
	}
	/**
	 * @return the canContact
	 */
	public Boolean isCanContact() {
		return canContact;
	}
	/**
	 * @param canContact the canContact to set
	 */
	public void setCanContact(Boolean canContact) {
		this.canContact = canContact;
	}
}
