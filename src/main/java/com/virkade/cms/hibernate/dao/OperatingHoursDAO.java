package com.virkade.cms.hibernate.dao;

import java.sql.Date;
import java.util.Calendar;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.virkade.cms.hibernate.utilities.HibernateUtilities;
import com.virkade.cms.model.Location;
import com.virkade.cms.model.OperatingHours;

public class OperatingHoursDAO {

	private static final Logger LOG = Logger.getLogger(OperatingHoursDAO.class);

	public static OperatingHours getOperation(Date date, Location location) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		OperatingHours opHours = null;
		try {
			hs.beginTransaction();
			Criteria criteria = hs.createCriteria(OperatingHours.class);
			criteria.add(Restrictions.eq(ConstantsDAO.OPERATING_DATE_FIELD, date));
			criteria.add(Restrictions.eq(ConstantsDAO.LOCATION_FIELD, location));
			opHours = (OperatingHours) criteria.uniqueResult();
		} catch (HibernateException he) {
			LOG.error("Hibernate exception getting operating hours with date=" + date, he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return opHours;
	}

	public static OperatingHours getTodayOperation(Location location) {
		return getOperation(new Date(Calendar.getInstance().getTimeInMillis()), location);
	}

	public static OperatingHours create(OperatingHours opHours) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		try {
			hs.beginTransaction();
			hs.save(opHours);
		} catch (HibernateException he) {
			LOG.error("Hibernate exception creating operating hours=" + opHours.toString(), he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return opHours;
	}

}
