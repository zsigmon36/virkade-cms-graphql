package com.virkade.cms.hibernate.utilities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.virkade.cms.auth.VirkadeEncryptor;
import com.virkade.cms.hibernate.dao.AddressDAO;
import com.virkade.cms.hibernate.dao.CountryDAO;
import com.virkade.cms.hibernate.dao.GameDAO;
import com.virkade.cms.hibernate.dao.LocationDAO;
import com.virkade.cms.hibernate.dao.RegionDAO;
import com.virkade.cms.hibernate.dao.SessionDAO;
import com.virkade.cms.hibernate.dao.StateDAO;
import com.virkade.cms.hibernate.dao.StatusDAO;
import com.virkade.cms.hibernate.dao.TypeDAO;
import com.virkade.cms.hibernate.dao.UserDAO;
import com.virkade.cms.model.Address;
import com.virkade.cms.model.Country;
import com.virkade.cms.model.Game;
import com.virkade.cms.model.Location;
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

		if (StatusDAO.fetchByCode(StatusDAO.ACTIVE_CODE) == null) {
			status.setCode(StatusDAO.ACTIVE_CODE);
			status.setDescription("Status for general customer or admins");
			status.setName(StatusDAO.ACTIVE_NAME);
			status.getAudit().setCreatedAt(new Date());
			status.getAudit().setCreatedBy(VirkadeHibernateConstants.SYSTEM);
			status.getAudit().setUpdatedAt(new Date());
			status.getAudit().setUpdatedBy(VirkadeHibernateConstants.SYSTEM);
			StatusDAO.create(status);
		}

		if (StatusDAO.fetchByCode(StatusDAO.INACTIVE_CODE) == null) {
			status.setCode(StatusDAO.INACTIVE_CODE);
			status.setDescription("Status for customer or admin that no longer want to interact with the system");
			status.setName(StatusDAO.INACTIVE_NAME);
			status.getAudit().setCreatedAt(new Date());
			status.getAudit().setCreatedBy(VirkadeHibernateConstants.SYSTEM);
			status.getAudit().setUpdatedAt(new Date());
			status.getAudit().setUpdatedBy(VirkadeHibernateConstants.SYSTEM);
			StatusDAO.create(status);
		}
	}

	public static void createDefaultTypes() {
		Type type = new Type();

		if (TypeDAO.fetchByCode(TypeDAO.CUSTOMER_CODE) == null) {
			type.setCode(TypeDAO.CUSTOMER_CODE);
			type.setName("Customer User");
			type.setDescription("this is the regular type user that has an account and played at least one session");
			type.getAudit().setCreatedAt(new Date());
			type.getAudit().setCreatedBy(VirkadeHibernateConstants.SYSTEM);
			type.getAudit().setUpdatedAt(new Date());
			type.getAudit().setUpdatedBy(VirkadeHibernateConstants.SYSTEM);
			TypeDAO.create(type);
		}

		if (TypeDAO.fetchByCode(TypeDAO.PROSPECT_CODE) == null) {
			type.setCode(TypeDAO.PROSPECT_CODE);
			type.setName("Prospect User");
			type.setDescription("this is the user has created an account, but has not played a session yet");
			type.getAudit().setCreatedAt(new Date());
			type.getAudit().setCreatedBy(VirkadeHibernateConstants.SYSTEM);
			type.getAudit().setUpdatedAt(new Date());
			type.getAudit().setUpdatedBy(VirkadeHibernateConstants.SYSTEM);
			TypeDAO.create(type);
		}
		if (TypeDAO.fetchByCode(TypeDAO.GUEST_CODE) == null) {
			type.setCode(TypeDAO.GUEST_CODE);
			type.setName("Guest User");
			type.setDescription("this is the public user when no account is signed in");
			type.getAudit().setCreatedAt(new Date());
			type.getAudit().setCreatedBy(VirkadeHibernateConstants.SYSTEM);
			type.getAudit().setUpdatedAt(new Date());
			type.getAudit().setUpdatedBy(VirkadeHibernateConstants.SYSTEM);
			TypeDAO.create(type);
		}
		
		if (TypeDAO.fetchByCode(TypeDAO.ADMIN_CODE) == null) {
			type.setCode(TypeDAO.ADMIN_CODE);
			type.setName("Administrator");
			type.setDescription("this is the user that can perform super user tasks, usually an employee");
			type.getAudit().setCreatedAt(new Date());
			type.getAudit().setCreatedBy(VirkadeHibernateConstants.SYSTEM);
			type.getAudit().setUpdatedAt(new Date());
			type.getAudit().setUpdatedBy(VirkadeHibernateConstants.SYSTEM);
			TypeDAO.create(type);
		}
		if (TypeDAO.fetchByCode(TypeDAO.PHYSICAL_ADDRESS) == null) {
			type.setCode(TypeDAO.PHYSICAL_ADDRESS);
			type.setName("Physical Address");
			type.setDescription("the physical address of an entity");
			type.getAudit().setCreatedAt(new Date());
			type.getAudit().setCreatedBy(VirkadeHibernateConstants.SYSTEM);
			type.getAudit().setUpdatedAt(new Date());
			type.getAudit().setUpdatedBy(VirkadeHibernateConstants.SYSTEM);
			TypeDAO.create(type);
		}
	}

	public static void createDefaultRegions() {

		if (RegionDAO.fetchByCode(RegionDAO.CODE_AMERICAS) == null) {
			Region region = new Region();
			region.setName(RegionDAO.NAME_AMERICAS);
			region.setRegionCode(RegionDAO.CODE_AMERICAS);
			region.setDescription("North America, central, and South America");
			region.getAudit().setCreatedAt(new Date());
			region.getAudit().setCreatedBy(VirkadeHibernateConstants.SYSTEM);
			region.getAudit().setUpdatedAt(new Date());
			region.getAudit().setUpdatedBy(VirkadeHibernateConstants.SYSTEM);
			RegionDAO.create(region);
		}
	}

	public static void createDefaultCountries() {

		if (CountryDAO.fetchByA2(CountryDAO.A2_UNITEDSTATES) == null) {
			Country country = new Country();
			country.setName(CountryDAO.NAME_UNITEDSTATES);
			country.setRegion(RegionDAO.fetchByCode(RegionDAO.CODE_AMERICAS));
			country.setA2(CountryDAO.A2_UNITEDSTATES);
			country.setA3(CountryDAO.A3_UNITEDSTATES);
			country.setDescription("The United States of America");
			country.getAudit().setCreatedAt(new Date());
			country.getAudit().setCreatedBy(VirkadeHibernateConstants.SYSTEM);
			country.getAudit().setUpdatedAt(new Date());
			country.getAudit().setUpdatedBy(VirkadeHibernateConstants.SYSTEM);
			CountryDAO.create(country);
		}

	}

	public static void createDefaultStates() {

		if (StateDAO.fetchByCode(StateDAO.CODE_ARKANSAS) == null) {
			State state = new State();
			state.setCountry(CountryDAO.fetchByA2(CountryDAO.A2_UNITEDSTATES));
			state.setName(StateDAO.NAME_ARKANSAS);
			state.setDescription("State of Arkansas");
			state.setStateCode(StateDAO.CODE_ARKANSAS);
			state.getAudit().setCreatedAt(new Date());
			state.getAudit().setCreatedBy(VirkadeHibernateConstants.SYSTEM);
			state.getAudit().setUpdatedAt(new Date());
			state.getAudit().setUpdatedBy(VirkadeHibernateConstants.SYSTEM);
			StateDAO.create(state);
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
		if (LocationDAO.fetchByName(LocationDAO.ORIGINAL_LOCATION_NAME) == null) {
			Location location = new Location();
			location.setName(LocationDAO.ORIGINAL_LOCATION_NAME);
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

		if (UserDAO.fetchByUserName(UserDAO.GUEST_USER_NAME) == null) {
			user.setFirstName(UserDAO.GUEST_USER_NAME);
			user.setLastName(UserDAO.GUEST_USER_NAME);
			user.setUserName(UserDAO.GUEST_USER_NAME);
			user.setAddress(AddressDAO.fetchById(AddressDAO.ORIGINAL_LOCATION_ID));
			user.setEmailAddress(UserDAO.GUEST_EMAIL_ADDRESS);
			user.setPassword(VirkadeEncryptor.hashEncode("123456"));
			user.setStatus(StatusDAO.fetchByCode(StatusDAO.ACTIVE_CODE));
			user.setType(TypeDAO.fetchByCode(TypeDAO.GUEST_CODE));
			user.getAudit().setCreatedAt(new Date());
			user.getAudit().setCreatedBy(VirkadeHibernateConstants.SYSTEM);
			user.getAudit().setUpdatedAt(new Date());
			user.getAudit().setUpdatedBy(VirkadeHibernateConstants.SYSTEM);
			UserDAO.createUpdate(user, false);
		}
		if (UserDAO.fetchByUserName(UserDAO.OWNER_USER_NAME) == null) {
			user.setFirstName(UserDAO.OWNER_USER_NAME);
			user.setLastName(UserDAO.OWNER_USER_NAME);
			user.setUserName(UserDAO.OWNER_USER_NAME);
			user.setAddress(AddressDAO.fetchById(AddressDAO.ORIGINAL_LOCATION_ID));
			user.setEmailAddress(UserDAO.GUEST_EMAIL_ADDRESS);
			user.setPassword(VirkadeEncryptor.hashEncode("Reid.Zac36"));
			user.setStatus(StatusDAO.fetchByCode(StatusDAO.ACTIVE_CODE));
			user.setType(TypeDAO.fetchByCode(TypeDAO.ADMIN_CODE));
			user.getAudit().setCreatedAt(new Date());
			user.getAudit().setCreatedBy(VirkadeHibernateConstants.SYSTEM);
			user.getAudit().setUpdatedAt(new Date());
			user.getAudit().setUpdatedBy(VirkadeHibernateConstants.SYSTEM);
			UserDAO.createUpdate(user, false);
		}
	}

	public static void createTestGame() {
		
		Game game = new Game();
		game.setCost(22.55);
		game.setCreator("Bethesda");
		game.setName("Super Hot");
		game.setDescription("test description");
		game.setEnabled(true);
		game.setWebSite("www.bethesda.com");
		game.getAudit().setCreatedAt(new Date());
		game.getAudit().setCreatedBy(VirkadeHibernateConstants.SYSTEM);
		game.getAudit().setUpdatedAt(new Date());
		game.getAudit().setUpdatedBy(VirkadeHibernateConstants.SYSTEM);
		Game testGame = GameDAO.fetchByName(game.getName());
		if (testGame == null) {
			GameDAO.create(game);
		} else {
			LOG.info(String.format("Seed name with %s already exists", game.getName()));
		}
		

	}

	public static void createTestSession() {
		List<Game> games = new ArrayList<>();
		games.add(GameDAO.fetchByName("Super Hot"));
		PlaySession session = new PlaySession(UserDAO.fetchByUserName(UserDAO.GUEST_USER_NAME), LocationDAO.fetchByName(LocationDAO.ORIGINAL_LOCATION_NAME), games, new Date(), new Date());
		SessionDAO.create(session);

	}

}
