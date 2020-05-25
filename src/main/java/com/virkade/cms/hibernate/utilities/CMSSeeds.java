package com.virkade.cms.hibernate.utilities;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.virkade.cms.auth.VirkadeEncryptor;
import com.virkade.cms.hibernate.dao.ActivityDAO;
import com.virkade.cms.hibernate.dao.AddressDAO;
import com.virkade.cms.hibernate.dao.ConstantsDAO;
import com.virkade.cms.hibernate.dao.CountryDAO;
import com.virkade.cms.hibernate.dao.LocationDAO;
import com.virkade.cms.hibernate.dao.PhoneDAO;
import com.virkade.cms.hibernate.dao.RegionDAO;
import com.virkade.cms.hibernate.dao.SessionDAO;
import com.virkade.cms.hibernate.dao.StateDAO;
import com.virkade.cms.hibernate.dao.StatusDAO;
import com.virkade.cms.hibernate.dao.TypeDAO;
import com.virkade.cms.hibernate.dao.UserDAO;
import com.virkade.cms.model.Activity;
import com.virkade.cms.model.Address;
import com.virkade.cms.model.Country;
import com.virkade.cms.model.Location;
import com.virkade.cms.model.Phone;
import com.virkade.cms.model.PlaySession;
import com.virkade.cms.model.Region;
import com.virkade.cms.model.State;
import com.virkade.cms.model.Status;
import com.virkade.cms.model.Type;
import com.virkade.cms.model.User;

public class CMSSeeds {

	private static final Logger LOG = Logger.getLogger(CMSSeeds.class);
	private CMSSeeds() {

	}

	public static void createDefaultStatus() {
		Status status = new Status();

		if (StatusDAO.fetchByCode(ConstantsDAO.ACTIVE_CODE) == null) {
			status.setCode(ConstantsDAO.ACTIVE_CODE);
			status.setDescription("Status for general customer or admins");
			status.setName(ConstantsDAO.ACTIVE_NAME);
			status.getAudit().setCreatedAt(new Date());
			status.getAudit().setCreatedBy(VirkadeHibernateConstants.SYSTEM);
			status.getAudit().setUpdatedAt(new Date());
			status.getAudit().setUpdatedBy(VirkadeHibernateConstants.SYSTEM);
			StatusDAO.create(status);
		}

		if (StatusDAO.fetchByCode(ConstantsDAO.INACTIVE_CODE) == null) {
			status.setCode(ConstantsDAO.INACTIVE_CODE);
			status.setDescription("Status for customer or admin that no longer want to interact with the system");
			status.setName(ConstantsDAO.INACTIVE_NAME);
			status.getAudit().setCreatedAt(new Date());
			status.getAudit().setCreatedBy(VirkadeHibernateConstants.SYSTEM);
			status.getAudit().setUpdatedAt(new Date());
			status.getAudit().setUpdatedBy(VirkadeHibernateConstants.SYSTEM);
			StatusDAO.create(status);
		}
	}

