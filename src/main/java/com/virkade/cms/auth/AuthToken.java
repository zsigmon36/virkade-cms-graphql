package com.virkade.cms.auth;

import java.util.Date;

public class AuthToken {
	private String token;
	private String username;
	private Date createdDate;

	public AuthToken() {
	}
	
	public AuthToken(String username, String authToken) {
		this.username = username;
		this.token = authToken;
		this.createdDate = new Date();
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the userName
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
}
