package com.virkade.cms.hibernate.dao;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.virkade.cms.hibernate.utilities.HibernateUtilities;
import com.virkade.cms.model.Phone;

public class PhoneDAO {

	private static final Logger LOG = Logger.getLogger(PhoneDAO.class);
	
	public static Phone fetch(Phone phone) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		try {
			hs.beginTransaction();
			Criteria criteria = hs.createCriteria(Phone.class);
			criteria.add(Restrictions.eqOrIsNull(ConstantsDAO.PHONE_COUNTRY_FIELD, phone.getCountryCode()));
			criteria.add(Restrictions.eqOrIsNull(ConstantsDAO.NUMBER_FIELD, phone.getNumber()));
			criteria.add(Restrictions.eqOrIsNull(ConstantsDAO.TYPE_FIELD, phone.getType()));
			phone = (Phone) criteria.uniqueResult();
		} catch (HibernateException he) {
			LOG.error("Hibernate exception phone with phone number="+phone.getNumber()+" for userId="+phone.getUserId(), he);
		}finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return phone;
	}
	
	public static Phone create(Phone phone) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		try {
			hs.beginTransaction();
			LOG.info("creating phone with number:"+phone.getNumber()+", country code"+phone.getCountryCode()+", for user:"+phone.getUsername());
			hs.save(phone);
		} catch (HibernateException he) {
			LOG.error("Hibernate exception saving phone with phone number="+phone.getNumber()+" for Username="+phone.getUsername(), he);
		}finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return phone;
	}

}
