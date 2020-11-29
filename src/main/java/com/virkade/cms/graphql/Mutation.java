/**
 * 
 */
package com.virkade.cms.graphql;

import java.nio.file.AccessDeniedException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.InputMismatchException;
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
import com.virkade.cms.hibernate.dao.ActivityDAO;
import com.virkade.cms.hibernate.dao.AddressDAO;
import com.virkade.cms.hibernate.dao.CommentDAO;
import com.virkade.cms.hibernate.dao.ConstantsDAO;
import com.virkade.cms.hibernate.dao.LegalDAO;
import com.virkade.cms.hibernate.dao.LocationDAO;
import com.virkade.cms.hibernate.dao.PhoneDAO;
import com.virkade.cms.hibernate.dao.PlaySessionDAO;
import com.virkade.cms.hibernate.dao.StatusDAO;
import com.virkade.cms.hibernate.dao.TransactionDAO;
import com.virkade.cms.hibernate.dao.TypeDAO;
import com.virkade.cms.hibernate.dao.UserDAO;
import com.virkade.cms.model.Activity;
import com.virkade.cms.model.Address;
import com.virkade.cms.model.Audit;
import com.virkade.cms.model.Comment;
import com.virkade.cms.model.InputActivity;
import com.virkade.cms.model.InputAddress;
import com.virkade.cms.model.InputComment;
import com.virkade.cms.model.InputLegal;
import com.virkade.cms.model.InputLocation;
import com.virkade.cms.model.InputPhone;
import com.virkade.cms.model.InputPlaySession;
import com.virkade.cms.model.InputTransaction;
import com.virkade.cms.model.InputUser;
import com.virkade.cms.model.Legal;
import com.virkade.cms.model.Location;
import com.virkade.cms.model.Phone;
import com.virkade.cms.model.PlaySession;
import com.virkade.cms.model.Transaction;
import com.virkade.cms.model.Type;
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

		if (convertedInputUser.getUsername() != null && convertedInputUser.getUsername().length() > 5) {
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
		if (convertedInputUser.getPassword() != null && convertedInputUser.getPassword().length() > 7) {
			userToUpdate.setPassword(VirkadeEncryptor.hashEncode(convertedInputUser.getPassword()));
		}
		if (convertedInputUser.getSecurityQuestion() != null) {
			userToUpdate.setSecurityQuestion(convertedInputUser.getSecurityQuestion());
		}
		if (convertedInputUser.getSecurityAnswer() != null && !convertedInputUser.getSecurityAnswer().equals("")) {
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

		boolean refreshLegals = false;
		if (inputUser.isTcAgree() != null && inputUser.isTcAgree() != userToUpdate.isTcAgree()) {
			Calendar cal = Calendar.getInstance();
			Timestamp now = new Timestamp(cal.getTimeInMillis());

			cal.set(Calendar.YEAR, (cal.get(Calendar.YEAR) + 1));
			Timestamp exp = new Timestamp(cal.getTimeInMillis());

			if (inputUser.isTcAgree()) {
				InputLegal newLegal = new InputLegal();
				newLegal.setActiveDate(now);
				newLegal.setAgree(true);
				newLegal.setEnabled(true);
				newLegal.setExpireDate(exp);
				newLegal.setTypeCode(ConstantsDAO.TERMS_CONDITIONS);
				newLegal.setUsername(userToUpdate.getUsername());
				addUserLegalDoc(newLegal, env);
			} else {
				List<Legal> tcLegal = userToUpdate.getActiveTCLegal();
				for (Legal curLegal : tcLegal) {
					curLegal.setAgree(inputUser.isTcAgree());
					LegalDAO.update(curLegal);
				}
			}
			refreshLegals = true;
		}

		if (inputUser.isLiableAgree() != null && inputUser.isLiableAgree() != userToUpdate.isLiableAgree()) {
			Calendar cal = Calendar.getInstance();
			Timestamp now = new Timestamp(cal.getTimeInMillis());

			cal.set(Calendar.YEAR, (cal.get(Calendar.YEAR) + 1));
			Timestamp exp = new Timestamp(cal.getTimeInMillis());

			if (inputUser.isLiableAgree()) {
				InputLegal newLegal = new InputLegal();
				newLegal.setActiveDate(now);
				newLegal.setAgree(true);
				newLegal.setEnabled(true);
				newLegal.setExpireDate(exp);
				newLegal.setTypeCode(ConstantsDAO.LIMITED_LIABLE);
				newLegal.setUsername(userToUpdate.getUsername());
				addUserLegalDoc(newLegal, env);
			} else {
				List<Legal> liabLegal = userToUpdate.getActiveLiabLegal();
				for (Legal curLegal : liabLegal) {
					curLegal.setAgree(inputUser.isLiableAgree());
					LegalDAO.update(curLegal);
				}
			}
			refreshLegals = true;

		}
		if (refreshLegals) {
			userToUpdate.setLegals(UserDAO.fetchByUsername(convertedInputUser.getUsername()).getLegals());
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

	public User updateUserType(long userId, String typeCode, DataFetchingEnvironment env) throws Exception {
		AuthContext context = env.getContext();
		User curSessionUser = context.getAuthUser();

		User userToUpdate = new User();
		userToUpdate.setUserId(userId);
		userToUpdate = UserDAO.fetch(userToUpdate);

		if (userToUpdate == null || !AuthData.checkPermission(env, userToUpdate, PermissionType.ADMIN)) {
			throw new AccessDeniedException("User cannot be modified by the requesting user");
		}

		if (typeCode != null) {
			Type type = TypeDAO.getByCode(typeCode);
			userToUpdate.setType(type);
		} else {
			throw new Exception("User type to update is not valid");
		}

		Audit updatedAuditInfo = VirkadeModel.addAuditToModel(curSessionUser, userToUpdate.getAudit());
		userToUpdate.setAudit(updatedAuditInfo);

		return UserDAO.createUpdate(userToUpdate, true);
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

		User user = UserDAO.fetch(userToUpdate);
		List<Phone> curPhoneNumbers = user.getPhoneNumbers();
		Phone phone = PhoneDAO.fetch(convertedInputPhone);

		if (phone == null) {
			convertedInputPhone.setUser(user);
			phone = PhoneDAO.create(convertedInputPhone);
		} else {
			List<Phone> newPhoneNumbers = new ArrayList<>();
			for (Phone curPhone : curPhoneNumbers) {
				if (curPhone.getType().getTypeId() != convertedInputPhone.getType().getTypeId()) {
					newPhoneNumbers.add(curPhone);
				}
			}
			phone.setUser(user);
			newPhoneNumbers.add(phone);
			user.setPhoneNumbers(newPhoneNumbers);
			user = UserDAO.createUpdate(user, true);
		}

		for (Phone curPhone : user.getPhoneNumbers()) {
			if (curPhone.getType().getTypeId() == convertedInputPhone.getType().getTypeId() && curPhone.getNumber().equals(convertedInputPhone.getNumber())
					&& curPhone.getCountryCode() == convertedInputPhone.getCountryCode()) {
				phone = curPhone;
			}
		}
		return phone;
	}

	public Address addUserAddress(InputAddress inputAddress, long userId, DataFetchingEnvironment env) throws Exception {
		AuthContext context = env.getContext();
		User curSessionUser = context.getAuthUser();
		if (!AuthData.checkPermission(env, curSessionUser, PermissionType.NORMAL)) {
			throw new AccessDeniedException("Comment cannot be added by the requesting user");
		}
		if (inputAddress.getPostalCode() != null && inputAddress.getPostalCode().length() > 0) {
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
		User userToUpdate = new User();
		if (userId == 0) {
			userToUpdate = curSessionUser;
		} else {
			userToUpdate.setUserId(userId);
		}
		userToUpdate = UserDAO.fetch(userToUpdate);
		userToUpdate.setAddress(address);
		UserDAO.createUpdate(userToUpdate, true);
		return address;
	}

	public Comment addComment(InputComment inputComment, DataFetchingEnvironment env) throws Exception {
		AuthContext context = env.getContext();
		User curSessionUser = context.getAuthUser();
		if (inputComment.getCommentContent() == null || inputComment.getCommentContent() == "") {
			throw new InputMismatchException("Comment content cannot be empty");
		}
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

	public PlaySession addUserSession(InputPlaySession inputPlaySession, DataFetchingEnvironment env) throws Exception {
		AuthContext context = env.getContext();
		User curSessionUser = context.getAuthUser();

		List<String> missingData = new ArrayList<String>();
		PlaySession convertedInputPlaySession = (PlaySession) VirkadeModel.convertObj(inputPlaySession.getClass().getName(), inputPlaySession);

		if (convertedInputPlaySession.getStartDate() == null) {
			missingData.add("StartDate");
		}
		if (convertedInputPlaySession.getEndDate() == null) {
			missingData.add("EndDate");
		}
		if (convertedInputPlaySession.getUser() == null) {
			missingData.add("User");
		}
		if (convertedInputPlaySession.getLocation() == null) {
			missingData.add("Location");
		}
		if (convertedInputPlaySession.getActivity() == null) {
			missingData.add("Activity");
		}
		if (!missingData.isEmpty()) {
			throw new Exception("Creation of play session for user not allowed with the following missing data [" + missingData.toString() + "]");
		}

		if (convertedInputPlaySession.getUser() == null || !AuthData.checkPermission(env, convertedInputPlaySession.getUser(), PermissionType.NORMAL)) {
			throw new AccessDeniedException("Session cannot be created by the requesting user");
		}

		Audit audit = VirkadeModel.addAuditToModel(curSessionUser, convertedInputPlaySession.getAudit());
		convertedInputPlaySession.setAudit(audit);

		PlaySession playSession = PlaySessionDAO.create(convertedInputPlaySession);

		return playSession;
	}

	public boolean deleteAllSessions(Timestamp dateRequested, String locationName, String activityName, DataFetchingEnvironment env) throws Exception {
		boolean results = false;
		if (!AuthData.checkPermission(env, null, PermissionType.ADMIN)) {
			throw new AccessDeniedException("You must be logged in as admin to perform this function");
		}
		Location location = null;
		Activity activity = null;
		if (activityName == null || activityName == "") {
			activity = ActivityDAO.getDefault();
		} else {
			activity = ActivityDAO.fetchByName(activityName);
		}
		if (locationName == null || locationName == "") {
			location = LocationDAO.getDefault();
		} else {
			location = LocationDAO.fetchByName(locationName, true);
		}
		if (location == null || activity == null) {
			throw new Exception("location or activity not found,  if you are looking for the default then pass null values for location and activity");
		}
		results = PlaySessionDAO.deleteAll(dateRequested, location, activity);
		return results;
	}

	public Location addUpdateLocation(InputLocation inputLocation, DataFetchingEnvironment env) throws Exception {
		AuthContext context = env.getContext();
		User curSessionUser = context.getAuthUser();
		List<String> missingData = new ArrayList<String>();
		if (!AuthData.checkPermission(env, null, PermissionType.ADMIN)) {
			throw new AccessDeniedException("You must be logged in as admin to perform this function");
		}
		if (inputLocation.getPostalCode() != null) {
			Pattern regex = Pattern.compile("^[0-9]{5}(?:-[0-9]{4})?$");
			Matcher matcher = regex.matcher(inputLocation.getPostalCode());
			if (!matcher.matches()) {
				missingData.add("postal code does not match expected pattern of 55555 or 55555-5555");
			}
		} else {
			missingData.add("postal code cannot be empty");
		}
		if (inputLocation.getName() == null || inputLocation.getName() == "" || inputLocation.getName().length() < 6) {
			missingData.add("name cannot be empty and must be at least 6 characters");
		}
		if (inputLocation.getDescription() == null || inputLocation.getDescription() == "" || inputLocation.getDescription().length() < 15) {
			missingData.add("description cannot be empty and must be at least 15 characters");

		}
		if (inputLocation.getCity() == null || inputLocation.getCity() == "") {
			missingData.add("city cannot be empty");
		}
		if (inputLocation.getStreet() == null || inputLocation.getStreet() == "") {
			missingData.add("street cannot be empty");
		}
		if (inputLocation.getPhoneNum() == null || inputLocation.getPhoneNum() == "" || inputLocation.getPhoneNum().length() < 7) {
			missingData.add("phone number needs to be a valid mobile phone number");
		}
		if (inputLocation.getManager() == null || inputLocation.getManager() == "") {
			missingData.add("manager cannot be empty");
		}
		if (inputLocation.getTaxRate() <= 0) {
			missingData.add("tax rate cannot be less than or equal to 0");
		}
		if (inputLocation.getStateId() <= 0) {
			missingData.add("must be a valid state id");
		}

		if (!missingData.isEmpty()) {
			throw new Exception("location creation or update not allowed with the following data issues [" + missingData.toString() + "]");
		}

		Location existLocation = LocationDAO.fetchById(inputLocation.getLocationId());
		if (existLocation == null) {
			existLocation = new Location();
		}
		Location location = (Location) VirkadeModel.convertObj(inputLocation.getClass().getName(), inputLocation);

		Audit auditInfo = VirkadeModel.addAuditToModel(curSessionUser, existLocation.getAudit());
		location.setAudit(auditInfo);

		if (location.getAddress().getAddressId() <= 0) {
			Address address = location.getAddress();
			Audit addressAudit = VirkadeModel.addAuditToModel(curSessionUser, address.getAudit());
			address.setAudit(addressAudit);
			location.setAddress(AddressDAO.create(address));
		}

		return LocationDAO.upsert(location);
	}

	public Activity addUpdateActivity(InputActivity inputActivity, DataFetchingEnvironment env) throws Exception {
		AuthContext context = env.getContext();
		User curSessionUser = context.getAuthUser();
		List<String> missingData = new ArrayList<String>();
		if (!AuthData.checkPermission(env, null, PermissionType.ADMIN)) {
			throw new AccessDeniedException("You must be logged in as admin to perform this function");
		}
		if (inputActivity.getName() == null || inputActivity.getName() == "" || inputActivity.getName().length() < 6) {
			missingData.add("name cannot be empty and must be at least 6 characters");
		}
		if (inputActivity.getDescription() == null || inputActivity.getDescription() == "" || inputActivity.getDescription().length() < 15) {
			missingData.add("description cannot be empty and must be at least 15 characters");
		}
		if (inputActivity.getCostpm() <= 0) {
			missingData.add("cost per min cannot be less than or equal to 0");
		}
		if (inputActivity.getSetupMin() <= 0) {
			missingData.add("setup minutes cannot be less than or equal to 0");
		}
		if (inputActivity.getSupportContact() == null || inputActivity.getSupportContact() == "") {
			missingData.add("support contact cannot be empty");
		}
		if (inputActivity.getWebSite() == null || inputActivity.getWebSite() == "") {
			missingData.add("website cannot be empty");
		}
		if (inputActivity.getCreator() == null || inputActivity.getCreator() == "") {
			missingData.add("creator cannot be empty");
		}

		if (!missingData.isEmpty()) {
			throw new Exception("activity creation or update not allowed with the following data issues [" + missingData.toString() + "]");
		}

		Activity existActivity = ActivityDAO.fetchById(inputActivity.getActivityId());
		if (existActivity == null) {
			existActivity = new Activity();
		}
		Activity activity = (Activity) VirkadeModel.convertObj(inputActivity.getClass().getName(), inputActivity);

		Audit auditInfo = VirkadeModel.addAuditToModel(curSessionUser, existActivity.getAudit());
		activity.setAudit(auditInfo);

		return ActivityDAO.upsert(activity);
	}

	public Transaction addUpdateTransaction(InputTransaction inputTransaction, DataFetchingEnvironment env) throws Exception {
		AuthContext context = env.getContext();
		User curSessionUser = context.getAuthUser();
		List<String> missingData = new ArrayList<String>();
		if (!AuthData.checkPermission(env, null, PermissionType.ADMIN)) {
			throw new AccessDeniedException("You must be logged in as admin to perform this function");
		}
		if (inputTransaction.getSessionIds() == null || inputTransaction.getSessionIds().isEmpty()) {
			missingData.add("session ids cannot be empty");
		}
		if (inputTransaction.getPayment() <= 1) {
			missingData.add("payment must be a valid amount");
		}
		if (inputTransaction.getRefId() == null || inputTransaction.getRefId() == "" || inputTransaction.getRefId().length() < 4) {
			missingData.add("reference id must be a valid identifier");
		}
		if (inputTransaction.getServiceName() == null || inputTransaction.getServiceName().length() < 3) {
			missingData.add("must be a valid payment type");
		}
		if (!missingData.isEmpty()) {
			throw new Exception("transaction creation or update not allowed with the following data issues [" + missingData.toString() + "]");
		}

		Transaction transaction = (Transaction) VirkadeModel.convertObj(inputTransaction.getClass().getName(), inputTransaction);

		if (transaction.getSessions().size() != inputTransaction.getSessionIds().size()) {
			List<Long> missingSessions = inputTransaction.getSessionIds();
			for (PlaySession curSession : transaction.getSessions()) {
				if (missingSessions.contains(curSession.getSessionId())) {
					missingSessions.remove(curSession.getSessionId());
				}
			}
			throw new Exception("transaction creation or update cannot occur if corresponding sessions for sessionIds cannot be found, missing sessions for IDs: " + missingSessions.toString());
		}

		Transaction existTransaction = TransactionDAO.fetchById(inputTransaction.getTransactionId());
		if (existTransaction == null) {
			existTransaction = new Transaction();
		} else {
			transaction.setTransactionId(existTransaction.getTransactionId());
		}

		Audit auditInfo = VirkadeModel.addAuditToModel(curSessionUser, existTransaction.getAudit());
		transaction.setAudit(auditInfo);

		List<PlaySession> existingSessions = transaction.getSessions();
		List<PlaySession> payedSessions = new ArrayList<>();
		for (PlaySession curSession : existingSessions) {
			curSession.setPayed(true);
			payedSessions.add(curSession);
		}
		transaction.setSessions(payedSessions);

		Transaction updatedTransaction = TransactionDAO.upsert(transaction);
		/*
		 * List<PlaySession> payedSessions = updatedTransaction.getSessions(); for (PlaySession curSession : payedSessions) { curSession.setPayed(true);
		 * SessionDAO.update(curSession); }
		 */
		return updatedTransaction;
	}

}
