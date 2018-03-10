package com.virkade.cms.graphql;

import graphql.schema.GraphQLScalarType;

public class Scalars {

	public static GraphQLScalarType Date = new GraphQLScalarType("Date", "Date scalar", new DateCoercing());
	
	public static GraphQLScalarType Long = new GraphQLScalarType("Long", "Long scalar", new LongCoercing());
	
}
