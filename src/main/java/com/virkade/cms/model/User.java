package com.virkade.cms.model;

import java.security.InvalidKeyException;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import org.apache.log4j.Logger;

import com.virkade.cms.auth.AuthData;
import com.virkade.cms.hibernate.dao.AddressDAO;
import com.virkade.cms.hibernate.dao.StatusDAO;
import com.virkade.cms.hibernate.dao.TypeDAO;

public class User extends VirkadeModel {
	private static final Logger LOG = Logger.getLogger(User.class);

	private long userId;
	private Type type;
	private Address address;
	private Status status;
	private List<PlaySession> sessions;
	private List<Comment> comments;
	private List<Phone> phoneNumbers;
	private String emailAddress;
	private String userName;
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
	private boolean emailVerified;
	private boolean playedBefore;
	private Date lastLogin;
	private boolean reServices;
	private boolean canContact;
	private Audit audit;

	public User() {
	}

	public User(AuthData authData) {
		this.userName = authData.getUserName();
		this.password = authData.getPassword();
		this.securityQuestion = authData.getPassword();
		this.securityAnswer = authData.getSecurityAnswer();
	}

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
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * @return the session
	 */
	public List<PlaySession> getSessions() {
		return sessions;
	}

	/**
	 * @param session
	 *            the session to set
	 */
	public void setSessions(List<PlaySession> sessions) {
		this.sessions = sessions;
	}

	/**
	 * @return the comments
	 */
	public List<Comment> getComments() {
		return comments;
	}

	/**
	 * @param comments
	 *            the comments to set
	 */
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	/**
	 * @return the phoneNumbers
	 */
	public List<Phone> getPhoneNumbers() {
		return phoneNumbers;
	}

	/**
	 * @param phoneNumbers the phoneNumbers to set
	 */
	public void setPhoneNumbers(List<Phone> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
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
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws InvalidKeyException
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
	 * @param securityQuestion
	 *            the securityQuestion to set
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
	 * @param securityAnswer
	 *            the securityAnswer to set
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
	 * @param audit
	 *            the audit to set
	 */
	public void setAudit(Audit audit) {
		this.audit = audit;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [userId=" + userId + ", type=" + type.toString() + ", address=" + address.toString() + ", status=" + status.toString() 
		+ ", emailAddress=" + emailAddress + ", userName=" + userName + ", password=" + password + ", firstName=" + firstName + ", lastName=" 
				+ lastName + ", gender=" + gender + ", age=" + age + ", height=" + height + ", weight=" + weight + ", idp=" + idp
				+ ", emailVerified=" + emailVerified + ", playedBefore=" + playedBefore + ", lastLogin=" + lastLogin + ", reServices=" + reServices 
				+ ", canContact=" + canContact + ", createdAt=" + audit.getCreatedAt() + ", updatedAt=" + audit.getUpdatedAt() + ", createdBy=" 
				+ audit.getCreatedBy() + ", updatedBy=" + audit.getUpdatedBy() + "]";
	}
	
	/**
	 * @return the attribute sorted list
	 */
	public SortedSet<String> getAttributeList() {
		SortedSet<String> attributes = super.getAttributeList();
		attributes.add("UserId");
		attributes.add("Type");
		attributes.add("Address");
		attributes.add("StatusId");
		attributes.add("Session");
		attributes.add("EmailAddress");
		attributes.add("UserName");
		attributes.add("Password");
		attributes.add("SecurityQuestion");
		attributes.add("SecurityAnswer");
		attributes.add("FirstName");
		attributes.add("LastName");
		attributes.add("Gender");
		attributes.add("Age");
		attributes.add("Height");
		attributes.add("Weight");
		attributes.add("Idp");
		attributes.add("EmailVerified");
		attributes.add("PlayedBefore");
		attributes.add("LastLogin");
		attributes.add("ReServices");
		attributes.add("CanContact");
		return attributes;
	}

	static User convertInput(InputUser inputUser) {
		User user = new User();
		user.setAddress(AddressDAO.fetchById(inputUser.getAddressId()));
		user.setAge(inputUser.getAge());
		user.setCanContact(inputUser.isCanContact());
		user.setEmailAddress(inputUser.getEmailAddress());
		user.setEmailVerified(inputUser.isEmailVerified());
		user.setFirstName(inputUser.getFirstName());
		user.setGender(inputUser.getGender());
		user.setHeight(inputUser.getHeight());
		user.setIdp(inputUser.getIdp());
		user.setLastName(inputUser.getLastName());
		user.setPassword(inputUser.getPassword());
		user.setSecurityQuestion(inputUser.getSecurityQuestion());
		user.setSecurityAnswer(inputUser.getSecurityAnswer());
		user.setPlayedBefore(inputUser.isPlayedBefore());
		user.setReServices(inputUser.isReServices());
		user.setStatus(StatusDAO.fetchById(inputUser.getStatusId()));
		user.setType(TypeDAO.getByCode(inputUser.getTypeCode()));
		user.setUserName(inputUser.getUserName());
		user.setWeight(inputUser.getWeight());
		return user;
	}


}
