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
	
	public List<User> getUserByEmailAddress(String emailAddress) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		hs.beginTransaction();

		org.hibernate.Query query = hs.createQuery("from user where emailAddress = :emailAddress").setString("emailAddress", emailAddress);
		List<User> users = query.list();
		hs.getTransaction().commit();
		hs.close();
		return users;
	}
	
	public List<User> getUserByUsername(String username){
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		hs.beginTransaction();

		org.hibernate.Query query = hs.createQuery("from user where username = :username").setString("username", username);
		List<User> users = query.list();
		hs.getTransaction().commit();
		hs.close();
		return users;
	}
	public List<User> getUser(Long userId){
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		hs.beginTransaction();

		org.hibernate.Query query = hs.createQuery("from user where userId = :userId").setLong("userId", userId);
		List<User> users = query.list();
		hs.getTransaction().commit();
		hs.close();
		return users;
	}
}