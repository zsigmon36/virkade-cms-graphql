package com.virkade.cms.hibernate.dao;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.virkade.cms.data.manipulator.PlaySessionCalculator;
import com.virkade.cms.hibernate.utilities.HibernateUtilities;
import com.virkade.cms.model.Activity;
import com.virkade.cms.model.Location;
import com.virkade.cms.model.OperatingHours;
import com.virkade.cms.model.PlaySession;
import com.virkade.cms.model.User;

public class SessionDAO {

	private static final Logger LOG = Logger.getLogger(SessionDAO.class);

	public static PlaySession create(PlaySession session) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		try {
			hs.beginTransaction();
			hs.save(session);
		} catch (HibernateException he) {
			LOG.error("Hibernate exception creating PlaySession=" + session.toString(), he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return session;

	}

	public static List<PlaySession> getAllSessions() {
		return getAllSessions(null);
	}

	public static List<PlaySession> getAllSessionsToday() {
		return getAllSessions(new Timestamp(Calendar.getInstance().getTimeInMillis()));
	}

	public static List<PlaySession> getAllSessions(Timestamp dateRequested) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		List<PlaySession> playSessions = new ArrayList<PlaySession>();

		try {
			hs.beginTransaction();
			Criteria criteria = hs.createCriteria(PlaySession.class);
			if (dateRequested != null) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(dateRequested);
				cal.clear(Calendar.HOUR);
				cal.clear(Calendar.MINUTE);
				cal.clear(Calendar.SECOND);
				cal.clear(Calendar.MILLISECOND);
				cal.set(Calendar.DATE, (cal.get(Calendar.DATE) + 1));
				Timestamp hiDate = new Timestamp(cal.getTimeInMillis());
				criteria.add(Restrictions.between(ConstantsDAO.END_DATE_FIELD, dateRequested, hiDate));
			}
			criteria.addOrder(Order.asc(ConstantsDAO.START_DATE_FIELD));
			playSessions = criteria.list();
		} catch (HibernateException he) {
			LOG.error("Hibernate exception getting PlaySessions", he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return playSessions;

	}

	public static List<PlaySession> getAvailableSessions(Timestamp dateRequested, Location location, Activity activity) {
		List<PlaySession> curSessions = null;
		OperatingHours opHours = new OperatingHours();

		if (dateRequested == null) {
			dateRequested = new Timestamp(Calendar.getInstance().getTimeInMillis());
			opHours = OperatingHoursDAO.getTodayOperation();
			curSessions = getAllSessionsToday();
		} else {
			opHours = OperatingHoursDAO.getOperation(new Date(dateRequested.getTime()));
			curSessions = getAllSessions(dateRequested);
		}
		List<PlaySession> availableSessions = null;
		try {
			availableSessions = PlaySessionCalculator.getAvailableSessions(dateRequested, opHours.getEndAt(), curSessions, opHours, location, activity);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new HibernateException("There was a thread interupt and we could not get all the available sessions :(", e);
		}
		return availableSessions;

	}

	public static List<PlaySession> getUserSessions(User user, Timestamp dateRequested) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		List<PlaySession> playSessions = new ArrayList<PlaySession>();

		try {
			hs.beginTransaction();
			Criteria criteria = hs.createCriteria(PlaySession.class);
			if (dateRequested != null) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(dateRequested);
				cal.clear(Calendar.HOUR);
				cal.clear(Calendar.MINUTE);
				cal.clear(Calendar.SECOND);
				cal.clear(Calendar.MILLISECOND);

				Timestamp loDate = new Timestamp(cal.getTimeInMillis());

				cal.set(Calendar.DATE, (cal.get(Calendar.DATE) + 1));
				Timestamp hiDate = new Timestamp(cal.getTimeInMillis());
				criteria.add(Restrictions.between(ConstantsDAO.END_DATE_FIELD, loDate, hiDate));
			}
			criteria.addOrder(Order.asc(ConstantsDAO.START_DATE_FIELD));
			criteria.add(Restrictions.eq(ConstantsDAO.USER_FIELD, user));
			criteria.addOrder(Order.asc(ConstantsDAO.START_DATE_FIELD));
			playSessions = criteria.list();
		} catch (HibernateException he) {
			LOG.error("Hibernate exception getting PlaySessions for user=" + user, he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return playSessions;

	}

}
