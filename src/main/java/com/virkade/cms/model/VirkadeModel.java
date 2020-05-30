package com.virkade.cms.model;

import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

public class VirkadeModel {
	
	public static VirkadeModel convertObj(String className, Object objToConvert) throws Exception{
		if (className.equalsIgnoreCase(InputUser.class.getName())) {
			return User.convertInput((InputUser) objToConvert);
		} else if (className.equalsIgnoreCase(InputAddress.class.getName())) {
			return Address.convertInput((InputAddress) objToConvert);
		} else if (className.equalsIgnoreCase(InputPhone.class.getName())) {
			return Phone.convertInput((InputPhone) objToConvert);
		} else if (className.equalsIgnoreCase(InputComment.class.getName())) {
			return Comment.convertInput((InputComment) objToConvert);
		} else if (className.equalsIgnoreCase(InputLegal.class.getName())) {
			return Legal.convertInput((InputLegal) objToConvert);
		}
		return null;
	}
	SortedSet<String> getAttributeList(){
		SortedSet<String> attributes = new TreeSet<String>();
		attributes.add("Audit");
		
		return attributes;
	}
	
	public static Audit addAuditToModel(User requestingUser,  boolean isNew) {
		Audit auditInfo = new Audit();
		auditInfo.setUpdatedAt(new Date());
		auditInfo.setUpdatedBy(requestingUser.getUsername());
	    if (isNew) {
	    	auditInfo.setCreatedBy(requestingUser.getUsername());
			auditInfo.setCreatedAt(new Date());
	    }
		return auditInfo;
	}
	
}
