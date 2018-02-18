package com.virkade.cms.graphql;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.web.bind.annotation.RestController;

import com.virkade.cms.hibernate.utilities.HibernateUtilities;
import com.virkade.cms.model.User;

@RestController
public class RestRouter {

		
	String addTestUser() {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		hs.beginTransaction();
		User user = new User();
		user.setFirstName("testFN-" + new Random().nextDouble());
		user.setLastName("testLN-" + new Random().nextDouble());
		user.setCreatedAt(new Date());
		hs.save(user);
		hs.getTransaction().commit();
		hs.close();
		return "<h1>VirKade GraphQL Server</h1><p>added user " + user.getFirstName() + " " + user.getLastName()
				+ "</p>";
	}

	String getUsers() {
		SessionFactory hsf = HibernateUtilities.getSessionFactory();
		Session hs = hsf.openSession();
		hs.beginTransaction();

		List<User> users = hs.createCriteria(User.class).list();

		hs.getTransaction().commit();
		hs.close();
		StringBuilder sb = new StringBuilder();
		sb.append("<h1>VirKade GraphQL Server</h1>");
		for (User user : users) {
			sb.append("<p>");
			sb.append(user.toString());
			sb.append("</p>");
		}
		return sb.toString();
	}

}
