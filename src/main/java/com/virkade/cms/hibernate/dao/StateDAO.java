package com.virkade.cms.hibernate.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.virkade.cms.hibernate.utilities.HibernateUtilities;
import com.virkade.cms.model.State;

public class StateDAO {

	private static final Logger LOG = Logger.getLogger(StateDAO.class);

	public static State create(State state) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		try {
			hs.beginTransaction();
			LOG.info("creating state with state name:"+state.getName());
			hs.save(state);
		} catch (HibernateException he) {
			LOG.error("Hibernate exception creating state=" + state.toString(), he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return state;
	}
	
	public static State getByCode(String code) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		State state = new State();
		try {
			hs.beginTransaction();
			Criteria criteria = hs.createCriteria(State.class);
			criteria.add(Restrictions.eq(ConstantsDAO.STATE_CODE_FIELD, code));
			state = (State) criteria.uniqueResult();
		} catch (HibernateException he) {
			LOG.error("Hibernate exception get state by StateCode=" + code, he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}
		if (state == null) {
			throw new HibernateException("No state found by StateCode=" + code);
		}
		return state;
	}
	
	public static State fetchByCode(String code) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		State state = new State();
		try {
			hs.beginTransaction();
			Criteria criteria = hs.createCriteria(State.class);
			criteria.add(Restrictions.eq(ConstantsDAO.STATE_CODE_FIELD, code));
			state = (State) criteria.uniqueResult();
		} catch (HibernateException he) {
			LOG.error("Hibernate exception get state by StateCode=" + code, he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return state;
	}
	
	public static State fetchById(long id) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		State state = new State();
		try {
			hs.beginTransaction();
			Criteria criteria = hs.createCriteria(State.class);
			criteria.add(Restrictions.eq(ConstantsDAO.STATEID_FIELD, id));
			state = (State) criteria.uniqueResult();
		} catch (HibernateException he) {
			LOG.error("Hibernate exception get state by StateID=" + id, he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return state;
	}
	
	public static State getById(long id) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		State state = new State();
		try {
			hs.beginTransaction();
			Criteria criteria = hs.createCriteria(State.class);
			criteria.add(Restrictions.eq(ConstantsDAO.STATEID_FIELD, id));
			state = (State) criteria.uniqueResult();
			
		} catch (HibernateException he) {
			LOG.error("Hibernate exception get state by StateID=" + id, he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}
		if (state == null) {
			throw new HibernateException("No state foundby StateID=" + id);
		}
		return state;
	}

	public static List<State> fetchAll() {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		List<State> states = null;
		try {
			hs.beginTransaction();
			Criteria criteria = hs.createCriteria(State.class);
			criteria.addOrder(Order.asc(ConstantsDAO.NAME_FIELD));
			states = criteria.list();
		} catch (HibernateException he) {
			LOG.error("Hibernate exception getting all states", he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return states;
	}

}
