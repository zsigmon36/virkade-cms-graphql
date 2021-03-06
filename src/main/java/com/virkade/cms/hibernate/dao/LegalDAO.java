package com.virkade.cms.hibernate.dao;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.virkade.cms.hibernate.utilities.HibernateUtilities;
import com.virkade.cms.model.Legal;

public class LegalDAO {

	private static final Logger LOG = Logger.getLogger(LegalDAO.class);
	
	public static Legal fetch(Legal legal) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		try {
			hs.beginTransaction();
			Criteria criteria = hs.createCriteria(Legal.class);
			criteria.add(Restrictions.eqOrIsNull(ConstantsDAO.USER_FIELD, legal.getUser()));
			criteria.add(Restrictions.eqOrIsNull(ConstantsDAO.TYPE_FIELD, legal.getType()));
			legal = (Legal) criteria.uniqueResult();
		} catch (HibernateException he) {
			LOG.error("Hibernate exception fetching legal", he);
		}finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return legal;
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
