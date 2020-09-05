package com.virkade.cms.model.search;

import com.google.common.base.Strings;
import com.virkade.cms.hibernate.dao.StateDAO;
import com.virkade.cms.model.State;

public class UserSearchFilter {

	private String fistName;
	private String lastName;
	private String emailAddress;
	private String username;
	private String street;
	private State state;
	private String city;
	private String postalCode;

	public UserSearchFilter() {

	}

	public UserSearchFilter(String firstName, String lastName, String emailAddress, String username, String street, long stateId, String city, String postalCode) {
		this.fistName = Strings.emptyToNull(firstName);
		this.lastName = Strings.emptyToNull(lastName);
		this.emailAddress = Strings.emptyToNull(emailAddress);
		this.username = Strings.emptyToNull(username);
		this.street = Strings.emptyToNull(street);
		this.state = stateId == 0 ? null : StateDAO.fetchById(stateId);
		this.city = Strings.emptyToNull(city);
		this.postalCode = Strings.emptyToNull(postalCode);
	}

	public String getFistName() {
		return fistName;
	}

	public void setFistName(String fistName) {
		this.fistName = fistName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

}
