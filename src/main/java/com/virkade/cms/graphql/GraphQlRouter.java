package com.virkade.cms.graphql;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.coxautodev.graphql.tools.SchemaParser;
import com.virkade.cms.hibernate.utilities.HibernateUtilities;

import graphql.schema.GraphQLSchema;
import graphql.servlet.SimpleGraphQLServlet;

@RestController
@RequestMapping("/service")
public class GraphQlRouter extends SimpleGraphQLServlet {

	private static final long serialVersionUID = 465489113L;

	public GraphQlRouter() {
		super(buildSchema());
		HibernateUtilities.getSessionFactory();
	}

	private static GraphQLSchema buildSchema() {
		return SchemaParser.newParser().resolvers(new Query(), new Mutation()).scalars(Scalars.Date, Scalars.Long)
				.file("schema.virkade.graphqls").build().makeExecutableSchema();
	}

	@RequestMapping(method = RequestMethod.POST)
	void graphqlServlet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);

	}

}