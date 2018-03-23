package com.virkade.cms.data.manipulator;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class AttributeUtil {
	public static SortedSet<String> commonAttributes(SortedSet<String> primaryObjAttributes, SortedSet<String> secondaryObjAttribute){
		SortedSet<String> commonList = new TreeSet<String>();
		
		Iterator<String> primObjIterator = primaryObjAttributes.iterator();
		while (primObjIterator.hasNext()){
			String currentPrimeObjAttribute = primObjIterator.next();
			if (secondaryObjAttribute.contains(currentPrimeObjAttribute)) {
				commonList.add(currentPrimeObjAttribute);
			}
		}
		
		return commonList;
		
	}
}
