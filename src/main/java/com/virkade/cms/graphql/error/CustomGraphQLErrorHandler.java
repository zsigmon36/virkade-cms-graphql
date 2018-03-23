/**
 * 
 */
package com.virkade.cms.graphql.error;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.servlet.GraphQLErrorHandler;

/**
 * @author SIGMON-MAIN
 *
 */
public class CustomGraphQLErrorHandler implements GraphQLErrorHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see graphql.servlet.GraphQLErrorHandler#processErrors(java.util.List)
	 */
	@Override
	public List<GraphQLError> processErrors(List<GraphQLError> errors) {
		 
		List<GraphQLError> clientErrors = errors.stream()
				.filter(e -> isClientError(e))
				.map(e -> e instanceof ExceptionWhileDataFetching 
						? new CustomError((ExceptionWhileDataFetching) e) 
								: e).collect(Collectors.toList());
		
		List<GraphQLError> serverErrors = errors.stream()
				.filter(e -> !isClientError(e))
				.map(e -> e instanceof ExceptionWhileDataFetching 
						? new CustomError((ExceptionWhileDataFetching) e) 
								: e).collect(Collectors.toList());
		List<GraphQLError> allErrors = new ArrayList<>();
		allErrors.addAll(clientErrors);
		allErrors.addAll(serverErrors);
		return allErrors;
	}
				
				
	protected boolean isClientError(GraphQLError error) {
		return !(error instanceof ExceptionWhileDataFetching || error instanceof Throwable);
	}
}
