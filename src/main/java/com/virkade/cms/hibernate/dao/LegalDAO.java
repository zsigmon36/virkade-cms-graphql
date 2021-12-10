package com.virkade.cms.hibernate.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.virkade.cms.hibernate.utilities.HibernateUtilities;
import com.virkade.cms.model.Legal;
import com.virkade.cms.model.Type;
import com.virkade.cms.model.User;

public class LegalDAO {

	private static final Logger LOG = Logger.getLogger(LegalDAO.class);
	
	public static Legal fetchByUserAndType(User user, Type type) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		Legal legal = null;
		try {
			hs.beginTransaction();
			Criteria criteria = hs.createCriteria(Legal.class);
			criteria.add(Restrictions.eqOrIsNull(ConstantsDAO.USER_FIELD, user));
			criteria.add(Restrictions.eqOrIsNull(ConstantsDAO.TYPE_FIELD, type));
			legal = (Legal) criteria.uniqueResult();
		} catch (HibernateException he) {
			LOG.error("Hibernate exception fetching legal", he);
		}finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return legal;
	}
	
	public static List<Legal> fetchEnabledExpired(int rows) {
		Timestamp ts = new Timestamp(new Date().getTime());
		List<Legal> legals = new ArrayList<>();
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		try {
			hs.beginTransaction();
			Criteria criteria = hs.createCriteria(Legal.class);
			criteria.add(Restrictions.eq(ConstantsDAO.ENABLED_FIELD, true));
			criteria.add(Restrictions.le(ConstantsDAO.EXPIRE_DATE, ts));
			criteria.setMaxResults(rows);
			legals = criteria.list();
		} catch (HibernateException he) {
			LOG.error("Hibernate exception fetching legals", he);
		}finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return legals;
	}
	
	public static List<Legal> fetchDisabled(int rows) {
		Timestamp ts = new Timestamp(new Date().getTime());
		List<Legal> legals = new ArrayList<>();
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		try {
			hs.beginTransaction();
			Criteria criteria = hs.createCriteria(Legal.class);
			criteria.add(Restrictions.eq(ConstantsDAO.ENABLED_FIELD, false));
			criteria.addOrder(Order.asc(ConstantsDAO.EXPIRE_DATE));
			criteria.setMaxResults(rows);
			legals = criteria.list();
		} catch (HibernateException he) {
			LOG.error("Hibernate exception fetching legals", he);
		}finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return legals;
	}
	
	public static Legal create(Legal legal) throws Exception {
		if (legal.getUser() == null) {
			throw new Exception("user must be selected and valid");
		}
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		try {
			hs.beginTransaction();
			LOG.info("creating legal entry type"+legal.getType()+", for user:"+legal.getUser().getUsername());
			hs.save(legal);
		} catch (HibernateException he) {
			LOG.error("Hibernate exception saving legal", he);
		}finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return legal;
	}
	
	public static Legal delete(Legal legal) throws Exception {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		try {
			hs.beginTransaction();
			LOG.info("deleting legal entry type"+legal.getType()+", for user:"+legal.getUser().getUsername());
			hs.delete(legal);
		} catch (HibernateException he) {
			LOG.error("Hibernate exception deleting legal", he);
		}finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return legal;
	}
	
	public static Legal update(Legal legal) throws Exception {
		if (legal.getUser() == null) {
			throw new Exception("user must be selected and valid");
		}
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		try {
			hs.beginTransaction();
			LOG.info("updating legal entry id"+legal.getLegalDocId()+", for user:"+legal.getUser().getUsername());
			hs.update(legal);
		} catch (HibernateException he) {
			LOG.error("Hibernate exception updating legal", he);
		}finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return legal;
	}

}
