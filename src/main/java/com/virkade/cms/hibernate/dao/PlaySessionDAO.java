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

public class PlaySessionDAO {
	
	private static final Logger LOG = Logger.getLogger(PlaySessionDAO.class);

	public static List<PlaySession> fetchUserSessions(String username) throws Exception {
		User user = UserDAO.fetchByUsername(username);
		List<PlaySession> playSessions = null;
		if (user == null) {
			throw new Exception("username: "+username+" could not be found");
		}
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		try {
			hs.beginTransaction();
			Criteria criteria = hs.createCriteria(PlaySession.class);
			criteria.add(Restrictions.eq("user", user));
			playSessions = criteria.list();
		} catch (HibernateException he) {
			LOG.error("Hibernate exception get play sessions by UserId=" + user.getUserId(), he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return playSessions;
	}
	
	public static PlaySession fetchSession(Long sessionId) throws Exception {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		PlaySession playSession = null;
		try {
			hs.beginTransaction();
			playSession = hs.get(PlaySession.class, sessionId);
		} catch (HibernateException he) {
			LOG.error("Hibernate exception get play sessions by session Id=" + sessionId, he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return playSession;
	}
	
	public static synchronized PlaySession create(PlaySession session) throws Exception {
		if (session.getLocation() == null || session.getActivity() == null) {
			throw new Exception("location and activity must be selected and valid");
		}
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
			LOG.info("creating play session with start date:"+session.getStartDate() + " for username:"+session.getUsername() + "at location:"+session.getLocation().getName()+ " and activity:"+session.getActivity().getName());
			hs.save(session);
		} catch (HibernateException he) {
			LOG.error("Hibernate exception creating PlaySession=" + session.toString(), he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return session;
	}
	
	public static PlaySession update(PlaySession session) throws Exception {
		if (session.getLocation() == null || session.getActivity() == null) {
			throw new Exception("location and activity must be selected and valid");
		}
		List<PlaySession> possibleSessions = getAvailableSessions(null, session.getLocation(), session.getActivity());
		boolean isOpen = false;
		for (PlaySession curAvailSession : possibleSessions) {
			if (curAvailSession.getStartDate().equals(session.getStartDate()) && curAvailSession.getEndDate().equals(session.getEndDate())) {
				isOpen = true;
				break;
			}
		}
		PlaySession curPlaySession = getById(session.getSessionId());
		if (!isOpen && !curPlaySession.getStartDate().equals(session.getStartDate()) && !curPlaySession.getEndDate().equals(session.getEndDate())) {
			throw new Exception("requested time is not available, someone may have beaten you to it");
		}
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		try {
			hs.beginTransaction();
			LOG.info("updating play session with start date:"+session.getStartDate() + " for username:"+session.getUsername() + "at location:"+session.getLocation().getName()+ " and activity:"+session.getActivity().getName());
			hs.update(session);
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
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				cal.set(Calendar.DATE, (cal.get(Calendar.DATE) + 1));
				Timestamp hiDate = new Timestamp(cal.getTimeInMillis());
				criteria.add(Restrictions.between(ConstantsDAO.END_DATE_FIELD, dateRequested, hiDate));
			}
			
			criteria.add(Restrictions.eq(ConstantsDAO.LOCATION_FIELD, location));
			criteria.add(Restrictions.eq(ConstantsDAO.ACTIVITY_FIELD, activity));
			
			if (payed != null) {
				criteria.add(Restrictions.eq(ConstantsDAO.PAYED_FIELD, payed));
			}
			//very important to have order asc for correct available session calculation
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
	
	public static List<PlaySession> getAllUserSessions(Timestamp dateRequested, User user, Location location, Activity activity, Boolean payed) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		List<PlaySession> playSessions = new ArrayList<PlaySession>();

		try {
			hs.beginTransaction();
			Criteria criteria = hs.createCriteria(PlaySession.class);
			if (dateRequested != null) {
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				cal.set(Calendar.DATE, (cal.get(Calendar.DATE) + 1));
				Timestamp hiDate = new Timestamp(cal.getTimeInMillis());
				criteria.add(Restrictions.between(ConstantsDAO.END_DATE_FIELD, dateRequested, hiDate));
			}
			
			criteria.add(Restrictions.eq(ConstantsDAO.LOCATION_FIELD, location));
			criteria.add(Restrictions.eq(ConstantsDAO.ACTIVITY_FIELD, activity));
			criteria.add(Restrictions.eq(ConstantsDAO.USER_FIELD, user));
			
			if (payed != null) {
				criteria.add(Restrictions.eq(ConstantsDAO.PAYED_FIELD, payed));
			}
			//criteria.addOrder(Order.desc(ConstantsDAO.START_DATE_FIELD));
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
				cal.clear(Calendar.HOUR_OF_DAY);
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
			criteria.addOrder(Order.desc(ConstantsDAO.START_DATE_FIELD));
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
			LOG.info("deleting all play session at location:"+location.getName()+ " and activity:"+activity.getName());
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

	public static PlaySession getById(long playSessionId) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		PlaySession pSession = null;
		try {
			hs.beginTransaction();
			pSession = hs.get(PlaySession.class, playSessionId);
		} catch (HibernateException he) {
			LOG.error("Hibernate exception getting play session by sessionId=" + playSessionId, he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return pSession;
	}
	
	public static PlaySession deleteById(long playSessionId) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		PlaySession pSession = null;
		try {
			hs.beginTransaction();
			LOG.info("deleting play session by id:"+playSessionId);
			pSession = getById(playSessionId);
			hs.delete(PlaySession.class.getName(), pSession);
		} catch (HibernateException he) {
			LOG.error("Hibernate exception deleteing play session by sessionId=" + playSessionId, he);
		} finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return pSession;
	}

}
