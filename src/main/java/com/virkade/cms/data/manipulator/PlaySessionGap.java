package com.virkade.cms.data.manipulator;

import java.sql.Timestamp;
import java.util.Calendar;

class PlaySessionGap {

	private Timestamp startDate;
	private Timestamp endDate;
	private boolean isBookedBefore;
	private boolean isBookedAfter;

	protected PlaySessionGap() {
		this.startDate = new Timestamp(Calendar.getInstance().getTimeInMillis());
		this.endDate = new Timestamp(Calendar.getInstance().getTimeInMillis());
	}

	protected PlaySessionGap(boolean isBookedBefore, Timestamp startDate, Timestamp endDate, boolean isBookedAfter) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.isBookedAfter = isBookedAfter;
		this.isBookedBefore = isBookedBefore;
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

	public boolean isBookedBefore() {
		return isBookedBefore;
	}

	public void setBookedBefore(boolean isBookedBefore) {
		this.isBookedBefore = isBookedBefore;
	}

	public boolean isBookedAfter() {
		return isBookedAfter;
	}

	public void setBookedAfter(boolean isBookedAfter) {
		this.isBookedAfter = isBookedAfter;
	}
}
