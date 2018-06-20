package com.virkade.cms.hibernate.dao;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.virkade.cms.hibernate.utilities.HibernateUtilities;
import com.virkade.cms.model.Phone;

public class PhoneDAO {

	private static final Logger LOG = Logger.getLogger(PhoneDAO.class);
	
	public static Phone create(Phone phone) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		try {
			hs.beginTransaction();
			hs.save(phone);
		} catch (HibernateException he) {
			LOG.error("Hibernate exception saving phone with phone number="+phone.getNumber()+" for userName="+phone.getUser().getUserName(), he);
		}finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return phone;
	}

}
