/**
 * 
 */
package com.virkade.cms.graphql;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;

import com.virkade.cms.hibernate.dao.ConstantsDAO;

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
		return ((Timestamp) input).toString();
	}

	@Override
	public Object parseValue(Object input) {
		return serialize(input);
	}

	@Override
	public Timestamp parseLiteral(Object input) {
		// parse the string values coming in
		LOG.debug("input for date is " + String.valueOf(input) + " type of " + input.getClass().getTypeName());
		if (input instanceof Timestamp) {
			return ((Timestamp) input);
		} else if (input instanceof Date) {
			Date date = (Date) input;
			Timestamp timestamp = new Timestamp(date.getTime());
			return timestamp;
		} else if (input instanceof StringValue) {
			Timestamp timestamp = null;
			SimpleDateFormat format = new SimpleDateFormat(ConstantsDAO.COMMON_DATE_FORMAT); // "2020-06-20 23:00:00"
			try {
				Calendar cal = Calendar.getInstance();
				StringValue typeInput = (StringValue) input;
				cal.setTime(format.parse(typeInput.getValue()));
				timestamp = new Timestamp(cal.getTimeInMillis());
			} catch (ParseException e) {
				LOG.error(e);
			}
			return timestamp;
		} else {
			return null;
		}
	}
}
