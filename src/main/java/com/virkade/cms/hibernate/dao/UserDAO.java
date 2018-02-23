package com.virkade.cms.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.virkade.cms.hibernate.utilities.HibernateUtilities;
import com.virkade.cms.model.User;

public class UserDAO {
	
	public static void pushUser(User user) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		hs.beginTransaction();
		hs.save(user);
		hs.getTransaction().commit();
		hs.close();
	}
}
