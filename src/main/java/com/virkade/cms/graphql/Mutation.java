/**
 * 
 */
package com.virkade.cms.graphql;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.virkade.cms.auth.AuthData;
import com.virkade.cms.auth.AuthToken;
import com.virkade.cms.auth.ClientSessionTracker;
import com.virkade.cms.auth.PermissionType;
import com.virkade.cms.auth.VirkadeEncryptor;
import com.virkade.cms.hibernate.dao.AddressDAO;
import com.virkade.cms.hibernate.dao.PhoneDAO;
import com.virkade.cms.hibernate.dao.StatusDAO;
import com.virkade.cms.hibernate.dao.TypeDAO;
import com.virkade.cms.hibernate.dao.UserDAO;
import com.virkade.cms.model.Address;
import com.virkade.cms.model.Audit;
import com.virkade.cms.model.Comment;
import com.virkade.cms.model.InputAddress;
import com.virkade.cms.model.InputComment;
import com.virkade.cms.model.InputPhone;
import com.virkade.cms.model.InputUser;
import com.virkade.cms.model.Legal;
import com.virkade.cms.model.Phone;
import com.virkade.cms.model.PlaySession;
import com.virkade.cms.model.User;
import com.virkade.cms.model.VirkadeModel;

import graphql.schema.DataFetchingEnvironment;

/**
 * @author SIGMON-MAIN
 *
 */
public class Mutation implements GraphQLRootResolver {

	private static final Logger LOG = Logger.getLogger(Mutation.class);
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
		if (UserDAO.fetch(new User(authData)) != null) {
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
			throw new Exception("Creation of user not allowed with the following missing data [" + missingData.toString() + "]");
		}

		if (!AuthData.checkPermission(env, UserDAO.fetchByUserName(authData.getUserName()), PermissionType.GUEST)) {
			throw new AccessDeniedException("User cannot be modified by the requesting user");
		}

		User user = new User(authData);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setUserName(authData.getUserName());
		user.setEmailAddress(emailAddress);
		user.setPassword(VirkadeEncryptor.hashEncode(authData.getPassword()));
		user.setSecurityQuestion(authData.getSecurityQuestion());
		user.setSecurityAnswer(VirkadeEncryptor.hashEncode(authData.getSecurityAnswer()));
		user.setStatus(StatusDAO.fetchByCode(StatusDAO.ACTIVE_CODE));
		user.setType(TypeDAO.getByCode(TypeDAO.PROSPECT_CODE));
		user.getAudit().setCreatedAt(new Date());
		user.getAudit().setCreatedBy((curSessionUser.getUserName() == null) ? user.getUserName() : curSessionUser.getUserName());
		user.getAudit().setUpdatedAt(new Date());
		user.getAudit().setUpdatedBy((curSessionUser.getUserName() == null) ? user.getUserName() : curSessionUser.getUserName());
		UserDAO.createUpdate(user, false);

