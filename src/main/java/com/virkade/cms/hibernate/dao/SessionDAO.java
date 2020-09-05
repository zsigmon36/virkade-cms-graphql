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

	public static synchronized PlaySession create(PlaySession session) throws Exception {
		List<PlaySession> possibleSessions = getAvailableSessions(null, session.getLocation(), session.getActivity());
		boolean isOpen = false;
		for (PlaySession curAvailSession : possibleSessions) {
			if (curAvailSession.getStartDate().equals(session.getStartDate()) && curAvailSession.getEndDate().equals(session.getEndDate())) {
				isOpen = true;
				break;
			}
		}
		if (!isOpen) {
			throw new Exception("requested time is not available, someone may have beaten you to it");
		}
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

	public static List<PlaySession> getAllSessions(Location location, Activity activity, Boolean payed) {
		return getAllSessions(null, location, activity, payed);
	}

	public static List<PlaySession> getAllSessionsToday(Location location, Activity activity, Boolean payed) {
		return getAllSessions(new Timestamp(Calendar.getInstance().getTimeInMillis()), location, activity, payed);
	}

	public static List<PlaySession> getAllSessions(Timestamp dateRequested, Location location, Activity activity, Boolean payed) {
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
				criteria.add(Restrictions.eq(ConstantsDAO.LOCATION_FIELD, location));
				criteria.add(Restrictions.between(ConstantsDAO.END_DATE_FIELD, dateRequested, hiDate));
				criteria.add(Restrictions.eq(ConstantsDAO.ACTIVITY_FIELD, activity));
				if (payed != null) {
					criteria.add(Restrictions.eq(ConstantsDAO.PAYED_FIELD, payed));
				}
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
			opHours = OperatingHoursDAO.getTodayOperation(location);
			curSessions = getAllSessionsToday(location, activity, null);
		} else {
			opHours = OperatingHoursDAO.getOperation(new Date(dateRequested.getTime()), location);
			curSessions = getAllSessions(dateRequested, location, activity, null);
		}
		List<PlaySession> availableSessions = null;
		try {
			availableSessions = PlaySessionCalculator.getAvailableSessions(dateRequested, opHours.getEndAt(), curSessions, opHours, location, activity);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new HibernateException("There was a thread interrupt and we could not get all the available sessions :(", e);
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

	public static boolean deleteAll(Timestamp dateRequested, Location location, Activity activity) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		List<PlaySession> playSessions = getAllSessions(dateRequested, location, activity, null);
		LOG.warn(String.format("%s play sessions will be deleted based on date requested:%s, location:%s, & activity:%s", playSessions.size(), (dateRequested == null ? "all" : dateRequested),
				location.getName(), activity.getName()));
		boolean results = false;
		try {
			hs.beginTransaction();
			for (PlaySession session : playSessions) {
				String entityName = session.getClass().getName();
				hs.delete(entityName, session);
			}
		} catch (HibernateException he) {
			LOG.error("Hibernate exception deleting PlaySessions", he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
			results = true;
		}
		return results;

	}

}
