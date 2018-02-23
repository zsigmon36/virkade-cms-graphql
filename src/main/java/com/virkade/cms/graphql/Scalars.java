package com.virkade.cms.graphql;

import java.sql.Timestamp;
import java.util.Date;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.GraphQLScalarType;

public class Scalars {

	public static GraphQLScalarType date = new GraphQLScalarType("Date", "Date scalar", new Coercing() {
		@Override
		public String serialize(Object input) {
			// serialize the ZonedDateTime into string on the way out
			return new Date(((Timestamp) input).getTime()).toString();
		}

		@Override
		public Object parseValue(Object input) {
			return serialize(input);
		}

		@Override
		public Date parseLiteral(Object input) {
			// parse the string values coming in
			if (input instanceof Timestamp) {
				return new Date(((Timestamp) input).getTime());
			} else {
				return null;
			}
		}
	});
	
	public static GraphQLScalarType Long = new GraphQLScalarType("Long", "Long scalar", new Coercing() {
		@Override
		public String serialize(Object input) {
			return ((Long) input).toString();
		}

		@Override
		public Object parseValue(Object input) {
			return serialize(input);
		}

		@Override
		public Long parseLiteral(Object input) {
			// parse the string values coming in
			if (input instanceof StringValue) {
				return ((java.lang.Long) input).longValue();
			}else if (input instanceof Long) {
					return ((java.lang.Long) input).longValue();
			} else {
				return null;
			}
		}
	});
}
