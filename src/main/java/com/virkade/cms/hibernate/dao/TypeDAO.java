package com.virkade.cms.hibernate.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.virkade.cms.hibernate.utilities.HibernateUtilities;
import com.virkade.cms.model.Type;

public class TypeDAO {
	public static final String ADMIN_CODE = "ADMN";
	public static final String PROSPECT_CODE = "PRSP";
	public static final String CUSTOMER_CODE = "CSTMR";
	public static final String TYPE_CODE = "code";
	public static final String TYPE_NAME = "name";
	private static final Logger LOG = Logger.getLogger(TypeDAO.class);
	public static final String PHYSICAL_ADDRESS = "PHYSCL_ADRS";
	
	private TypeDAO() {
		
	}
	
	public static List<Type> fetchByName(String typeName) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		List<Type> type = new ArrayList<Type>();
		try {
			hs.beginTransaction();
			Criteria criteria = hs.createCriteria(Type.class);
			criteria.add(Restrictions.eq(TYPE_NAME, typeName));
			type = criteria.list();
		} catch (HibernateException he) {
			LOG.error("Hibernate exception getting types by type name="+typeName, he);
		}finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return type;
	}
	
	public static Type fetchByCode(String code) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		Type type = new Type();
		try {
			hs.beginTransaction();
			Criteria criteria = hs.createCriteria(Type.class);
			criteria.add(Restrictions.eq(TYPE_CODE, code));
			type = (Type) criteria.uniqueResult();
		} catch (HibernateException he) {
			LOG.error("Hibernate exception getting type by type code="+code, he);
		}finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return type;
	}

	public static Type fetchById(long typeId) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		Type type = new Type();
		try {
			hs.beginTransaction();
			hs.get(Type.class.getName(), typeId);
		} catch (HibernateException he) {
			LOG.error("Hibernate exception getting type by type by Id="+typeId, he);
		}finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return type;
	}

	public static Type create(Type type) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		try {
			hs.beginTransaction();
			hs.save(type);
		} catch (HibernateException he) {
			LOG.error("Hibernate exception saving type with type code="+type.getCode(), he);
		}finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return type;
		
	}
}

	
