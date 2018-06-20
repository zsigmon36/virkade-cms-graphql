package com.virkade.cms.hibernate.dao;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.virkade.cms.hibernate.utilities.HibernateUtilities;
import com.virkade.cms.model.Location;
import com.virkade.cms.model.State;

public class LocationDAO {

	private static final Logger LOG = Logger.getLogger(LocationDAO.class);
	
	public static final String ORIGINAL_LOCATION_NAME = "VirKade Prime";

	private static final String NAME_FIELD = "name";

	public static Location fetchByName(String name) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		Location location = new Location();
		try {
			hs.beginTransaction();
			Criteria criteria = hs.createCriteria(State.class);
			criteria.add(Restrictions.eq(NAME_FIELD, name));
			location = (Location) criteria.uniqueResult();
		} catch (HibernateException he) {
			LOG.error("Hibernate exception getting location by name=" + name, he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return location;
	}

	public static Location create(Location location) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		try {
			hs.beginTransaction();
			hs.save(location);
		} catch (HibernateException he) {
			LOG.error("Hibernate exception creating location=" + location.toString(), he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return location;
	}

}