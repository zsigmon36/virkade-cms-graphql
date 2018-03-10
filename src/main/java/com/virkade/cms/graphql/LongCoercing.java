/**
 * 
 */
package com.virkade.cms.graphql;

import graphql.language.StringValue;
import graphql.schema.Coercing;

/**
 * @author SIGMON-MAIN
 *
 */
public class LongCoercing implements Coercing<Object, String> {

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

}
