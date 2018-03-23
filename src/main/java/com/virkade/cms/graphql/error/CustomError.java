/**
 * 
 */
package com.virkade.cms.graphql.error;

import com.fasterxml.jackson.annotation.JsonIgnore;

import graphql.ExceptionWhileDataFetching;

/**
 * @author SIGMON-MAIN
 *
 */
public class CustomError extends ExceptionWhileDataFetching {

	public CustomError(ExceptionWhileDataFetching exception) {
		super(exception.getException());
	}
	
	@Override
	@JsonIgnore
	public Throwable getException() {
		return super.getException();
		
	}

}
