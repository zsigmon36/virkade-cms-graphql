package com.virkade.cms.graphql;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coxautodev.graphql.tools.SchemaParser;
import com.virkade.cms.hibernate.utilities.HibernateUtilities;
import com.virkade.cms.model.User;

import graphql.schema.GraphQLSchema;
import graphql.servlet.SimpleGraphQLServlet;

@RestController
public class RestRouter extends SimpleGraphQLServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 465489113L;

	public RestRouter() {
		super(buildSchema());
	}

	private static GraphQLSchema buildSchema() {
		return SchemaParser.newParser()
				.resolvers(new Query())
				.file("schema.virkade.graphql") // parse the schema file created earlier
				.build().makeExecutableSchema();
	}
	
	@RequestMapping("/graphql")
		String graphQl() {
		return "";
	}
	

	@RequestMapping("/addTestUser")
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

	@RequestMapping("/users")
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
