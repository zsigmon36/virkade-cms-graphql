package com.virkade.cms.hibernate.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.virkade.cms.hibernate.utilities.HibernateUtilities;
import com.virkade.cms.model.User;


public class UserDAO {

	//property fields to query
	private static final String USER_NAME = "userName";
	
	public static void pushUser(User user) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		hs.beginTransaction();
		hs.save(user);
		hs.getTransaction().commit();
		hs.close();
	}

	public static User lookupUser(String userName) throws Exception {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		hs.beginTransaction();

		Criteria criteria = hs.createCriteria(User.class);
		criteria.add(Restrictions.eq(USER_NAME, userName));
		User user = (User) criteria.uniqueResult();
		hs.getTransaction().commit();
		hs.close();
		return user;
	}
}
