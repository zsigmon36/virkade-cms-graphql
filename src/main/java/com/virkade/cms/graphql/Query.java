package com.virkade.cms.graphql;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.virkade.cms.hibernate.utilities.HibernateUtilities;
import com.virkade.cms.model.User;

public class Query implements GraphQLRootResolver {

	public List<User> allUsers() {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		hs.beginTransaction();

		List<User> users = hs.createCriteria(User.class).list();

		hs.getTransaction().commit();
		hs.close();
		return users;
	}
}