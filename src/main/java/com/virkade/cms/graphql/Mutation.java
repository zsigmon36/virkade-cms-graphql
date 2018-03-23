/**
 * 
 */
package com.virkade.cms.graphql;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.virkade.cms.auth.AuthData;
import com.virkade.cms.auth.AuthToken;
import com.virkade.cms.auth.ClientSessionTracker;
import com.virkade.cms.auth.VirkadeEncryptor;
import com.virkade.cms.hibernate.dao.StatusDAO;
import com.virkade.cms.hibernate.dao.TypeDAO;
import com.virkade.cms.hibernate.dao.UserDAO;
import com.virkade.cms.hibernate.utilities.CMSSeeds;
import com.virkade.cms.model.Audit;
import com.virkade.cms.model.InputUser;
import com.virkade.cms.model.Status;
import com.virkade.cms.model.User;

import graphql.schema.DataFetchingEnvironment;

/**
 * @author SIGMON-MAIN
 *
 */
public class Mutation implements GraphQLRootResolver {
	
	private Query query = new Query();
	
	public Mutation() {
	}
	

	public User createNewUser(String emailAddress, AuthData authData, String firstName, String lastName, DataFetchingEnvironment env) throws Exception {
		AuthContext context = env.getContext();
		User curSessionUser = context.getAuthUser();
		
		List<String> missingData = new ArrayList<String>();

		if (authData == null) {
			throw new Exception("Creation of user not allowed with null AuthData");
		}
		if (UserDAO.fetchUser(new User(authData)) != null) {
			throw new Exception("User already exists with the userName=" + authData.getUserName());
		}

		if (firstName == null || firstName.equalsIgnoreCase("")) {
			missingData.add("FirstName");
		}
		if (lastName == null || lastName.equalsIgnoreCase("")) {
			missingData.add("LastName");
		}
		if (emailAddress == null || emailAddress.equalsIgnoreCase("")) {
			missingData.add("EmailAddress");
		}
		if (authData.getUserName() == null || authData.getUserName().equalsIgnoreCase("")) {
			missingData.add("Username");
		}
		if (authData.getPassword() == null || authData.getPassword().equalsIgnoreCase("")) {
			missingData.add("Password");
		}
		if (authData.getSecurityQuestion() == null || authData.getSecurityQuestion().equalsIgnoreCase("")) {
			missingData.add("SecurityQuestion");
		}
		if (authData.getSecurityAnswer() == null || authData.getSecurityAnswer().equalsIgnoreCase("")) {
			missingData.add("SecurityAnswer");
		}
		if (!missingData.isEmpty()) {
			throw new Exception(
					"Creation of user not allowed with the following missing data [" + missingData.toString() + "]");
		}
		User user = new User(authData);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setUserName(authData.getUserName());
		user.setEmailAddress(emailAddress);
		user.setPassword(VirkadeEncryptor.hashEncode(authData.getPassword()));
		user.setSecurityQuestion(authData.getSecurityQuestion());
		user.setSecurityAnswer(VirkadeEncryptor.hashEncode(authData.getSecurityAnswer()));
		user.setStatus(StatusDAO.fetchStatusByCode(StatusDAO.ACTIVE_CODE));
		user.setType(TypeDAO.fetchTypeByCode(TypeDAO.PROSPECT_CODE));
		user.getAudit().setCreatedAt(new Date());
		user.getAudit().setCreatedBy((curSessionUser.getUserName() == null)?user.getUserName():curSessionUser.getUserName());
		user.getAudit().setUpdatedAt(new Date());
		user.getAudit().setUpdatedBy((curSessionUser.getUserName() == null)?user.getUserName():curSessionUser.getUserName());
		UserDAO.pushUser(user, false);

		return user;

	}

	public AuthToken signIn(AuthData authData) throws Exception {
		return ClientSessionTracker.clientSignIn(authData);
	}

	public User updateUser(InputUser inputUser, DataFetchingEnvironment env) throws Exception {
		AuthContext context = env.getContext();
		User curSessionUser = context.getAuthUser();
		
		if (!curSessionUser.getUserName().equals(inputUser.getUserName()) && 
				(curSessionUser.getType().getTypeId() != TypeDAO.fetchTypeByCode(TypeDAO.ADMIN_CODE).getTypeId())) {
			throw new AccessDeniedException("User cannot be modified by the requesting user");
		}
		
		User userToUpdate = query.getUser(inputUser.getUserName());
		User convertedInputUser = User.convertObjToUser(inputUser.getClass().getName(), inputUser);
		
		if (convertedInputUser.getAddress() != null) {
			userToUpdate.setAddress(convertedInputUser.getAddress());
		}
		if (convertedInputUser.getAge() != 0) {
			userToUpdate.setAge(convertedInputUser.getAge());
		}
		if (convertedInputUser.getEmailAddress() != null) {
			userToUpdate.setEmailAddress(convertedInputUser.getEmailAddress());
		}
		if (convertedInputUser.getFirstName() != null) {
			userToUpdate.setFirstName(convertedInputUser.getFirstName());
		}
		if (convertedInputUser.getGender() != null) {
			userToUpdate.setGender(convertedInputUser.getGender());
		}
		if (convertedInputUser.getHeight() != 0) {
			userToUpdate.setHeight(convertedInputUser.getHeight());
		}
		if (convertedInputUser.getIdp() != 0F) {
			userToUpdate.setIdp(convertedInputUser.getIdp());
		}
		if (convertedInputUser.getLastName() != null) {
			userToUpdate.setLastName(convertedInputUser.getLastName());
		}
		if (convertedInputUser.getPassword() != null) {
			userToUpdate.setPassword(VirkadeEncryptor.hashEncode(convertedInputUser.getPassword()));
		}
		if (convertedInputUser.getSecurityQuestion() != null) {
			userToUpdate.setSecurityQuestion(convertedInputUser.getSecurityQuestion());
		}
		if (convertedInputUser.getSecurityAnswer() != null) {
			userToUpdate.setSecurityAnswer(VirkadeEncryptor.hashEncode(convertedInputUser.getSecurityAnswer()));
		}
		if (convertedInputUser.getStatus() != null) {
			userToUpdate.setStatus(convertedInputUser.getStatus());
		}
		if (convertedInputUser.getType() != null) {
			userToUpdate.setType(convertedInputUser.getType());
		}
		if (convertedInputUser.getWeight() != 0) {
			userToUpdate.setWeight(convertedInputUser.getWeight());
		}
		
		Audit updatedAuditInfo = userToUpdate.getAudit();
		updatedAuditInfo.setUpdatedAt(new Date());
		updatedAuditInfo.setUpdatedBy(curSessionUser.getUserName());
		userToUpdate.setAudit(updatedAuditInfo);
		
		return UserDAO.pushUser(userToUpdate, true);
	}

}
