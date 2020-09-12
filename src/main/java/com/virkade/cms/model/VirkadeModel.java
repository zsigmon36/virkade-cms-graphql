package com.virkade.cms.model;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.SortedSet;
import java.util.TreeSet;

import com.google.common.base.Strings;

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
		} else if (className.equalsIgnoreCase(InputPlaySession.class.getName())) {
			return PlaySession.convertInput((InputPlaySession) objToConvert);
		} else if (className.equalsIgnoreCase(InputLocation.class.getName())) {
			return Location.convertInput((InputLocation) objToConvert);
		}
		return null;
	}
	SortedSet<String> getAttributeList(){
		SortedSet<String> attributes = new TreeSet<String>();
		attributes.add("Audit");
		
		return attributes;
	}
	
	public static Audit addAuditToModel(User requestingUser, Audit currentAudit) {
		Audit auditInfo = new Audit();
		auditInfo.setUpdatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		auditInfo.setUpdatedBy(requestingUser.getUsername());
	    if (currentAudit == null || Strings.isNullOrEmpty(currentAudit.getCreatedBy())) {
	    	auditInfo.setCreatedBy(requestingUser.getUsername());
			auditInfo.setCreatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
	    } else {
	    	auditInfo.setCreatedBy(currentAudit.getCreatedBy());
			auditInfo.setCreatedAt(currentAudit.getCreatedAt());
	    }
		return auditInfo;
	}
	
}
