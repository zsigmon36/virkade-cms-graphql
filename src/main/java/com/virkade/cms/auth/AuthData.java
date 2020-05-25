package com.virkade.cms.auth;

import com.virkade.cms.graphql.AuthContext;
import com.virkade.cms.hibernate.dao.ConstantsDAO;
import com.virkade.cms.hibernate.dao.TypeDAO;
import com.virkade.cms.model.User;

import graphql.schema.DataFetchingEnvironment;

public class AuthData {
	private String username;
	private String password;
	private String securityQuestion;
	private String securityAnswer;

	public AuthData() {

	}

	public AuthData(String username, String password) {
		this.username = username;
		this.password = password;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
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
	 * @param password
	 *            the password to set
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

	public static boolean checkPermission(DataFetchingEnvironment env, User userToModify, PermissionType type) {
		AuthContext context = env.getContext();
		User curUser = context.getAuthUser();
		
		switch (type) {
		case GUEST:
			return guestPermissionCheck(curUser);
		case NORMAL:
			return normalPermissionCheck(curUser, userToModify);
		case ADMIN:
			return adminPermissionCheck(curUser);
		default:
			return false;
		}
	}

	private static boolean guestPermissionCheck(User curUser) {
		return true;
	}

	private static boolean normalPermissionCheck(User curUser, User userToModify) {
		if (adminPermissionCheck(curUser)) {
			return true;
		} else if (curUser.getUserId() == (userToModify.getUserId()) && curUser.getType().getTypeId() != TypeDAO.fetchByCode(ConstantsDAO.GUEST_CODE).getTypeId()) {
			return true;
		}
		return false;
	}

	private static boolean adminPermissionCheck(User curUser) {
		if (curUser.getType().getTypeId() == TypeDAO.fetchByCode(ConstantsDAO.ADMIN_CODE).getTypeId()) {
			return true;
		}
		return false;
	}

}
