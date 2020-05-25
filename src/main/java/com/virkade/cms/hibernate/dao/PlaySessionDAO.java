package com.virkade.cms.hibernate.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.virkade.cms.hibernate.utilities.HibernateUtilities;
import com.virkade.cms.model.PlaySession;
import com.virkade.cms.model.User;

public class PlaySessionDAO {
	
	private static final Logger LOG = Logger.getLogger(PlaySessionDAO.class);

	public static List<PlaySession> fetchUserSessions(String username) throws Exception {
		User user = UserDAO.fetchByUsername(username);
		List<PlaySession> playSessions = null;
		if (user == null) {
			throw new Exception("username: "+username+" could not be found");
		}
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		try {
			hs.beginTransaction();
			Criteria criteria = hs.createCriteria(PlaySession.class);
			criteria.add(Restrictions.eq("user", user));
			playSessions = criteria.list();
		} catch (HibernateException he) {
			LOG.error("Hibernate exception get play sessions by UserId=" + user.getUserId(), he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return playSessions;
	}
}
