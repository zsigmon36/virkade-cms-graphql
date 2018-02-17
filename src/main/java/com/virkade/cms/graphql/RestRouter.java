package com.virkade.cms.graphql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.virkade.cms.hibernate.utilities.HibernateUtilities;

@RestController
public class RestRouter {

	@RequestMapping("/")
	String home() {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		hs.close();
		return "<h1>VirKade GraphQL Server</h1>";
	}

}