	public static void createDefaultTypes() {
		Type type = new Type();

		if (TypeDAO.fetchByCode(ConstantsDAO.CUSTOMER_CODE) == null) {
			type.setCode(ConstantsDAO.CUSTOMER_CODE);
			type.setName("Customer User");
			type.setDescription("this is the regular type user that has an account and played at least one session");
			type.getAudit().setCreatedAt(new Date());
			type.getAudit().setCreatedBy(VirkadeHibernateConstants.SYSTEM);
			type.getAudit().setUpdatedAt(new Date());
			type.getAudit().setUpdatedBy(VirkadeHibernateConstants.SYSTEM);
			TypeDAO.create(type);
		}

		if (TypeDAO.fetchByCode(ConstantsDAO.PROSPECT_CODE) == null) {
			type.setCode(ConstantsDAO.PROSPECT_CODE);
			type.setName("Prospect User");
			type.setDescription("this is the user has created an account, but has not played a session yet");
			type.getAudit().setCreatedAt(new Date());
			type.getAudit().setCreatedBy(VirkadeHibernateConstants.SYSTEM);
			type.getAudit().setUpdatedAt(new Date());
			type.getAudit().setUpdatedBy(VirkadeHibernateConstants.SYSTEM);
			TypeDAO.create(type);
		}
		if (TypeDAO.fetchByCode(ConstantsDAO.GUEST_CODE) == null) {
			type.setCode(ConstantsDAO.GUEST_CODE);
			type.setName("Guest User");
			type.setDescription("this is the public user when no account is signed in");
			type.getAudit().setCreatedAt(new Date());
			type.getAudit().setCreatedBy(VirkadeHibernateConstants.SYSTEM);
			type.getAudit().setUpdatedAt(new Date());
			type.getAudit().setUpdatedBy(VirkadeHibernateConstants.SYSTEM);
			TypeDAO.create(type);
		}
		
		if (TypeDAO.fetchByCode(ConstantsDAO.ADMIN_CODE) == null) {
			type.setCode(ConstantsDAO.ADMIN_CODE);
			type.setName("Administrator");
			type.setDescription("this is the user that can perform super user tasks, usually an employee");
			type.getAudit().setCreatedAt(new Date());
			type.getAudit().setCreatedBy(VirkadeHibernateConstants.SYSTEM);
			type.getAudit().setUpdatedAt(new Date());
			type.getAudit().setUpdatedBy(VirkadeHibernateConstants.SYSTEM);
			TypeDAO.create(type);
		}
		if (TypeDAO.fetchByCode(ConstantsDAO.PHYSICAL_ADDRESS) == null) {
			type.setCode(ConstantsDAO.PHYSICAL_ADDRESS);
			type.setName("Physical Address");
			type.setDescription("the physical address of an entity");
			type.getAudit().setCreatedAt(new Date());
			type.getAudit().setCreatedBy(VirkadeHibernateConstants.SYSTEM);
			type.getAudit().setUpdatedAt(new Date());
			type.getAudit().setUpdatedBy(VirkadeHibernateConstants.SYSTEM);
			TypeDAO.create(type);
		}
		if (TypeDAO.fetchByCode(ConstantsDAO.MOBILE_PHONE) == null) {
			type.setCode(ConstantsDAO.MOBILE_PHONE);
			type.setName("Mobile Phone Number");
			type.setDescription("mobile network phone number");
			type.getAudit().setCreatedAt(new Date());
			type.getAudit().setCreatedBy(VirkadeHibernateConstants.SYSTEM);
			type.getAudit().setUpdatedAt(new Date());
			type.getAudit().setUpdatedBy(VirkadeHibernateConstants.SYSTEM);
			TypeDAO.create(type);
		}
		if (TypeDAO.fetchByCode(ConstantsDAO.HOME_PHONE) == null) {
			type.setCode(ConstantsDAO.HOME_PHONE);
			type.setName("Land line Phone Number");
			type.setDescription("terestrial service phone number");
			type.getAudit().setCreatedAt(new Date());
			type.getAudit().setCreatedBy(VirkadeHibernateConstants.SYSTEM);
			type.getAudit().setUpdatedAt(new Date());
			type.getAudit().setUpdatedBy(VirkadeHibernateConstants.SYSTEM);
			TypeDAO.create(type);
		}
		if (TypeDAO.fetchByCode(ConstantsDAO.CONDITION_COMMENT) == null) {
			type.setCode(ConstantsDAO.CONDITION_COMMENT);
			type.setName("User Conditions Comment");
			type.setDescription("user comments about conditions that need to be known");
			type.getAudit().setCreatedAt(new Date());
			type.getAudit().setCreatedBy(VirkadeHibernateConstants.SYSTEM);
			type.getAudit().setUpdatedAt(new Date());
			type.getAudit().setUpdatedBy(VirkadeHibernateConstants.SYSTEM);
			TypeDAO.create(type);
		}
		if (TypeDAO.fetchByCode(ConstantsDAO.GENERAL_COMMENT) == null) {
			type.setCode(ConstantsDAO.GENERAL_COMMENT);
			type.setName("General User Comments");
			type.setDescription("Comments provided by users about anything");
			type.getAudit().setCreatedAt(new Date());
			type.getAudit().setCreatedBy(VirkadeHibernateConstants.SYSTEM);
			type.getAudit().setUpdatedAt(new Date());
			type.getAudit().setUpdatedBy(VirkadeHibernateConstants.SYSTEM);
			TypeDAO.create(type);
		}
	}

