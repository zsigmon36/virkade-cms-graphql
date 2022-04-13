package com.virkade.cms.model;

import java.util.SortedSet;

import com.virkade.cms.hibernate.dao.TypeDAO;

public class Phone extends VirkadeModel{

	private long phoneId;
	private User user;
	private Type type;
	private String number;
	private int countryCode;
	private Audit audit;

	/**
	 * @return the phoneId
	 */
	public long getPhoneId() {
		return phoneId;
	}

	/**
	 * @param phoneId
	 *            the phoneId to set
	 */
	public void setPhoneId(long phoneId) {
		this.phoneId = phoneId;
	}

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number
	 *            the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
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
	 * @return the Username
	 */
	public String getUsername() {
		return user.getUsername();
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

	/**
	 * @return the countryCode
	 */
	public int getCountryCode() {
		return countryCode;
	}

	/**
	 * @param countryCode
	 *            the countryCode to set
	 */
	public void setCountryCode(int countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * @return the attribute sorted list
	 */
	public SortedSet<String> getAttributeList() {
		SortedSet<String> attributes = super.getAttributeList();
		attributes.add("PhoneId");
		attributes.add("User");
		attributes.add("Type");
		attributes.add("Number");
		attributes.add("CountryCode");
		return attributes;
	}

	static Phone convertInput(InputPhone inputPhone) {
		Phone phone = new Phone();
		phone.setType(TypeDAO.fetchByCode(inputPhone.getTypeCode()));
		phone.setNumber(inputPhone.getNumber());
		phone.setCountryCode(inputPhone.getCountryCode());
		return phone;
	}
}
