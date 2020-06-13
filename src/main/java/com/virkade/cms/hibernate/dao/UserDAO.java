package com.virkade.cms.hibernate.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.virkade.cms.hibernate.utilities.HibernateUtilities;
import com.virkade.cms.model.User;

public class UserDAO {

	private static final Logger LOG = Logger.getLogger(UserDAO.class);
	
	private UserDAO() {
	}

	public static User createUpdate(User user, boolean update) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		try {
			hs.beginTransaction();
			if (update) {
				hs.update(user);
			} else {
				hs.save(user);
			}
		} catch (HibernateException he) {
			LOG.error("Hibernate exception creating or updating user=" + user.toString(), he);
			return null;
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return user;
	}

	public static User fetchByUsername(String username) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		User user = new User();
		try {
			hs.beginTransaction();
			Criteria criteria = hs.createCriteria(User.class);
			criteria.add(Restrictions.eq(ConstantsDAO.USER_NAME_FIELD, username));
			user = (User) criteria.uniqueResult();
		} catch (HibernateException he) {
			LOG.error("Hibernate exception getting user by username=" + username, he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}

		return user;
	}
	
	public static User getByUsername(String username) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		User user = new User();
		try {
			hs.beginTransaction();
			Criteria criteria = hs.createCriteria(User.class);
			criteria.add(Restrictions.eq(ConstantsDAO.USER_NAME_FIELD, username));
			user = (User) criteria.uniqueResult();
			if (user == null) throw new HibernateException("No user found");
		} catch (HibernateException he) {
			LOG.error("Hibernate exception getting user by username=" + username, he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}

		return user;
	}

	/**
	 * @param user
	 *            Pass in a user object to see if there is a match persisted the User requires at least a userName, userId, or emailAddress;
	 * @return
	 */
	public static User fetch(User user) {

		if (user.getUsername() != null) {
			user = fetchByUsername(user.getUsername());
		} else if (user.getUserId() != 0) {
			user = fetchById(user.getUserId());
		} else if (user.getEmailAddress() != null) {
			List<User> users = fetchByEmail(user.getEmailAddress());
			user = (users.isEmpty() ? null : users.get(0));
		}
		return user;
	}

	private static User fetchById(long userId) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		User user = null;
		try {
			hs.beginTransaction();
			user = hs.get(User.class, userId);
		} catch (HibernateException he) {
			LOG.error("Hibernate exception getting user by userId=" + userId, he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return user;
	}

	public static List<User> fetchByEmail(String emailAddress) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		List<User> users = null;
		try {
			hs.beginTransaction();
			Criteria criteria = hs.createCriteria(User.class);
			criteria.add(Restrictions.eq(ConstantsDAO.EMAIL_ADDRESS_FIELD, emailAddress));
			users = criteria.list();
		} catch (HibernateException he) {
			LOG.error("Hibernate exception getting users by emailAddress=" + emailAddress, he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return users;
	}

	public static List<User> fetchAll() {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		List<User> users = null;
		try {
			hs.beginTransaction();
			users = hs.createCriteria(User.class).list();
		} catch (HibernateException he) {
			LOG.error("Hibernate exception getting all users", he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return users;
	}
}
