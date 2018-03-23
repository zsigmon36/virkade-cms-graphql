package com.virkade.cms.hibernate.dao;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.virkade.cms.hibernate.utilities.HibernateUtilities;
import com.virkade.cms.model.Address;

public class AddressDAO {

	private static final Logger LOG = Logger.getLogger(AddressDAO.class);
	
	public static Address fetchAddressById(long addressId) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		Address address = null;
		try {
			hs.beginTransaction();
			address = hs.get(Address.class, addressId);
		} catch (HibernateException he) {
			LOG.error("Hibernate exception getting Address by addressId="+addressId, he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return address;
	}

}
