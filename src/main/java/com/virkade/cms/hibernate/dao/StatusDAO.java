package com.virkade.cms.hibernate.dao;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.virkade.cms.hibernate.utilities.HibernateUtilities;
import com.virkade.cms.model.Status;

public class StatusDAO {

	private static final Logger LOG = Logger.getLogger(StatusDAO.class);
	
	public static Status fetchById(long statusId) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		Status status = null;
		try {
			hs.beginTransaction();
			status = hs.get(Status.class, statusId);
		} catch (HibernateException he) {
			LOG.error("Hibernate exception getting Status by statusId=" + statusId, he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return status;
	}
	
	public static Status create(Status status) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		try {
			hs.beginTransaction();
			hs.save(status);
		} catch (HibernateException he) {
			LOG.error("Hibernate exception creating Status with statusCode=" + status.getCode(), he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return status;
	}

	public static Status fetchByCode(String code) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		Status status = null;
		try {
			hs.beginTransaction();
			Criteria criteria = hs.createCriteria(Status.class);
			criteria.add(Restrictions.eq(ConstantsDAO.CODE_FIELD, code));
			status = (Status) criteria.uniqueResult();
		} catch (HibernateException he) {
			LOG.error("Hibernate exception getting Status by statusCode=" + code, he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return status;
	}

}
