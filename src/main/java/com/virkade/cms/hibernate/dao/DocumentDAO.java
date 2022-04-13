package com.virkade.cms.hibernate.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.virkade.cms.hibernate.utilities.HibernateUtilities;
import com.virkade.cms.model.Document;
import com.virkade.cms.model.Type;

public class DocumentDAO {

	private static final Logger LOG = Logger.getLogger(DocumentDAO.class);
	
	public static Document fetch(Document doc) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		try {
			hs.beginTransaction();
			Criteria criteria = hs.createCriteria(Document.class);
			doc = (Document) criteria.uniqueResult();
		} catch (HibernateException he) {
			LOG.error("Hibernate exception fetching legal", he);
		}finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return doc;
	}
	
	public static Document fetchByTitleAndVersion(String title, float ver) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		Document doc = null;
		try {
			hs.beginTransaction();
			Criteria criteria = hs.createCriteria(Document.class);
			criteria.add(Restrictions.eqOrIsNull(ConstantsDAO.TITLE_FIELD, title));
			criteria.add(Restrictions.eqOrIsNull(ConstantsDAO.VERSION_FIELD, ver));
			doc = (Document) criteria.uniqueResult();
		} catch (HibernateException he) {
			LOG.error("Hibernate exception fetching legal", he);
		}finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return doc;
	}
	
	public static Document fetchLatestDocumentByType(String typeCode) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		List<Document> docs = null;
		Type type = TypeDAO.fetchByCode(typeCode);
		try {
			hs.beginTransaction();
			Criteria criteria = hs.createCriteria(Document.class);
			criteria.add(Restrictions.eq(ConstantsDAO.TYPE_FIELD, type));
			criteria.add(Restrictions.eq(ConstantsDAO.ENABLED_FIELD, true));
			docs = criteria.list();
		} catch (HibernateException he) {
			LOG.error("Hibernate exception fetching legal", he);
		}finally {
			hs.getTransaction().commit();
			hs.close();
		}
		Document doc = new Document();
		for (Document curDoc : docs) {
			if (curDoc.getVersion() > doc.getVersion()) {
				doc = curDoc;
			}
		}
		
		return doc;
	}
	
	public static Document create(Document doc) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		try {
			hs.beginTransaction();
			LOG.info("creating doc entry type"+doc.getType()+", with title:"+doc.getTitle());
			hs.save(doc);
		} catch (HibernateException he) {
			LOG.error("Hibernate exception saving doc", he);
		}finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return doc;
	}
	
	public static Document update(Document doc) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		try {
			hs.beginTransaction();
			LOG.info("creating doc entry type"+doc.getType()+", with title:"+doc.getTitle());
			hs.update(doc);
		} catch (HibernateException he) {
			LOG.error("Hibernate exception saving doc", he);
		}finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return doc;
	}

	public static Document fetchByDocId(long docId) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		Document doc = null;
		try {
			hs.beginTransaction();
			doc = hs.get(Document.class, docId);
		} catch (HibernateException he) {
			LOG.error("Hibernate exception fetching document", he);
		}finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return doc;
	}

}
