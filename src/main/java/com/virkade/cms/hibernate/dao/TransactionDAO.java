package com.virkade.cms.hibernate.dao;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.virkade.cms.hibernate.utilities.HibernateUtilities;
import com.virkade.cms.model.Transaction;

public class TransactionDAO {

	private static final Logger LOG = Logger.getLogger(TransactionDAO.class);
	
	public static Transaction fetchById(Long id) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		Transaction transaction = new Transaction();
		try {
			hs.beginTransaction();
			transaction = hs.get(Transaction.class, id);
		} catch (HibernateException he) {
			LOG.error("Hibernate exception get Transaction by id=" + id, he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}

		return transaction;
	}
	

	public static Transaction create(Transaction transaction) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		try {
			hs.beginTransaction();
			hs.save(transaction);
		} catch (HibernateException he) {
			LOG.error("Hibernate exception creating transaction=" + transaction.toString(), he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return transaction;
	}
	
	public static Transaction update(Transaction transaction) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		try {
			hs.beginTransaction();
			hs.update(transaction);
		} catch (HibernateException he) {
			LOG.error("Hibernate exception updating transaction=" + transaction.toString(), he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return transaction;
	}

	public static Transaction upsert(Transaction transaction) {
		if (transaction.getTransactionId() <= 0) {
			return create(transaction);
		} else {
			return update(transaction);
		}
	}

}
