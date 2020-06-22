/**
 * 
 */
package com.virkade.cms.graphql;

import java.nio.file.AccessDeniedException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.virkade.cms.auth.AuthData;
import com.virkade.cms.auth.AuthToken;
import com.virkade.cms.auth.ClientSessionTracker;
import com.virkade.cms.auth.PermissionType;
import com.virkade.cms.auth.VirkadeEncryptor;
import com.virkade.cms.hibernate.dao.AddressDAO;
import com.virkade.cms.hibernate.dao.CommentDAO;
import com.virkade.cms.hibernate.dao.ConstantsDAO;
import com.virkade.cms.hibernate.dao.LegalDAO;
import com.virkade.cms.hibernate.dao.PhoneDAO;
import com.virkade.cms.hibernate.dao.StatusDAO;
import com.virkade.cms.hibernate.dao.TypeDAO;
import com.virkade.cms.hibernate.dao.UserDAO;
import com.virkade.cms.model.Address;
import com.virkade.cms.model.Audit;
import com.virkade.cms.model.Comment;
import com.virkade.cms.model.InputAddress;
import com.virkade.cms.model.InputComment;
import com.virkade.cms.model.InputLegal;
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
			throw new Exception("User already exists with the username=" + authData.getUsername());
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
		if (authData.getUsername() == null || authData.getUsername().equalsIgnoreCase("")) {
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

		if (!AuthData.checkPermission(env, UserDAO.fetchByUsername(authData.getUsername()), PermissionType.GUEST)) {
			throw new AccessDeniedException("User cannot be modified by the requesting user");
		}

		User user = new User(authData);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setUsername(authData.getUsername());
		user.setEmailAddress(emailAddress);
		user.setPassword(VirkadeEncryptor.hashEncode(authData.getPassword()));
		user.setSecurityQuestion(authData.getSecurityQuestion());
		user.setSecurityAnswer(VirkadeEncryptor.hashEncode(authData.getSecurityAnswer()));
		user.setAge(0);
		user.setCanContact(false);
		user.setCanContact(false);
		user.setEmailVerified(false);
		user.setHeight(0);
		user.setPlayedBefore(false);
		user.setReServices(false);
		user.setWeight(0);
		user.setStatus(StatusDAO.fetchByCode(ConstantsDAO.ACTIVE_CODE));
		user.setType(TypeDAO.getByCode(ConstantsDAO.PROSPECT_CODE));
		
		Audit updatedAuditInfo = VirkadeModel.addAuditToModel(curSessionUser, null);
		user.setAudit(updatedAuditInfo);
		
		UserDAO.createUpdate(user, false);

		return user;

	}

	public User updateUser(InputUser inputUser, DataFetchingEnvironment env) throws Exception {
		AuthContext context = env.getContext();
		User curSessionUser = context.getAuthUser();

		User userToUpdate = new User();
		userToUpdate.setUserId(inputUser.getUserId());
		userToUpdate = UserDAO.fetch(userToUpdate);

		if (userToUpdate == null || !AuthData.checkPermission(env, userToUpdate, PermissionType.NORMAL)) {
			throw new AccessDeniedException("User cannot be modified by the requesting user");
		}

		User convertedInputUser = (User) VirkadeModel.convertObj(inputUser.getClass().getName(), inputUser);

		if (convertedInputUser.getUsername() != null) {
			userToUpdate.setUsername(convertedInputUser.getUsername());
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
		if (convertedInputUser.getIdp() != 0.0F) {
			userToUpdate.setIdp(convertedInputUser.getIdp());
		}
		if (convertedInputUser.getBirthday() != null) {
			userToUpdate.setBirthday(convertedInputUser.getBirthday());
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
		if (convertedInputUser.canContact() != null) {
			userToUpdate.setCanContact(convertedInputUser.canContact());
		}
		if (convertedInputUser.isReServices() != null) {
			userToUpdate.setReServices(convertedInputUser.isReServices());
		}
		if (convertedInputUser.isPlayedBefore() != null) {
			userToUpdate.setPlayedBefore(convertedInputUser.isPlayedBefore());
		}

		Audit updatedAuditInfo = VirkadeModel.addAuditToModel(curSessionUser, userToUpdate.getAudit());
		userToUpdate.setAudit(updatedAuditInfo);

		return UserDAO.createUpdate(userToUpdate, true);
	}

	public AuthToken signIn(AuthData authData) throws Exception {
		 AuthToken authToken = ClientSessionTracker.clientSignIn(authData);
		 if (authToken != null && authToken.getToken().length() > 0) {
			 User user = UserDAO.getByUsername(authToken.getUsername());
			 user.setLastLogin(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			 UserDAO.createUpdate(user, true);
		 }
		 return authToken;
	}
	
	public boolean recoverySignIn(AuthData authData) throws Exception {
		boolean results = false;
		AuthToken authToken = ClientSessionTracker.recoverySignIn(authData);
		if (authToken.getToken() != null) {
			results = true;
		}
		return results;
	}

	public String signOut(String username, DataFetchingEnvironment env) throws Exception {
		User userToModify = UserDAO.fetchByUsername(username);
		if (!AuthData.checkPermission(env, userToModify, PermissionType.NORMAL)) {
			throw new AccessDeniedException("User cannot be modified by the requesting user");
		}

		ClientSessionTracker.purgeSession(username, false);
		return "Success";
	}

	public Phone addPhone(InputPhone phoneInput, DataFetchingEnvironment env) throws Exception {
		AuthContext context = env.getContext();
		User curSessionUser = context.getAuthUser();
		User userToUpdate = query.getUserByUsername(phoneInput.getUsername());

		if (userToUpdate == null || !AuthData.checkPermission(env, userToUpdate, PermissionType.NORMAL)) {
			throw new AccessDeniedException("User cannot be modified by the requesting user");
		}
		
		Phone convertedInputPhone = (Phone) VirkadeModel.convertObj(phoneInput.getClass().getName(), phoneInput);
		
		List<String> missingData = new ArrayList<String>();
		if (phoneInput.getTypeCode() == null || phoneInput.getTypeCode().equalsIgnoreCase("")) {
			missingData.add("Type Code");
		}
		if (phoneInput.getNumber() == "") {
			missingData.add("Number");
		}
		if (!missingData.isEmpty()) {
			throw new Exception("Creation of phone not allowed with the following missing data [" + missingData.toString() + "]");
		}
		
		Audit updatedAuditInfo = VirkadeModel.addAuditToModel(curSessionUser, null);
		convertedInputPhone.setAudit(updatedAuditInfo);
		
		Phone phone = new Phone();
		User user = UserDAO.fetch(userToUpdate);
		convertedInputPhone.setUser(user);
		phone = PhoneDAO.fetch(convertedInputPhone);
		if (phone == null) {
			phone = PhoneDAO.create(convertedInputPhone);
		}
		return phone;

	}

	public Address addUserAddress(InputAddress inputAddress, DataFetchingEnvironment env) throws Exception {
		AuthContext context = env.getContext();
		User curSessionUser = context.getAuthUser();
		if (!AuthData.checkPermission(env, curSessionUser, PermissionType.NORMAL)) {
			throw new AccessDeniedException("Comment cannot be added by the requesting user");
		}
		if (inputAddress.getPostalCode() != null) {
			Pattern regex = Pattern.compile("^[0-9]{5}(?:-[0-9]{4})?$");
			Matcher matcher = regex.matcher(inputAddress.getPostalCode());
			if (!matcher.matches()) {
				throw new Exception("Postal code does not match expected pattern of 55555 or 55555-5555");
			}		
		}
		
		Address convertedInputAddress = (Address) VirkadeModel.convertObj(inputAddress.getClass().getName(), inputAddress);
		
		Audit updatedAuditInfo = VirkadeModel.addAuditToModel(curSessionUser, null);
		convertedInputAddress.setAudit(updatedAuditInfo);
		
		Address address = AddressDAO.fetch(convertedInputAddress);
		if (address == null) {
			address = AddressDAO.create(convertedInputAddress);
		} else {
			LOG.warn(String.format("Address was found, assigning user: %s to address: %s ", curSessionUser.getUsername(), address.getAddressId()));
		}
		User user = UserDAO.fetch(curSessionUser);
		user.setAddress(address);
		UserDAO.createUpdate(user, true);
		return address;
	}

	public Comment addComment(InputComment inputComment, DataFetchingEnvironment env) throws Exception {
		AuthContext context = env.getContext();
		User curSessionUser = context.getAuthUser();
		User userToUpdate = UserDAO.fetchByUsername(inputComment.getUsername());
		if (userToUpdate == null || !AuthData.checkPermission(env, userToUpdate, PermissionType.NORMAL)) {
			throw new AccessDeniedException("Comment cannot be added by the requesting user");
		}

		Comment convertedInputComment = (Comment) Comment.convertObj(inputComment.getClass().getName(), inputComment);
		
		Audit updatedAuditInfo = VirkadeModel.addAuditToModel(curSessionUser, null);
		convertedInputComment.setAudit(updatedAuditInfo);
		
		Comment comment = CommentDAO.fetch(convertedInputComment);
		if (comment == null) {
			comment = CommentDAO.create(convertedInputComment);
		}
		return comment;
	}

	public Legal addUserLegalDoc(InputLegal inputLegal, DataFetchingEnvironment env) throws Exception {
		AuthContext context = env.getContext();
		User curSessionUser = context.getAuthUser();
		User userToUpdate = UserDAO.fetchByUsername(inputLegal.getUsername());
		if (userToUpdate == null || !AuthData.checkPermission(env, userToUpdate, PermissionType.NORMAL)) {
			throw new AccessDeniedException("Legal document association cannot be modified by the requesting user");
		}
		
		Legal convertedInputLegal = (Legal) VirkadeModel.convertObj(InputLegal.class.getName(), inputLegal);
		
		Legal legal = LegalDAO.fetch(convertedInputLegal);
		if (legal == null) {
			Audit updatedAuditInfo = VirkadeModel.addAuditToModel(curSessionUser, null);
			convertedInputLegal.setAudit(updatedAuditInfo);
			legal = LegalDAO.create(convertedInputLegal);
		} else {
			Audit updatedAuditInfo = VirkadeModel.addAuditToModel(curSessionUser, legal.getAudit());
			convertedInputLegal.setAudit(updatedAuditInfo);
			convertedInputLegal.setLegalDocId(legal.getLegalDocId());
			legal = LegalDAO.update(convertedInputLegal);
		}
		return legal;
	}

	public boolean setNewPassword(String username, String passCode, String newPassword, DataFetchingEnvironment env) throws Exception {
		User userToUpdate = UserDAO.fetchByUsername(username);
		AuthContext context = env.getContext();
		User curSessionUser = context.getAuthUser();
		boolean results = false;
		if (ClientSessionTracker.isValidClientSession(username, passCode, true)) {
			userToUpdate.setPassword(VirkadeEncryptor.hashEncode(newPassword));
			Audit updatedAuditInfo = VirkadeModel.addAuditToModel(curSessionUser, userToUpdate.getAudit());
			userToUpdate.setAudit(updatedAuditInfo);
			User updatedUser = UserDAO.createUpdate(userToUpdate, true);
			results = (updatedUser != null);
			ClientSessionTracker.purgeSession(passCode, true);
		}
		return results;
	}

	public boolean setSecurityQA(String username, String securityQ, String securityA, DataFetchingEnvironment env) throws AccessDeniedException {
		AuthContext context = env.getContext();
		User curSessionUser = context.getAuthUser();
		User userToUpdate = UserDAO.fetchByUsername(username);
		if (userToUpdate == null || !AuthData.checkPermission(env, userToUpdate, PermissionType.NORMAL)) {
			throw new AccessDeniedException("User cannot be modified by the requesting user");
		}
		return false;
	}
	
	public PlaySession addSession(String username, DataFetchingEnvironment env) throws AccessDeniedException {
		AuthContext context = env.getContext();
		User curSessionUser = context.getAuthUser();
		User userToUpdate = UserDAO.fetchByUsername(username);
		if (userToUpdate == null || !AuthData.checkPermission(env, userToUpdate, PermissionType.NORMAL)) {
			throw new AccessDeniedException("Session cannot be created by the requesting user");
		}
		return null;
	}

}
