package com.virkade.cms.hibernate.dao;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.virkade.cms.hibernate.utilities.HibernateUtilities;
import com.virkade.cms.model.Comment;

public class CommentDAO {

	private static final Logger LOG = Logger.getLogger(CommentDAO.class);
	
	public static Comment fetch(Comment comment) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		try {
			hs.beginTransaction();
			Criteria criteria = hs.createCriteria(Comment.class);
			criteria.add(Restrictions.eqOrIsNull(ConstantsDAO.COMMENT_CONTENT_FIELD, comment.getCommentContent()));
			criteria.add(Restrictions.eqOrIsNull(ConstantsDAO.TYPE_FIELD, comment.getType()));
			criteria.add(Restrictions.eqOrIsNull(ConstantsDAO.USER_FIELD, comment.getUser()));
			comment = (Comment) criteria.uniqueResult();
		} catch (HibernateException he) {
			LOG.error("Hibernate exception fetching comment", he);
		}finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return comment;
	}
	
	public static Comment create(Comment comment) {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		try {
			hs.beginTransaction();
			LOG.info("creating comment entry type:"+comment.getType()+", for user:"+ comment.getUsername());
			hs.save(comment);
		} catch (HibernateException he) {
			LOG.error("Hibernate exception saving comment", he);
		}finally {
			hs.getTransaction().commit();
			hs.close();
		}
		return comment;
	}

}
