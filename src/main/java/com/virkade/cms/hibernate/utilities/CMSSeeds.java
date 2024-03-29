package com.virkade.cms.hibernate.utilities;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.virkade.cms.DocUtil;
import com.virkade.cms.PropsUtil;
import com.virkade.cms.auth.VirkadeEncryptor;
import com.virkade.cms.hibernate.dao.ActivityDAO;
import com.virkade.cms.hibernate.dao.AddressDAO;
import com.virkade.cms.hibernate.dao.ConstantsDAO;
import com.virkade.cms.hibernate.dao.CountryDAO;
import com.virkade.cms.hibernate.dao.DocumentDAO;
import com.virkade.cms.hibernate.dao.LegalDAO;
import com.virkade.cms.hibernate.dao.LocationDAO;
import com.virkade.cms.hibernate.dao.PhoneDAO;
import com.virkade.cms.hibernate.dao.PlaySessionDAO;
import com.virkade.cms.hibernate.dao.RegionDAO;
import com.virkade.cms.hibernate.dao.StateDAO;
import com.virkade.cms.hibernate.dao.StatusDAO;
import com.virkade.cms.hibernate.dao.TypeDAO;
import com.virkade.cms.hibernate.dao.UserDAO;
import com.virkade.cms.model.Activity;
import com.virkade.cms.model.Address;
import com.virkade.cms.model.Audit;
import com.virkade.cms.model.Country;
import com.virkade.cms.model.Document;
import com.virkade.cms.model.Legal;
import com.virkade.cms.model.Location;
import com.virkade.cms.model.Phone;
import com.virkade.cms.model.PlaySession;
import com.virkade.cms.model.Region;
import com.virkade.cms.model.State;
import com.virkade.cms.model.Status;
import com.virkade.cms.model.Type;
import com.virkade.cms.model.User;
import com.virkade.cms.model.VirkadeModel;

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
			status.getAudit().setCreatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			status.getAudit().setCreatedBy(ConstantsDAO.SYSTEM);
			status.getAudit().setUpdatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			status.getAudit().setUpdatedBy(ConstantsDAO.SYSTEM);
			StatusDAO.create(status);
		}

		if (StatusDAO.fetchByCode(ConstantsDAO.INACTIVE_CODE) == null) {
			status.setCode(ConstantsDAO.INACTIVE_CODE);
			status.setDescription("Status for customer or admin that no longer want to interact with the system");
			status.setName(ConstantsDAO.INACTIVE_NAME);
			status.getAudit().setCreatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			status.getAudit().setCreatedBy(ConstantsDAO.SYSTEM);
			status.getAudit().setUpdatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			status.getAudit().setUpdatedBy(ConstantsDAO.SYSTEM);
			StatusDAO.create(status);
		}
	}

	public static void createDefaultTypes() {
		Type type = new Type();

		if (TypeDAO.fetchByCode(ConstantsDAO.CUSTOMER_CODE) == null) {
			type.setCode(ConstantsDAO.CUSTOMER_CODE);
			type.setName("Customer User");
			type.setDescription("this is the regular type user that has an account and played at least one session");
			type.getAudit().setCreatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			type.getAudit().setCreatedBy(ConstantsDAO.SYSTEM);
			type.getAudit().setUpdatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			type.getAudit().setUpdatedBy(ConstantsDAO.SYSTEM);
			TypeDAO.create(type);
		}

		if (TypeDAO.fetchByCode(ConstantsDAO.PROSPECT_CODE) == null) {
			type.setCode(ConstantsDAO.PROSPECT_CODE);
			type.setName("Prospect User");
			type.setDescription("this is the user has created an account, but has not played a session yet");
			type.getAudit().setCreatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			type.getAudit().setCreatedBy(ConstantsDAO.SYSTEM);
			type.getAudit().setUpdatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			type.getAudit().setUpdatedBy(ConstantsDAO.SYSTEM);
			TypeDAO.create(type);
		}
		if (TypeDAO.fetchByCode(ConstantsDAO.GUEST_CODE) == null) {
			type.setCode(ConstantsDAO.GUEST_CODE);
			type.setName("Guest User");
			type.setDescription("this is the public user when no account is signed in");
			type.getAudit().setCreatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			type.getAudit().setCreatedBy(ConstantsDAO.SYSTEM);
			type.getAudit().setUpdatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			type.getAudit().setUpdatedBy(ConstantsDAO.SYSTEM);
			TypeDAO.create(type);
		}

		if (TypeDAO.fetchByCode(ConstantsDAO.ADMIN_CODE) == null) {
			type.setCode(ConstantsDAO.ADMIN_CODE);
			type.setName("Administrator");
			type.setDescription("this is the user that can perform super user tasks, usually an employee");
			type.getAudit().setCreatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			type.getAudit().setCreatedBy(ConstantsDAO.SYSTEM);
			type.getAudit().setUpdatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			type.getAudit().setUpdatedBy(ConstantsDAO.SYSTEM);
			TypeDAO.create(type);
		}
		if (TypeDAO.fetchByCode(ConstantsDAO.PHYSICAL_ADDRESS) == null) {
			type.setCode(ConstantsDAO.PHYSICAL_ADDRESS);
			type.setName("Physical Address");
			type.setDescription("the physical address of an entity");
			type.getAudit().setCreatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			type.getAudit().setCreatedBy(ConstantsDAO.SYSTEM);
			type.getAudit().setUpdatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			type.getAudit().setUpdatedBy(ConstantsDAO.SYSTEM);
			TypeDAO.create(type);
		}
		if (TypeDAO.fetchByCode(ConstantsDAO.MOBILE_PHONE) == null) {
			type.setCode(ConstantsDAO.MOBILE_PHONE);
			type.setName("Mobile Phone Number");
			type.setDescription("mobile network phone number");
			type.getAudit().setCreatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			type.getAudit().setCreatedBy(ConstantsDAO.SYSTEM);
			type.getAudit().setUpdatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			type.getAudit().setUpdatedBy(ConstantsDAO.SYSTEM);
			TypeDAO.create(type);
		}
		if (TypeDAO.fetchByCode(ConstantsDAO.HOME_PHONE) == null) {
			type.setCode(ConstantsDAO.HOME_PHONE);
			type.setName("Land line Phone Number");
			type.setDescription("terestrial service phone number");
			type.getAudit().setCreatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			type.getAudit().setCreatedBy(ConstantsDAO.SYSTEM);
			type.getAudit().setUpdatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			type.getAudit().setUpdatedBy(ConstantsDAO.SYSTEM);
			TypeDAO.create(type);
		}
		if (TypeDAO.fetchByCode(ConstantsDAO.CONDITION_COMMENT) == null) {
			type.setCode(ConstantsDAO.CONDITION_COMMENT);
			type.setName("User Conditions Comment");
			type.setDescription("user comments about conditions that need to be known");
			type.getAudit().setCreatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			type.getAudit().setCreatedBy(ConstantsDAO.SYSTEM);
			type.getAudit().setUpdatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			type.getAudit().setUpdatedBy(ConstantsDAO.SYSTEM);
			TypeDAO.create(type);
		}
		if (TypeDAO.fetchByCode(ConstantsDAO.GENERAL_COMMENT) == null) {
			type.setCode(ConstantsDAO.GENERAL_COMMENT);
			type.setName("General User Comments");
			type.setDescription("Comments provided by users about anything");
			type.getAudit().setCreatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			type.getAudit().setCreatedBy(ConstantsDAO.SYSTEM);
			type.getAudit().setUpdatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			type.getAudit().setUpdatedBy(ConstantsDAO.SYSTEM);
			TypeDAO.create(type);
		}
		if (TypeDAO.fetchByCode(ConstantsDAO.TERMS_CONDITIONS) == null) {
			type.setCode(ConstantsDAO.TERMS_CONDITIONS);
			type.setName("terms and conditions");
			type.setDescription("the legal document for terms and conditions");
			type.getAudit().setCreatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			type.getAudit().setCreatedBy(ConstantsDAO.SYSTEM);
			type.getAudit().setUpdatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			type.getAudit().setUpdatedBy(ConstantsDAO.SYSTEM);
			TypeDAO.create(type);
		}
		if (TypeDAO.fetchByCode(ConstantsDAO.LIMITED_LIABLE) == null) {
			type.setCode(ConstantsDAO.LIMITED_LIABLE);
			type.setName("limited liablity");
			type.setDescription("the legal document for limited liability waiver");
			type.getAudit().setCreatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			type.getAudit().setCreatedBy(ConstantsDAO.SYSTEM);
			type.getAudit().setUpdatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			type.getAudit().setUpdatedBy(ConstantsDAO.SYSTEM);
			TypeDAO.create(type);
		}
	}

	public static void createDefaultRegions() {

		if (RegionDAO.fetchByCode(ConstantsDAO.CODE_AMERICAS) == null) {
			Region region = new Region();
			region.setName(ConstantsDAO.NAME_AMERICAS);
			region.setRegionCode(ConstantsDAO.CODE_AMERICAS);
			region.setDescription("North America, central, and South America");
			region.getAudit().setCreatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			region.getAudit().setCreatedBy(ConstantsDAO.SYSTEM);
			region.getAudit().setUpdatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			region.getAudit().setUpdatedBy(ConstantsDAO.SYSTEM);
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
			country.getAudit().setCreatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			country.getAudit().setCreatedBy(ConstantsDAO.SYSTEM);
			country.getAudit().setUpdatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			country.getAudit().setUpdatedBy(ConstantsDAO.SYSTEM);
			CountryDAO.create(country);
		}

	}

	public static void createDefaultStates() {

		List<String> lines = null;
		try {
			LOG.info("getting state data resource: stateData.csv");
			Path resource = Paths.get("config", "stateData.csv");
			if (!Files.exists(resource)) {
				LOG.warn("could not find external resource, looking in project");
				URL internalResource = CMSSeeds.class.getClassLoader().getResource(resource.toString());
				resource = Paths.get(internalResource.toURI());
			}
			lines = Files.readAllLines(resource);
		} catch (InvalidPathException | IOException | URISyntaxException e) {
			LOG.error(e);
		}
		for (int i = 0; i < lines.size(); i++) {
			if (i == 0)
				continue;
			String[] valuesArray = lines.get(i).split(",");
			if (StateDAO.fetchByCode(valuesArray[2]) == null) {
				State state = new State();
				state.setName(valuesArray[0]);
				state.setAbbreviation(valuesArray[1]);
				state.setStateCode(valuesArray[2]);
				state.setCountry(CountryDAO.fetchByA2(valuesArray[3]));
				state.getAudit().setCreatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
				state.getAudit().setCreatedBy(ConstantsDAO.SYSTEM);
				state.getAudit().setUpdatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
				state.getAudit().setUpdatedBy(ConstantsDAO.SYSTEM);
				State newState = StateDAO.create(state);
				if (newState != null) {
					LOG.info(valuesArray[0] + " : state created ");
				}
			} else {
				LOG.warn(valuesArray[0] + " : state already exists ");
			}
		}

	}

	public static void createDefaultAddress() {
		if (AddressDAO.fetch(AddressDAO.ORIGINAL_LOCATION) == null) {
			Address address = AddressDAO.ORIGINAL_LOCATION;
			address.getAudit().setCreatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			address.getAudit().setCreatedBy(ConstantsDAO.SYSTEM);
			address.getAudit().setUpdatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			address.getAudit().setUpdatedBy(ConstantsDAO.SYSTEM);
			AddressDAO.create(address);
		}
	}

	public static void createDefaultLocation() {
		if (LocationDAO.fetchByName(ConstantsDAO.ORIGINAL_LOCATION_NAME, true) == null) {
			Location location = new Location();
			location.setName(ConstantsDAO.ORIGINAL_LOCATION_NAME);
			location.setAddress(AddressDAO.fetchById(AddressDAO.ORIGINAL_LOCATION_ID));
			location.setTaxRate(0.095f);
			location.setDescription("the first VirKade");
			location.setManager("Zachary Sigmon");
			location.setPhoneNum(PropsUtil.getAdminMobileNum());
			location.setEnabled(Boolean.TRUE);
			location.getAudit().setCreatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			location.getAudit().setCreatedBy(ConstantsDAO.SYSTEM);
			location.getAudit().setUpdatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			location.getAudit().setUpdatedBy(ConstantsDAO.SYSTEM);
			LocationDAO.create(location);
		}
	}
	
	public static void createDefaultDocs() {
		StringBuilder sb = new StringBuilder();
		sb.append(ConstantsDAO.DEFAULT_TC_TITLE);
		sb.append(ConstantsDAO.DEFAULT_DOC_SEP);
		sb.append(ConstantsDAO.DEFAULT_DOC_VERSION);
		
		float tandcV = DocUtil.getTextFileVersion(sb.toString());
		
		sb.append(ConstantsDAO.DEFAULT_DOC_EXT);
		
		String tandcC = DocUtil.getTextFileContent(sb.toString());
		if (DocumentDAO.fetchByTitleAndVersion(ConstantsDAO.DEFAULT_TC_TITLE, ConstantsDAO.DEFAULT_DOC_VERSION) == null) {
			Document doc = new Document();
			doc.setTitle(ConstantsDAO.DEFAULT_TC_TITLE);
			doc.setContent(tandcC);
			doc.setVersion(tandcV);
			doc.setEnabled(true);
			doc.setType(TypeDAO.fetchByCode(ConstantsDAO.TERMS_CONDITIONS));
			doc.getAudit().setCreatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			doc.getAudit().setCreatedBy(ConstantsDAO.SYSTEM);
			doc.getAudit().setUpdatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			doc.getAudit().setUpdatedBy(ConstantsDAO.SYSTEM);
			DocumentDAO.create(doc);
		}
		sb.replace(0, ConstantsDAO.DEFAULT_TC_TITLE.length(), ConstantsDAO.DEFAULT_LIAB_TITLE);
		String liabC = DocUtil.getTextFileContent(sb.toString());
		
		sb.delete((sb.length()-(ConstantsDAO.DEFAULT_DOC_EXT).length()), sb.length());
		float liabV = DocUtil.getTextFileVersion(sb.toString());
		if (DocumentDAO.fetchByTitleAndVersion(ConstantsDAO.DEFAULT_LIAB_TITLE, ConstantsDAO.DEFAULT_DOC_VERSION) == null) {
			Document doc = new Document();
			doc.setTitle(ConstantsDAO.DEFAULT_LIAB_TITLE);
			doc.setContent(liabC);
			doc.setVersion(liabV);
			doc.setEnabled(true);
			doc.setType(TypeDAO.fetchByCode(ConstantsDAO.LIMITED_LIABLE));
			doc.getAudit().setCreatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			doc.getAudit().setCreatedBy(ConstantsDAO.SYSTEM);
			doc.getAudit().setUpdatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			doc.getAudit().setUpdatedBy(ConstantsDAO.SYSTEM);
			DocumentDAO.create(doc);
		}
	}

	public static void createDefaultUsers() {
		User user = new User();
		User user2 = new User();

		Calendar cal = Calendar.getInstance();
		Timestamp now = new Timestamp(cal.getTimeInMillis());

		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.YEAR, (cal2.get(Calendar.YEAR) + 99));
		Timestamp expireDate = new Timestamp(cal2.getTimeInMillis());

		if (UserDAO.fetchByUsername(ConstantsDAO.GUEST_USER_NAME) == null) {
			user.setFirstName(ConstantsDAO.GUEST_USER_NAME);
			user.setLastName(ConstantsDAO.GUEST_USER_NAME);
			user.setUsername(ConstantsDAO.GUEST_USER_NAME);
			user.setAddress(AddressDAO.fetchById(AddressDAO.ORIGINAL_LOCATION_ID));
			user.setEmailAddress(ConstantsDAO.GUEST_EMAIL_ADDRESS);
			user.setAccountVerified(true);
			user.setPassword(VirkadeEncryptor.hashEncode("123456"));
			user.setStatus(StatusDAO.fetchByCode(ConstantsDAO.ACTIVE_CODE));
			user.setType(TypeDAO.fetchByCode(ConstantsDAO.GUEST_CODE));
			user.getAudit().setCreatedAt(now);
			user.getAudit().setCreatedBy(ConstantsDAO.SYSTEM);
			user.getAudit().setUpdatedAt(now);
			user.getAudit().setUpdatedBy(ConstantsDAO.SYSTEM);
			UserDAO.createUpdate(user, false);

			Phone phone = new Phone();
			phone.setUser(user);
			phone.setCountryCode(PropsUtil.DEFAULT_PHONE_CC);
			phone.setNumber(PropsUtil.getAdminMobileNum());
			phone.setType(TypeDAO.fetchByCode(ConstantsDAO.HOME_PHONE));
			phone.getAudit().setCreatedAt(now);
			phone.getAudit().setCreatedBy(ConstantsDAO.SYSTEM);
			phone.getAudit().setUpdatedAt(now);
			phone.getAudit().setUpdatedBy(ConstantsDAO.SYSTEM);
			PhoneDAO.create(phone);

			Legal legal = new Legal();
			legal.setActiveDate(now);
			legal.setUser(user);
			legal.setAgree(true);
			Document tAndCDoc = DocumentDAO.fetchByTitleAndVersion(ConstantsDAO.DEFAULT_TC_TITLE, ConstantsDAO.DEFAULT_DOC_VERSION);
			legal.setDocument(DocumentDAO.fetchByDocId(tAndCDoc.getDocId()));
			String pSig = DocUtil.getEncodedOwnerSig();
			legal.setPSig(pSig);
			legal.setEnabled(true);
			legal.setExpireDate(expireDate);
			legal.setType(TypeDAO.fetchByCode(ConstantsDAO.TERMS_CONDITIONS));
			legal.getAudit().setCreatedAt(now);
			legal.getAudit().setCreatedBy(ConstantsDAO.SYSTEM);
			legal.getAudit().setUpdatedAt(now);
			legal.getAudit().setUpdatedBy(ConstantsDAO.SYSTEM);
			try {
				LegalDAO.create(legal);
			} catch (Exception e) {
				LOG.error(e);
			}

			Document liabDoc = DocumentDAO.fetchByTitleAndVersion(ConstantsDAO.DEFAULT_LIAB_TITLE, ConstantsDAO.DEFAULT_DOC_VERSION);
			legal.setDocument(DocumentDAO.fetchByDocId(liabDoc.getDocId()));
			legal.setType(TypeDAO.fetchByCode(ConstantsDAO.LIMITED_LIABLE));
			try {
				LegalDAO.create(legal);
			} catch (Exception e) {
				LOG.error(e);
			}
		}
		if (UserDAO.fetchByUsername(ConstantsDAO.OWNER_USER_UNAME) == null) {
			user2.setFirstName(ConstantsDAO.OWNER_USER_FNAME);
			user2.setLastName(ConstantsDAO.OWNER_USER_LNAME);
			user2.setUsername(ConstantsDAO.OWNER_USER_UNAME);
			user2.setAddress(AddressDAO.fetchById(AddressDAO.ORIGINAL_LOCATION_ID));
			user2.setEmailAddress(ConstantsDAO.GUEST_EMAIL_ADDRESS);
			user2.setAccountVerified(true);
			user2.setPassword(VirkadeEncryptor.hashEncode(ConstantsDAO.DEFAULT_OWNER_PASSWORD));
			user2.setStatus(StatusDAO.fetchByCode(ConstantsDAO.ACTIVE_CODE));
			user2.setType(TypeDAO.fetchByCode(ConstantsDAO.ADMIN_CODE));
			user2.getAudit().setCreatedAt(now);
			user2.getAudit().setCreatedBy(ConstantsDAO.SYSTEM);
			user2.getAudit().setUpdatedAt(now);
			user2.getAudit().setUpdatedBy(ConstantsDAO.SYSTEM);
			UserDAO.createUpdate(user2, false);

			Phone phone = new Phone();
			phone.setUser(user2);
			phone.setCountryCode(PropsUtil.DEFAULT_PHONE_CC);
			phone.setNumber(PropsUtil.getAdminMobileNum());
			phone.setType(TypeDAO.fetchByCode(ConstantsDAO.MOBILE_PHONE));
			phone.getAudit().setCreatedAt(now);
			phone.getAudit().setCreatedBy(ConstantsDAO.SYSTEM);
			phone.getAudit().setUpdatedAt(now);
			phone.getAudit().setUpdatedBy(ConstantsDAO.SYSTEM);
			PhoneDAO.create(phone);

			phone.setUser(user2);
			phone.setNumber(PropsUtil.getAdminMobileNum());
			phone.setType(TypeDAO.fetchByCode(ConstantsDAO.HOME_PHONE));
			PhoneDAO.create(phone);

			Legal legal = new Legal();
			legal.setActiveDate(now);
			legal.setUser(user2);
			legal.setAgree(true);
			Document tAndCDoc = DocumentDAO.fetchByTitleAndVersion(ConstantsDAO.DEFAULT_TC_TITLE, ConstantsDAO.DEFAULT_DOC_VERSION);
			legal.setDocument(DocumentDAO.fetchByDocId(tAndCDoc.getDocId()));
			String pSig = DocUtil.getEncodedOwnerSig();
			legal.setPSig(pSig);
			legal.setEnabled(true);
			legal.setExpireDate(expireDate);
			legal.setType(TypeDAO.fetchByCode(ConstantsDAO.TERMS_CONDITIONS));
			legal.getAudit().setCreatedAt(now);
			legal.getAudit().setCreatedBy(ConstantsDAO.SYSTEM);
			legal.getAudit().setUpdatedAt(now);
			legal.getAudit().setUpdatedBy(ConstantsDAO.SYSTEM);
			try {
				LegalDAO.create(legal);
			} catch (Exception e) {
				LOG.error(e);
			}

			Document liabDoc = DocumentDAO.fetchByTitleAndVersion(ConstantsDAO.DEFAULT_LIAB_TITLE, ConstantsDAO.DEFAULT_DOC_VERSION);
			legal.setDocument(DocumentDAO.fetchByDocId(liabDoc.getDocId()));
			legal.setType(TypeDAO.fetchByCode(ConstantsDAO.LIMITED_LIABLE));
			try {
				LegalDAO.create(legal);
			} catch (Exception e) {
				LOG.error(e);
			}

		}
	}

	public static void createDefaultActivity() {
		if (ActivityDAO.fetchByName(ConstantsDAO.DEFAULT_ACTIVITY_NAME) == null) {
			Activity activity = new Activity();
			activity.setCostpm(1.00f);
			activity.setSetupMin(PropsUtil.getPlaySessionSetupMin());
			activity.setCreator("HTC");
			activity.setName(ConstantsDAO.DEFAULT_ACTIVITY_NAME);
			activity.setDescription("the default vr arcade for the vive");
			activity.setEnabled(true);
			activity.setSupportContact("https://service.viveport.com/hc/en-us/requests/new?v_to_v&");
			activity.setWebSite("https://www.viveport.com");
			activity.getAudit().setCreatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			activity.getAudit().setCreatedBy(ConstantsDAO.SYSTEM);
			activity.getAudit().setUpdatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			activity.getAudit().setUpdatedBy(ConstantsDAO.SYSTEM);
			LOG.info(String.format("creating default activity %s", activity.toString()));
			ActivityDAO.create(activity);

		} else {
			LOG.info(String.format("default activity %s already exists", ConstantsDAO.DEFAULT_ACTIVITY_NAME));
		}
	}

	public static void createTestSession(int numberOfSessions) {
		while (numberOfSessions-- > 0) {
			List<PlaySession> allSessionsAvail = PlaySessionDAO.getAvailableSessions(null, LocationDAO.getDefault(), ActivityDAO.getDefault());
			if (allSessionsAvail.size() == 0) {
				LOG.warn("no availible sessions so nothing to create");
				break;
			}
			long randomSelection = (Math.round(Math.random() * (allSessionsAvail.size() - 1)));
			PlaySession session = allSessionsAvail.get((int) randomSelection);
			User user = UserDAO.fetchByUsername(ConstantsDAO.OWNER_USER_UNAME);
			session.setUser(user);
			Audit audit = VirkadeModel.addAuditToModel(user, session.getAudit());
			session.setAudit(audit);
			session.setPayed(false);
			try {
				PlaySessionDAO.create(session, 0, 0);
			} catch (Exception e) {
				LOG.error(e);
			}
		}
	}
}
