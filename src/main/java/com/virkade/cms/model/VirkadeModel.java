package com.virkade.cms.model;

import java.util.SortedSet;
import java.util.TreeSet;

public class VirkadeModel {
	
	public static VirkadeModel convertObj(String className, Object objToConvert) throws Exception{
		if (className.equalsIgnoreCase(InputUser.class.getName())) {
			return User.convertInput((InputUser) objToConvert);
		} else if (className.equalsIgnoreCase(InputAddress.class.getName())) {
			return Address.convertInput((InputAddress) objToConvert);
		}
		return null;
	}
	SortedSet<String> getAttributeList(){
		SortedSet<String> attributes = new TreeSet<String>();
		attributes.add("Audit");
		
		return attributes;
	}
	
}
