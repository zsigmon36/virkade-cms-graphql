/**
 * 
 */
package com.virkade.cms.graphql;

import java.sql.Timestamp;
import java.util.Date;

import graphql.schema.Coercing;

/**
 * @author SIGMON-MAIN
 *
 */
public class DateCoercing implements Coercing<Object, String> {
	@Override
	public String serialize(Object input) {
		// serialize the ZonedDateTime into string on the way out
		return ((Date)input).toString();
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
		} else if (input instanceof Date) {
			return ((Date)input);
		} else {
			return null;
		}
	}
}
