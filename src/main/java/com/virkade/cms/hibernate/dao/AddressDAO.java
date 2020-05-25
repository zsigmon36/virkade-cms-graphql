package com.virkade.cms.hibernate.dao;

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

	// common constants
	public static Address ORIGINAL_LOCATION = new Address();
	public static long ORIGINAL_LOCATION_ID = 1L;
		
	static {
		ORIGINAL_LOCATION.setAddressId(ORIGINAL_LOCATION_ID);
		ORIGINAL_LOCATION.setStreet("149 Glen");
		ORIGINAL_LOCATION.setCity("Farmingtion");
		ORIGINAL_LOCATION.setState(StateDAO.fetchByCode(ConstantsDAO.CODE_ARKANSAS));
		ORIGINAL_LOCATION.setPostalCode("72730");
		ORIGINAL_LOCATION.setType(TypeDAO.fetchByCode(ConstantsDAO.PHYSICAL_ADDRESS));
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
			criteria.add(Restrictions.eqOrIsNull(ConstantsDAO.STREET_FIELD, address.getStreet()));
			criteria.add(Restrictions.eqOrIsNull(ConstantsDAO.CITY_FIELD, address.getCity()));
			criteria.add(Restrictions.eqOrIsNull(ConstantsDAO.APT_FIELD, address.getApt()));
			criteria.add(Restrictions.eqOrIsNull(ConstantsDAO.UNIT_FIELD, address.getUnit()));
			criteria.add(Restrictions.eqOrIsNull(ConstantsDAO.STATE_FIELD, address.getState()));
			criteria.add(Restrictions.eqOrIsNull(ConstantsDAO.ZIP_FIELD, address.getPostalCode()));
			criteria.add(Restrictions.eqOrIsNull(ConstantsDAO.TYPE_FIELD, address.getType()));
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
