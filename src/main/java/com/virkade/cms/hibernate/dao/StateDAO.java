package com.virkade.cms.hibernate.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.virkade.cms.hibernate.utilities.HibernateUtilities;
import com.virkade.cms.model.State;
import com.virkade.cms.model.User;

public class StateDAO {

	private static final Logger LOG = Logger.getLogger(StateDAO.class);
	
	//common constants
	public static final String CODE_ARKANSAS = "AR";
	public static final String NAME_ARKANSAS = "Arkansas";
	//field constants
	private static final String CODE_FIELD = "stateCode";
	private static final String ID_FIELD = "stateId";

	public static State create(State state) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		try {
			hs.beginTransaction();
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
			criteria.add(Restrictions.eq(CODE_FIELD, code));
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
			criteria.add(Restrictions.eq(CODE_FIELD, code));
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
			criteria.add(Restrictions.eq(ID_FIELD, id));
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
			criteria.add(Restrictions.eq(ID_FIELD, id));
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
			states = hs.createCriteria(State.class).list();
		} catch (HibernateException he) {
			LOG.error("Hibernate exception getting all states", he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return states;
	}

}
