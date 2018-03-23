package com.virkade.cms.hibernate.utilities;

import java.util.Date;

import com.virkade.cms.auth.VirkadeEncryptor;
import com.virkade.cms.hibernate.dao.StatusDAO;
import com.virkade.cms.hibernate.dao.TypeDAO;
import com.virkade.cms.hibernate.dao.UserDAO;
import com.virkade.cms.model.Status;
import com.virkade.cms.model.Type;
import com.virkade.cms.model.User;

public class CMSSeeds {

	private CMSSeeds() {

	}

	public static void createDefaultStatus() {
		Status status = new Status();

		if (StatusDAO.fetchStatusByCode(StatusDAO.ACTIVE_CODE) == null) {
			status.setCode(StatusDAO.ACTIVE_CODE);
			status.setDescription("Status for general customer or admins");
			status.setName(StatusDAO.ACTIVE_NAME);
			status.getAudit().setCreatedAt(new Date());
			status.getAudit().setCreatedBy(VirkadeHibernateConstants.SYSTEM);
			status.getAudit().setUpdatedAt(new Date());
			status.getAudit().setUpdatedBy(VirkadeHibernateConstants.SYSTEM);
			StatusDAO.createStatus(status);
		}

		if (StatusDAO.fetchStatusByCode(StatusDAO.INACTIVE_CODE) == null) {
			status.setCode(StatusDAO.INACTIVE_CODE);
			status.setDescription("Status for customer or admin that no longer want to interact with the system");
			status.setName(StatusDAO.INACTIVE_NAME);
			status.getAudit().setCreatedAt(new Date());
			status.getAudit().setCreatedBy(VirkadeHibernateConstants.SYSTEM);
			status.getAudit().setUpdatedAt(new Date());
			status.getAudit().setUpdatedBy(VirkadeHibernateConstants.SYSTEM);
			StatusDAO.createStatus(status);
		}
	}

	public static void createDefaultTypes() {
		Type type = new Type();

		if (TypeDAO.fetchTypeByCode(TypeDAO.CUSTOMER_CODE) == null) {
			type.setCode(TypeDAO.CUSTOMER_CODE);
			type.setName("Customer User");
			type.setDescription("this is the regular type user that has an account and played at least one session");
			type.getAudit().setCreatedAt(new Date());
			type.getAudit().setCreatedBy(VirkadeHibernateConstants.SYSTEM);
			type.getAudit().setUpdatedAt(new Date());
			type.getAudit().setUpdatedBy(VirkadeHibernateConstants.SYSTEM);
			TypeDAO.createType(type);
		}

		if (TypeDAO.fetchTypeByCode(TypeDAO.PROSPECT_CODE) == null) {
			type.setCode(TypeDAO.PROSPECT_CODE);
			type.setName("Prospect User");
			type.setDescription("this is the user has created an account, but has not played a session yet");
			type.getAudit().setCreatedAt(new Date());
			type.getAudit().setCreatedBy(VirkadeHibernateConstants.SYSTEM);
			type.getAudit().setUpdatedAt(new Date());
			type.getAudit().setUpdatedBy(VirkadeHibernateConstants.SYSTEM);
			TypeDAO.createType(type);
		}
		if (TypeDAO.fetchTypeByCode(TypeDAO.ADMIN_CODE) == null) {
			type.setCode(TypeDAO.ADMIN_CODE);
			type.setName("Administrator");
			type.setDescription("this is the user that can perform super user tasks, usually an employee");
			type.getAudit().setCreatedAt(new Date());
			type.getAudit().setCreatedBy(VirkadeHibernateConstants.SYSTEM);
			type.getAudit().setUpdatedAt(new Date());
			type.getAudit().setUpdatedBy(VirkadeHibernateConstants.SYSTEM);
			TypeDAO.createType(type);
		}
	}

	public static void createDefaultUsers() {
		User user = new User();

		if (UserDAO.fetchUserByUserName(UserDAO.GUEST_USER_NAME) == null) {
			user.setFirstName(UserDAO.GUEST_USER_NAME);
			user.setLastName(UserDAO.GUEST_USER_NAME);
			user.setUserName(UserDAO.GUEST_USER_NAME);
			user.setEmailAddress(UserDAO.GUEST_EMAIL_ADDRESS);
			user.setPassword(VirkadeEncryptor.hashEncode("123456"));
			user.setStatus(StatusDAO.fetchStatusByCode(StatusDAO.ACTIVE_CODE));
			user.setType(TypeDAO.fetchTypeByCode(TypeDAO.PROSPECT_CODE));
			user.getAudit().setCreatedAt(new Date());
			user.getAudit().setCreatedBy(VirkadeHibernateConstants.SYSTEM);
			user.getAudit().setUpdatedAt(new Date());
			user.getAudit().setUpdatedBy(VirkadeHibernateConstants.SYSTEM);
			UserDAO.pushUser(user, false);
		}
		if (UserDAO.fetchUserByUserName(UserDAO.OWNER_USER_NAME) == null) {
			user.setFirstName(UserDAO.OWNER_USER_NAME);
			user.setLastName(UserDAO.OWNER_USER_NAME);
			user.setUserName(UserDAO.OWNER_USER_NAME);
			user.setEmailAddress(UserDAO.GUEST_EMAIL_ADDRESS);
			user.setPassword(VirkadeEncryptor.hashEncode("Reid.Zac36"));
			user.setStatus(StatusDAO.fetchStatusByCode(StatusDAO.ACTIVE_CODE));
			user.setType(TypeDAO.fetchTypeByCode(TypeDAO.ADMIN_CODE));
			user.getAudit().setCreatedAt(new Date());
			user.getAudit().setCreatedBy(VirkadeHibernateConstants.SYSTEM);
			user.getAudit().setUpdatedAt(new Date());
			user.getAudit().setUpdatedBy(VirkadeHibernateConstants.SYSTEM);
			UserDAO.pushUser(user, false);
		}
	}
}
