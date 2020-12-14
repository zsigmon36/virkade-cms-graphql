package com.virkade.cms.hibernate.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.virkade.cms.hibernate.utilities.HibernateUtilities;
import com.virkade.cms.model.Address;
import com.virkade.cms.model.User;
import com.virkade.cms.model.search.UserSearchFilter;

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
				LOG.info("updating user with username:"+user.getUsername());
				hs.update(user);
			} else {
				LOG.info("saving user with username:"+user.getUsername());
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
			LOG.debug("fetching user with username:"+user.getUsername());
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
			LOG.debug("fetching user with username:"+user.getUsername());
			user = (User) criteria.uniqueResult();
			if (user == null)
				throw new HibernateException("No user found");
		} catch (HibernateException he) {
			LOG.error("Hibernate exception getting user by username=" + username, he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}

		return user;
	}
	
	public static User getById(long userId) {
		User user = fetchById(userId);
		if (user == null)
			throw new HibernateException("No user found");
		return user;
	}

	/**
	 * @param user Pass in a user object to see if there is a match persisted the User requires at least a userName, userId, or emailAddress;
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

	public static User fetchById(long userId) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		User user = null;
		try {
			hs.beginTransaction();
			LOG.debug("fetching user by id:"+userId);
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
			LOG.debug("fetching user with email:"+emailAddress);
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
			LOG.debug("fetching all users");
			users = hs.createCriteria(User.class).list();
		} catch (HibernateException he) {
			LOG.error("Hibernate exception getting all users", he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return users;
	}

	public static List<User> searchUsers(UserSearchFilter userSearchFilter, int count, int offset) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		List<User> users = null;
		List<Address> addresses= null;
		try {
			hs.beginTransaction();
			
			Criteria addressCriteria = hs.createCriteria(Address.class);
			
			//ProjectionList selects = Projections.projectionList();
			//selects.add(Projections.property(ConstantsDAO.ADDRESS_ID));
			//addressCriteria.setProjection(selects);
			boolean executeQuery = false;
			if (userSearchFilter.getStreet() != null) {
				addressCriteria.add(Restrictions.ilike(ConstantsDAO.STREET_FIELD, userSearchFilter.getStreet(), MatchMode.ANYWHERE));
				executeQuery = true;
			}
			if (userSearchFilter.getCity() != null) {
				addressCriteria.add(Restrictions.ilike(ConstantsDAO.CITY_FIELD, userSearchFilter.getCity(), MatchMode.ANYWHERE));
				executeQuery = true;
			}
			if (userSearchFilter.getState() != null) {
				addressCriteria.add(Restrictions.eq(ConstantsDAO.STATE_FIELD, userSearchFilter.getState()));
				executeQuery = true;
			}
			if (userSearchFilter.getPostalCode() != null) {
				addressCriteria.add(Restrictions.eq(ConstantsDAO.ZIP_FIELD, userSearchFilter.getPostalCode()));
				executeQuery = true;
			}
			if (executeQuery) {
				addresses = addressCriteria.list();
			}	
			
			Criteria userCriteria = hs.createCriteria(User.class);
			
			userCriteria.setFirstResult(offset);
			userCriteria.setMaxResults(count);

			userCriteria.addOrder(Order.asc(ConstantsDAO.LAST_NAME_FIELD));
			
			if (userSearchFilter.getFistName() != null) {
				userCriteria.add(Restrictions.ilike(ConstantsDAO.FIRST_NAME_FIELD, userSearchFilter.getFistName(), MatchMode.ANYWHERE));
			}
			if (userSearchFilter.getLastName() != null) {
				userCriteria.add(Restrictions.ilike(ConstantsDAO.LAST_NAME_FIELD, userSearchFilter.getLastName(), MatchMode.ANYWHERE));
			}
			if (userSearchFilter.getEmailAddress() != null) {
				userCriteria.add(Restrictions.ilike(ConstantsDAO.EMAIL_ADDRESS_FIELD, userSearchFilter.getEmailAddress(), MatchMode.ANYWHERE));
			}
			if (userSearchFilter.getUsername() != null) {
				userCriteria.add(Restrictions.ilike(ConstantsDAO.USER_NAME_FIELD, userSearchFilter.getUsername(), MatchMode.ANYWHERE));
			}
			if (addresses != null) {
				userCriteria.add(Restrictions.in(ConstantsDAO.ADDRESS, addresses));
			}
			LOG.debug("searching for user with search filter:"+userSearchFilter.toString());
			users = userCriteria.list();
		} catch (HibernateException he) {
			LOG.error("Hibernate exception getting users with search filters", he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return users;
	}
}
