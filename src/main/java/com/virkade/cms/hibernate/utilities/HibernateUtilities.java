package com.virkade.cms.hibernate.utilities;


import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtilities {

	private static SessionFactory sessionfactory;
	private static StandardServiceRegistry serviceReg;
	
	static {
		init();
	}
	private HibernateUtilities() {
		super();
	}
	private static void init() {
		try {
			serviceReg = new StandardServiceRegistryBuilder().configure().build();
			sessionfactory = new MetadataSources(serviceReg).buildMetadata().buildSessionFactory();
		}catch (HibernateException he) {
			he.printStackTrace();
		}  
	}
	
	public static SessionFactory getSessionFactory() {
		if (sessionfactory == null) {
			init();
		}
		return sessionfactory;
	}
}
