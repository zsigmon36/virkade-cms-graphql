package com.virkade.cms.graphql;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.coxautodev.graphql.tools.SchemaParser;
import com.virkade.cms.BootApplication;
import com.virkade.cms.communication.EmailUtil;
import com.virkade.cms.graphql.error.CustomGraphQLErrorHandler;
import com.virkade.cms.hibernate.dao.LocationDAO;
import com.virkade.cms.hibernate.utilities.CMSSeeds;

import graphql.schema.GraphQLSchema;
import graphql.servlet.DefaultExecutionStrategyProvider;
import graphql.servlet.GraphQLContext;
import graphql.servlet.SimpleGraphQLServlet;

@RestController
@RequestMapping("/service")
public class GraphQlRouter extends SimpleGraphQLServlet {
	private static final Logger LOG = Logger.getLogger(SimpleGraphQLServlet.class);
	private static final long serialVersionUID = 465489113L;

	public GraphQlRouter() {
		super(buildSchema(), new DefaultExecutionStrategyProvider(), null, null, new CustomGraphQLErrorHandler(), null);

		LOG.info("checking default values are present in the database");
		CMSSeeds.createDefaultTypes();
		CMSSeeds.createDefaultStatus();
		CMSSeeds.createDefaultRegions();
		CMSSeeds.createDefaultCountries();
		CMSSeeds.createDefaultStates();
		CMSSeeds.createDefaultAddress();
		CMSSeeds.createDefaultUsers();
		CMSSeeds.createDefaultLocation();
		CMSSeeds.createDefaultActivity();
		BootApplication.startWorkDay(LocationDAO.getDefault());
		
		//start work day and start over each day at midnight
		LOG.info("adding task to start new work day if going past midnight.");
		Timer timer = new Timer();
		
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				BootApplication.startWorkDay(LocationDAO.getDefault());
			}
		};
		
		Calendar firstRefreshDate = Calendar.getInstance();
		LOG.info("current date:"+firstRefreshDate.getTime().toString());
		firstRefreshDate.set(Calendar.DAY_OF_MONTH, (firstRefreshDate.get(Calendar.DAY_OF_MONTH)+1));
		firstRefreshDate.set(Calendar.HOUR, 0);
		firstRefreshDate.set(Calendar.MINUTE, 0);
		firstRefreshDate.set(Calendar.SECOND, 0);
		firstRefreshDate.set(Calendar.MILLISECOND, 0);
		LOG.info("roll over date:"+firstRefreshDate.getTime().toString());
		//one day
		long period = 1000 * 60 * 60 * 24;
		LOG.info("roll over every:"+period+" millis");
		timer.schedule(task, new Date(firstRefreshDate.getTimeInMillis()), period);

		// test
		EmailUtil.sendSimpleMail("sigmonbus36@gmail.com", "VirKade CMS System Starting",
				"this is a test message for the email utility");

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
	protected GraphQLContext createContext(Optional<HttpServletRequest> request,
			Optional<HttpServletResponse> response) {
		String authToken = request.map(req -> req.getHeader("Authorization"))
				.filter(id -> !id.isEmpty())
				.map(id -> id.replace("Bearer ", ""))
				.orElse(null);
		String username = request.map(req -> req.getHeader("Username"))
				.filter(id -> !id.isEmpty())
				.orElse(null);

		return new AuthContext(username, authToken, request, response);
	}

}