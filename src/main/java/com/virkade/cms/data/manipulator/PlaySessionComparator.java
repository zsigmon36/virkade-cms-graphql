package com.virkade.cms.data.manipulator;

import java.util.Comparator;

import com.virkade.cms.model.PlaySession;

public class PlaySessionComparator implements Comparator<PlaySession>{

	@Override
	public int compare(PlaySession o1, PlaySession o2) {
		return o1.getStartDate().compareTo(o2.getStartDate());
	}

}
