package com.virkade.cms.hibernate.dao;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.virkade.cms.hibernate.utilities.HibernateUtilities;
import com.virkade.cms.model.State;

public class StateDAO {

	private static final Logger LOG = Logger.getLogger(StateDAO.class);
	
	//common constants
	public static final String CODE_ARKANSAS = "AR";
	public static final String NAME_ARKANSAS = "Arkansas";
	//field constants
	private static final String CODE_FIELD = "stateCode";

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

}
