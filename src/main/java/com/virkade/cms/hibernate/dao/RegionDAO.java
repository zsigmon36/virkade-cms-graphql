package com.virkade.cms.hibernate.dao;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.virkade.cms.hibernate.utilities.HibernateUtilities;
import com.virkade.cms.model.Region;

public class RegionDAO {

	private static final Logger LOG = Logger.getLogger(RegionDAO.class);

	//common region constants
	public static final String NAME_AMERICAS = "Americas";
	public static final String CODE_AMERICAS = "AMS";

	//property fields
	private static final String CODE_FIELD = "regionCode";
	
	public static Region fetchByCode(String code) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		Region region = new Region();
		try {
			hs.beginTransaction();
			Criteria criteria = hs.createCriteria(Region.class);
			criteria.add(Restrictions.eq(CODE_FIELD, code));
			region = (Region) criteria.uniqueResult();
		} catch (HibernateException he) {
			LOG.error("Hibernate exception get Region by regionCode=" + code, he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return region;
	}


	public static Region create(Region region) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		try {
			hs.beginTransaction();
			hs.save(region);
		} catch (HibernateException he) {
			LOG.error("Hibernate exception creating region=" + region.toString(), he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return region;
	}

}
