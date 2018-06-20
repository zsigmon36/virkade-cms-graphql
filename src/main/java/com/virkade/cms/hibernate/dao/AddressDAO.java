package com.virkade.cms.hibernate.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.virkade.cms.hibernate.utilities.HibernateUtilities;
import com.virkade.cms.model.Address;

public class AddressDAO {

	private static final Logger LOG = Logger.getLogger(AddressDAO.class);
	// field constants
	private static final String STREET_FIELD = "street";
	private static final String CITY_FIELD = "city";
	private static final String APT_FIELD = "apt";
	private static final String UNIT_FIELD = "unit";
	private static final String STATE_FIELD = "state";
	private static final String ZIP_FIELD = "postalCode";
	private static final String TYPE_FIELD = "type";

	// common constants
	public static Address ORIGINAL_LOCATION = new Address();
	public static long ORIGINAL_LOCATION_ID = 1L;

	static {
		ORIGINAL_LOCATION.setAddressId(ORIGINAL_LOCATION_ID);
		ORIGINAL_LOCATION.setStreet("149 Glen");
		ORIGINAL_LOCATION.setCity("Farmingtion");
		ORIGINAL_LOCATION.setState(StateDAO.fetchByCode(StateDAO.CODE_ARKANSAS));
		ORIGINAL_LOCATION.setPostalCode(72730);
		ORIGINAL_LOCATION.setType(TypeDAO.fetchByCode(TypeDAO.PHYSICAL_ADDRESS));
	}

	public static Address fetchById(long addressId) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		Address address = null;
		try {
			hs.beginTransaction();
			address = hs.get(Address.class, addressId);
		} catch (HibernateException he) {
			LOG.error("Hibernate exception getting Address by addressId=" + addressId, he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return address;
	}

	public static Address fetch(Address address) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		Address addressReturned = new Address();
		try {
			hs.beginTransaction();
			Criteria criteria = hs.createCriteria(Address.class);
			Map<String, Object> fields = new HashMap<>();
			fields.put(STREET_FIELD, address.getStreet());
			fields.put(CITY_FIELD, address.getCity());
			fields.put(APT_FIELD, address.getApt());
			fields.put(UNIT_FIELD, address.getUnit());
			fields.put(STATE_FIELD, address.getState());
			fields.put(ZIP_FIELD, address.getPostalCode());
			fields.put(TYPE_FIELD, address.getType());
			criteria.add(Restrictions.allEq(fields));
			addressReturned = (Address) criteria.uniqueResult();
		} catch (HibernateException he) {
			LOG.error("Hibernate exception getting Address=" + address.toString(), he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return addressReturned;
	}

	public static Address create(Address address) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		try {
			hs.beginTransaction();
			hs.save(address);
		} catch (HibernateException he) {
			LOG.error("Hibernate exception get creating Address=" + address.toString(), he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return address;

	}

}