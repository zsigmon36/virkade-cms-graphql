package com.virkade.cms.graphql;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.coxautodev.graphql.tools.SchemaParser;
import com.virkade.cms.graphql.error.CustomGraphQLErrorHandler;
import com.virkade.cms.hibernate.utilities.CMSSeeds;

import graphql.schema.GraphQLSchema;
import graphql.servlet.DefaultExecutionStrategyProvider;
import graphql.servlet.GraphQLContext;
import graphql.servlet.SimpleGraphQLServlet;

@RestController
@RequestMapping("/service")
public class GraphQlRouter extends SimpleGraphQLServlet {

	private static final long serialVersionUID = 465489113L;
	
	public GraphQlRouter() {
		super(buildSchema(), new DefaultExecutionStrategyProvider(), null, null, new CustomGraphQLErrorHandler(), null);
		CMSSeeds.createDefaultTypes();
		CMSSeeds.createDefaultStatus();
		CMSSeeds.createDefaultRegions();
		CMSSeeds.createDefaultCountries();
		CMSSeeds.createDefaultStates();
		CMSSeeds.createDefaultAddress();
		CMSSeeds.createDefaultUsers();
		CMSSeeds.createDefaultLocation();
		
		//test session
		CMSSeeds.createTestGame();
		CMSSeeds.createTestSession();
		
	}

	private static GraphQLSchema buildSchema() {
		return SchemaParser.newParser().resolvers(new Query(), new Mutation()).scalars(Scalars.Date, Scalars.Long)
				.file("schema.virkade.graphqls").build().makeExecutableSchema();
	}

	@RequestMapping(method = RequestMethod.POST)
	void graphqlServlet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);

	}
	
	@Override
	protected GraphQLContext createContext(Optional<HttpServletRequest> request, Optional<HttpServletResponse> response) {
		String authToken = request
		        .map(req -> req.getHeader("Authorization"))
		        .filter(id -> !id.isEmpty())
		        .map(id -> id.replace("Bearer ", ""))
		        .orElse(null);
		String username = request
		        .map(req -> req.getHeader("Username"))
		        .filter(id -> !id.isEmpty())
		        .orElse(null);

		
		return new AuthContext(username, authToken, request, response);
	}
	
}