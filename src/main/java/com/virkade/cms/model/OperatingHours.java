package com.virkade.cms.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class OperatingHours extends VirkadeModel implements Serializable {

	private static final long serialVersionUID = -7851431078202209522L;
	private Date operatingDate;
	private Location location;
	private Timestamp startAt;
	private Timestamp endAt;
	private Audit audit;

	/**
	 * @return the operatingDate
	 */
	public Date getOperatingDate() {
		return operatingDate;
	}

	/**
	 * @param operatingDate
	 *            the operatingDate to set
	 */
	public void setOperatingDate(Date operatingDate) {
		this.operatingDate = operatingDate;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * @return the startAt
	 */
	public Timestamp getStartAt() {
		return startAt;
	}

	/**
	 * @param startAt
	 *            the startAt to set
	 */
	public void setStartAt(Timestamp startAt) {
		this.startAt = startAt;
	}

	/**
	 * @return the endAt
	 */
	public Timestamp getEndAt() {
		return endAt;
	}

	/**
	 * @param endAt
	 *            the endAt to set
	 */
	public void setEndAt(Timestamp endAt) {
		this.endAt = endAt;
	}

	/**
	 * @return the audit
	 */
	public Audit getAudit() {
		if (this.audit == null) {
			this.audit = new Audit();
		}
		return audit;
	}

	/**
	 * @param audit
	 *            the audit to set
	 */
	public void setAudit(Audit audit) {
		this.audit = audit;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OperatingHours [operatingDate=" + operatingDate + ", location=" + location + ", startAt=" + startAt + ", endAt=" + endAt + ", audit=" + audit + "]";
	}

}
