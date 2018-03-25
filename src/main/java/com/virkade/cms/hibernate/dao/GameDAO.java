package com.virkade.cms.hibernate.dao;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.virkade.cms.hibernate.utilities.HibernateUtilities;
import com.virkade.cms.model.Game;
import com.virkade.cms.model.User;

public class GameDAO {

	private static final Logger LOG = Logger.getLogger(GameDAO.class);
	// field constants
	private static final String NAME_FIELD = "name";

	public static Game fetchByName(String name) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		Game game = new Game();
		try {
			hs.beginTransaction();
			Criteria criteria = hs.createCriteria(Game.class);
			criteria.add(Restrictions.eq(NAME_FIELD, name));
			game = (Game) criteria.uniqueResult();
		} catch (HibernateException he) {
			LOG.error("Hibernate exception get Game by name=" + name, he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}

		return game;
	}

	public static Game create(Game game) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		try {
			hs.beginTransaction();
			hs.save(game);
		} catch (HibernateException he) {
			LOG.error("Hibernate exception creating game=" + game.toString(), he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return game;
	}

}