		return user;

	}

	public User updateUser(InputUser inputUser, DataFetchingEnvironment env) throws Exception {
		AuthContext context = env.getContext();
		User curSessionUser = context.getAuthUser();

		User userToUpdate = query.getUserById(inputUser.getUserId());

		if (!AuthData.checkPermission(env, userToUpdate, PermissionType.NORMAL)) {
			throw new AccessDeniedException("User cannot be modified by the requesting user");
		}

		User convertedInputUser = (User) VirkadeModel.convertObj(inputUser.getClass().getName(), inputUser);

		if (convertedInputUser.getUserName() != null) {
			userToUpdate.setUserName(convertedInputUser.getUserName());
		}
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

		return UserDAO.createUpdate(userToUpdate, true);
	}

	public AuthToken signIn(AuthData authData) throws Exception {
		return ClientSessionTracker.clientSignIn(authData);
	}

	public String signOut(DataFetchingEnvironment env) throws Exception {
		AuthContext context = env.getContext();
		User curSessionUser = context.getAuthUser();
		if (!AuthData.checkPermission(env, curSessionUser, PermissionType.NORMAL)) {
			throw new AccessDeniedException("User cannot be modified by the requesting user");
		}

		ClientSessionTracker.purgeActiveSession(curSessionUser.getUserName());
		return "Success";
	}

	public Phone addPhone(InputPhone phoneInput, DataFetchingEnvironment env) throws Exception {
		AuthContext context = env.getContext();
		User curSessionUser = context.getAuthUser();
		User userToUpdate = query.getUserByUserName(phoneInput.getUserName());

		if (!AuthData.checkPermission(env, userToUpdate, PermissionType.NORMAL)) {
			throw new AccessDeniedException("User cannot be modified by the requesting user");
		}

		List<String> missingData = new ArrayList<String>();
		if (phoneInput.getTypeCode() == null || phoneInput.getTypeCode().equalsIgnoreCase("")) {
			missingData.add("Type Code");
		}
		if (phoneInput.getCountryCode() == 0) {
			missingData.add("Country Code");
		}
		if (phoneInput.getNumber() == 0) {
			missingData.add("Number");
		}
		if (!missingData.isEmpty()) {
			throw new Exception("Creation of phone not allowed with the following missing data [" + missingData.toString() + "]");
		}
		Phone phone = new Phone();

		phone.setUser(userToUpdate);
		phone.setType(TypeDAO.fetchByCode(phoneInput.getTypeCode()));
		phone.setNumber(phoneInput.getNumber());
		phone.setCountryCode(phoneInput.getCountryCode());
		phone.getAudit().setCreatedAt(new Date());
		phone.getAudit().setCreatedBy(curSessionUser.getUserName());
		phone.getAudit().setUpdatedAt(new Date());
		phone.getAudit().setUpdatedBy(curSessionUser.getUserName());

		return PhoneDAO.create(phone);

	}

	public Address addUserAddress(InputAddress inputAddress, DataFetchingEnvironment env) throws Exception {
		AuthContext context = env.getContext();
		User curSessionUser = context.getAuthUser();
		if (!AuthData.checkPermission(env, curSessionUser, PermissionType.NORMAL)) {
			throw new AccessDeniedException("Comment cannot be added by the requesting user");
		}
		if (inputAddress.getPostalCode() < 10000) {
			//do message or action
		}
		
		Address convertedInputAddress = (Address) VirkadeModel.convertObj(inputAddress.getClass().getName(), inputAddress);
		
		Audit updatedAuditInfo = convertedInputAddress.getAudit();
		updatedAuditInfo.setUpdatedAt(new Date());
		updatedAuditInfo.setUpdatedBy(curSessionUser.getUserName());
		updatedAuditInfo.setCreatedBy(curSessionUser.getUserName());
		updatedAuditInfo.setCreatedAt(new Date());
		convertedInputAddress.setAudit(updatedAuditInfo);
		
		Address address = AddressDAO.fetch(convertedInputAddress);
		if (address == null) {
			address = AddressDAO.create(convertedInputAddress);
		} else {
			LOG.warn(String.format("Address was found, assigning user: %s to address: %s ", curSessionUser.getUserName(), address.getAddressId()));
		}
		User user = UserDAO.fetch(curSessionUser);
		user.setAddress(address);
		UserDAO.createUpdate(user, true);
		return address;
	}

	public Comment addComment(InputComment inputComment, DataFetchingEnvironment env) throws AccessDeniedException {
		AuthContext context = env.getContext();
		User curSessionUser = context.getAuthUser();
		User userToUpdate = UserDAO.fetchByUserName(inputComment.getUserName());
		if (!AuthData.checkPermission(env, userToUpdate, PermissionType.NORMAL)) {
			throw new AccessDeniedException("Comment cannot be added by the requesting user");
		}

		Comment convertedInputComment = Comment.convertObjToComment(inputComment.getClass().getName(), inputComment);
		return null;
	}

	public PlaySession addSession(String userName, DataFetchingEnvironment env) throws AccessDeniedException {
		AuthContext context = env.getContext();
		User curSessionUser = context.getAuthUser();
		User userToUpdate = UserDAO.fetchByUserName(userName);
		if (!AuthData.checkPermission(env, userToUpdate, PermissionType.NORMAL)) {
			throw new AccessDeniedException("Session cannot be created by the requesting user");
		}
		return null;
	}

	public Legal addLegal(String userName, DataFetchingEnvironment env) throws AccessDeniedException {
		AuthContext context = env.getContext();
		User curSessionUser = context.getAuthUser();
		User userToUpdate = UserDAO.fetchByUserName(userName);
		if (!AuthData.checkPermission(env, userToUpdate, PermissionType.NORMAL)) {
			throw new AccessDeniedException("Legal document association cannot be modified by the requesting user");
		}
		return null;
	}

	public String setPassword(String userName, String newPassword, DataFetchingEnvironment env) throws AccessDeniedException {
		AuthContext context = env.getContext();
		User curSessionUser = context.getAuthUser();
		User userToUpdate = UserDAO.fetchByUserName(userName);
		if (!AuthData.checkPermission(env, userToUpdate, PermissionType.NORMAL)) {
			throw new AccessDeniedException("User cannot be modified by the requesting user");
		}
		return null;
	}

	public String setSecurityQA(String userName, String securityQ, String securityA, DataFetchingEnvironment env) throws AccessDeniedException {
		AuthContext context = env.getContext();
		User curSessionUser = context.getAuthUser();
		User userToUpdate = UserDAO.fetchByUserName(userName);
		if (!AuthData.checkPermission(env, userToUpdate, PermissionType.NORMAL)) {
			throw new AccessDeniedException("User cannot be modified by the requesting user");
		}
		return null;
	}

}