	public static void createDefaultRegions() {

		if (RegionDAO.fetchByCode(ConstantsDAO.CODE_AMERICAS) == null) {
			Region region = new Region();
			region.setName(ConstantsDAO.NAME_AMERICAS);
			region.setRegionCode(ConstantsDAO.CODE_AMERICAS);
			region.setDescription("North America, central, and South America");
			region.getAudit().setCreatedAt(new Date());
			region.getAudit().setCreatedBy(VirkadeHibernateConstants.SYSTEM);
			region.getAudit().setUpdatedAt(new Date());
			region.getAudit().setUpdatedBy(VirkadeHibernateConstants.SYSTEM);
			RegionDAO.create(region);
		}
	}

	public static void createDefaultCountries() {

		if (CountryDAO.fetchByA2(ConstantsDAO.A2_UNITEDSTATES) == null) {
			Country country = new Country();
			country.setName(ConstantsDAO.NAME_UNITEDSTATES);
			country.setRegion(RegionDAO.fetchByCode(ConstantsDAO.CODE_AMERICAS));
			country.setA2(ConstantsDAO.A2_UNITEDSTATES);
			country.setA3(ConstantsDAO.A3_UNITEDSTATES);
			country.setDescription("The United States of America");
			country.getAudit().setCreatedAt(new Date());
			country.getAudit().setCreatedBy(VirkadeHibernateConstants.SYSTEM);
			country.getAudit().setUpdatedAt(new Date());
			country.getAudit().setUpdatedBy(VirkadeHibernateConstants.SYSTEM);
			CountryDAO.create(country);
		}

	}

	public static void createDefaultStates() {

		
		List<String> lines = null;
		try {
			Path path = Paths.get(ClassLoader.getSystemResource("stateData.csv").toURI());
			lines = Files.readAllLines(path);
		}catch (InvalidPathException | IOException | URISyntaxException  e) {
			LOG.error(e);
		}
		for (int i=0; i < lines.size(); i++) {
			if (i == 0) continue;
			String[] valuesArray = lines.get(i).split(",");
			if (StateDAO.fetchByCode(valuesArray[2]) == null) {
				State state = new State();
				state.setName(valuesArray[0]);
				state.setAbbreviation(valuesArray[1]);
				state.setStateCode(valuesArray[2]);
				state.setCountry(CountryDAO.fetchByA2(valuesArray[3]));
				state.getAudit().setCreatedAt(new Date());
				state.getAudit().setCreatedBy(VirkadeHibernateConstants.SYSTEM);
				state.getAudit().setUpdatedAt(new Date());
				state.getAudit().setUpdatedBy(VirkadeHibernateConstants.SYSTEM);
				State newState = StateDAO.create(state);
				if (newState != null) {
					LOG.info(valuesArray[0] + " : state created ");
				}
			}else {
				LOG.warn(valuesArray[0] + " : state already exists ");
			}
		}

	}

	public static void createDefaultAddress() {
		if (AddressDAO.fetch(AddressDAO.ORIGINAL_LOCATION) == null) {
			Address address = AddressDAO.ORIGINAL_LOCATION;
			address.getAudit().setCreatedAt(new Date());
			address.getAudit().setCreatedBy(VirkadeHibernateConstants.SYSTEM);
			address.getAudit().setUpdatedAt(new Date());
			address.getAudit().setUpdatedBy(VirkadeHibernateConstants.SYSTEM);
			AddressDAO.create(address);
		}
	}

