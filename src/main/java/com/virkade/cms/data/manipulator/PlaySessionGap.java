package com.virkade.cms.data.manipulator;

import java.sql.Timestamp;
import java.util.Calendar;

class PlaySessionGap {

	private Timestamp startDate;
	private Timestamp endDate;

	protected PlaySessionGap() {
		this.startDate = new Timestamp(Calendar.getInstance().getTimeInMillis());
		this.endDate = new Timestamp(Calendar.getInstance().getTimeInMillis());
	}

	protected PlaySessionGap(Timestamp startDate, Timestamp endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
	}

	/**
	 * @return the startDate
	 */
	Timestamp getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	Timestamp getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
}
