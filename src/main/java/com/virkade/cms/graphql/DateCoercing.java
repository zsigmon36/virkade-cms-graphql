/**
 * 
 */
package com.virkade.cms.graphql;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import graphql.language.StringValue;
import graphql.schema.Coercing;

/**
 * @author SIGMON-MAIN
 *
 */
public class DateCoercing implements Coercing<Object, String> {
	
	private static final Logger LOG = Logger.getLogger(Mutation.class);
	
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
		LOG.info("input for date is " +String.valueOf(input)+ " type of "+input.getClass().getTypeName());
		if (input instanceof Timestamp) {
			return new Date(((Timestamp) input).getTime());
		} else if (input instanceof Date) {
			return ((Date)input);
		} else if (input instanceof StringValue) {
			Date date = null;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			try {
				StringValue typeInput = (StringValue) input;
				date = format.parse(typeInput.getValue());
			} catch (ParseException e) {
				LOG.error(e);
			}
			return date;
		} else {
			return null;
		}
	}
}
