package com.virkade.cms.hibernate.dao;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.virkade.cms.hibernate.utilities.HibernateUtilities;
import com.virkade.cms.model.PlaySession;

public class SessionDAO {

	private static final Logger LOG = Logger.getLogger(SessionDAO.class);

	public static PlaySession create(PlaySession session) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		try {
			hs.beginTransaction();
			hs.save(session);
		} catch (HibernateException he) {
			LOG.error("Hibernate exception creating PlaySession=" + session.toString(), he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return session;

	}

}