	public static void createDefaultLocation() {
		if (LocationDAO.fetchByName(ConstantsDAO.ORIGINAL_LOCATION_NAME) == null) {
			Location location = new Location();
			location.setName(ConstantsDAO.ORIGINAL_LOCATION_NAME);
			location.setAddress(AddressDAO.fetchById(AddressDAO.ORIGINAL_LOCATION_ID));
			location.setDescription("the first VirKade");
			location.setManager("Zachary Sigmon");
			location.setPhoneNum(4792632216L);
			location.setEnabled(Boolean.TRUE);
			location.getAudit().setCreatedAt(new Date());
			location.getAudit().setCreatedBy(VirkadeHibernateConstants.SYSTEM);
			location.getAudit().setUpdatedAt(new Date());
			location.getAudit().setUpdatedBy(VirkadeHibernateConstants.SYSTEM);
			LocationDAO.create(location);
		}
	}

	public static void createDefaultUsers() {
		User user = new User();
		User user2 = new User();

		if (UserDAO.fetchByUsername(ConstantsDAO.GUEST_USER_NAME) == null) {
			user.setFirstName(ConstantsDAO.GUEST_USER_NAME);
			user.setLastName(ConstantsDAO.GUEST_USER_NAME);
			user.setUsername(ConstantsDAO.GUEST_USER_NAME);
			user.setAddress(AddressDAO.fetchById(AddressDAO.ORIGINAL_LOCATION_ID));
			user.setEmailAddress(ConstantsDAO.GUEST_EMAIL_ADDRESS);
			user.setPassword(VirkadeEncryptor.hashEncode("123456"));
			user.setStatus(StatusDAO.fetchByCode(ConstantsDAO.ACTIVE_CODE));
			user.setType(TypeDAO.fetchByCode(ConstantsDAO.GUEST_CODE));
			user.getAudit().setCreatedAt(new Date());
			user.getAudit().setCreatedBy(VirkadeHibernateConstants.SYSTEM);
			user.getAudit().setUpdatedAt(new Date());
			user.getAudit().setUpdatedBy(VirkadeHibernateConstants.SYSTEM);
			UserDAO.createUpdate(user, false);
			
			Phone phone = new Phone();
			phone.setUser(user);
			phone.setCountryCode(1);
			phone.setNumber("4795445445");
			phone.setType(TypeDAO.fetchByCode(ConstantsDAO.HOME_PHONE));
			phone.getAudit().setCreatedAt(new Date());
			phone.getAudit().setCreatedBy(VirkadeHibernateConstants.SYSTEM);
			phone.getAudit().setUpdatedAt(new Date());
			phone.getAudit().setUpdatedBy(VirkadeHibernateConstants.SYSTEM);
			PhoneDAO.create(phone);
		}
		if (UserDAO.fetchByUsername(ConstantsDAO.OWNER_USER_NAME) == null) {
			user2.setFirstName(ConstantsDAO.OWNER_USER_NAME);
			user2.setLastName(ConstantsDAO.OWNER_USER_NAME);
			user2.setUsername(ConstantsDAO.OWNER_USER_NAME);
			user2.setAddress(AddressDAO.fetchById(AddressDAO.ORIGINAL_LOCATION_ID));
			user2.setEmailAddress(ConstantsDAO.GUEST_EMAIL_ADDRESS);
			user2.setPassword(VirkadeEncryptor.hashEncode(ConstantsDAO.DEFAULT_OWNER_PASSWORD));
			user2.setStatus(StatusDAO.fetchByCode(ConstantsDAO.ACTIVE_CODE));
			user2.setType(TypeDAO.fetchByCode(ConstantsDAO.ADMIN_CODE));
			user2.getAudit().setCreatedAt(new Date());
			user2.getAudit().setCreatedBy(VirkadeHibernateConstants.SYSTEM);
			user2.getAudit().setUpdatedAt(new Date());
			user2.getAudit().setUpdatedBy(VirkadeHibernateConstants.SYSTEM);
			UserDAO.createUpdate(user2, false);
			
			Phone phone = new Phone();
			phone.setUser(user2);
			phone.setCountryCode(1);
			phone.setNumber("4795445445");
			phone.setType(TypeDAO.fetchByCode(ConstantsDAO.MOBILE_PHONE));
			phone.getAudit().setCreatedAt(new Date());
			phone.getAudit().setCreatedBy(VirkadeHibernateConstants.SYSTEM);
			phone.getAudit().setUpdatedAt(new Date());
			phone.getAudit().setUpdatedBy(VirkadeHibernateConstants.SYSTEM);
			PhoneDAO.create(phone);
			
			phone.setUser(user);
			phone.setCountryCode(1);
			phone.setNumber("4792632216");
			phone.setType(TypeDAO.fetchByCode(ConstantsDAO.MOBILE_PHONE));
			phone.getAudit().setCreatedAt(new Date());
			phone.getAudit().setCreatedBy(VirkadeHibernateConstants.SYSTEM);
			phone.getAudit().setUpdatedAt(new Date());
			phone.getAudit().setUpdatedBy(VirkadeHibernateConstants.SYSTEM);
			PhoneDAO.create(phone);
			
		}
	}

