package com.virkade.cms.hibernate.dao;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.virkade.cms.hibernate.utilities.HibernateUtilities;
import com.virkade.cms.model.Country;

public class CountryDAO {

	private static final Logger LOG = Logger.getLogger(CountryDAO.class);

	public static Country fetchByA2(String a2) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		Country country = new Country();
		try {
			hs.beginTransaction();
			Criteria criteria = hs.createCriteria(Country.class);
			criteria.add(Restrictions.eq(ConstantsDAO.A2_FIELD, a2));
			country = (Country) criteria.uniqueResult();
		} catch (HibernateException he) {
			LOG.error("Hibernate exception get country by a2=" + a2, he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return country;
	}

	public static Country create(Country country) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		try {
			hs.beginTransaction();
			hs.save(country);
		} catch (HibernateException he) {
			LOG.error("Hibernate exception creating country=" + country.toString(), he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return country;
	}

}
