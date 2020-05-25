package com.virkade.cms.hibernate.dao;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.virkade.cms.hibernate.utilities.HibernateUtilities;
import com.virkade.cms.model.Activity;

public class ActivityDAO {

	private static final Logger LOG = Logger.getLogger(ActivityDAO.class);

	public static Activity fetchByName(String name) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		Activity activity = new Activity();
		try {
			hs.beginTransaction();
			Criteria criteria = hs.createCriteria(Activity.class);
			criteria.add(Restrictions.eq(ConstantsDAO.NAME_FIELD, name));
			activity = (Activity) criteria.uniqueResult();
		} catch (HibernateException he) {
			LOG.error("Hibernate exception get Game by name=" + name, he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}

		return activity;
	}

	public static Activity create(Activity game) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		try {
			hs.beginTransaction();
			hs.save(game);
		} catch (HibernateException he) {
			LOG.error("Hibernate exception creating game=" + game.toString(), he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return game;
	}

}