	public static void createTestGame() {
		
		Activity activity = new Activity();
		activity.setCostpm(0.85);
		activity.setCreator("HTC");
		activity.setName("Vive Port");
		activity.setDescription("the default vr arcade for the vive");
		activity.setEnabled(true);
		activity.setSupportContact("https://service.viveport.com/hc/en-us/requests/new?v_to_v&");
		activity.setWebSite("https://www.viveport.com");
		activity.getAudit().setCreatedAt(new Date());
		activity.getAudit().setCreatedBy(VirkadeHibernateConstants.SYSTEM);
		activity.getAudit().setUpdatedAt(new Date());
		activity.getAudit().setUpdatedBy(VirkadeHibernateConstants.SYSTEM);
		Activity testActivity = ActivityDAO.fetchByName(activity.getName());
		if (testActivity == null) {
			ActivityDAO.create(activity);
		} else {
			LOG.info(String.format("Seed name with %s already exists", activity.getName()));
		}
		

	}

	public static void createTestSession() {
		List<Activity> activities = new ArrayList<>();
		activities.add(ActivityDAO.fetchByName("Vive Port"));
		User user = UserDAO.fetchByUsername(ConstantsDAO.GUEST_USER_NAME);
		Location location = LocationDAO.fetchByName(ConstantsDAO.ORIGINAL_LOCATION_NAME);
		
		
		PlaySession session = new PlaySession();
		session.setUser(user);
		session.setLocation(location);
		session.setActivities(activities);
		session.setStartDate(new Date());
		session.setEndDate(new Date());
		session.getAudit().setCreatedAt(new Date());
		session.getAudit().setCreatedBy(VirkadeHibernateConstants.SYSTEM);
		session.getAudit().setUpdatedAt(new Date());
		session.getAudit().setUpdatedBy(VirkadeHibernateConstants.SYSTEM);
		SessionDAO.create(session);
		
		user = UserDAO.fetchByUsername(ConstantsDAO.OWNER_USER_NAME);
		session.setUser(user);
		session.setLocation(location);
		session.setActivities(activities);
		session.setStartDate(new Date());
		session.setEndDate(new Date());
		session.getAudit().setCreatedAt(new Date());
		session.getAudit().setCreatedBy(VirkadeHibernateConstants.SYSTEM);
		session.getAudit().setUpdatedAt(new Date());
		session.getAudit().setUpdatedBy(VirkadeHibernateConstants.SYSTEM);
		SessionDAO.create(session);

	}

}
