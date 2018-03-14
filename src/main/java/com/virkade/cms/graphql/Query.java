package com.virkade.cms.graphql;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.virkade.cms.hibernate.dao.UserDAO;
import com.virkade.cms.hibernate.utilities.HibernateUtilities;
import com.virkade.cms.model.PlaySession;
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
	
	public User getUser(String userName) throws Exception {
		return UserDAO.lookupUser(userName);
	}
	
	public List<PlaySession> getUserSessions(String username) throws Exception {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		hs.beginTransaction();

		org.hibernate.Query query = hs.createQuery("from user where username = :username").setString("username", username);
		List<User> users = query.list();
		if (users.size() > 1) {
			throw new Exception("more than one user found for username="+username+" this should not be possible");
		}
		User user = users.get(0);
		org.hibernate.Query query2 = hs.createQuery("from session where userId = :userId").setLong("userId", user.getUserId());
		List<PlaySession> playSessions = query2.list();
		hs.getTransaction().commit();
		hs.close();
		return playSessions;
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