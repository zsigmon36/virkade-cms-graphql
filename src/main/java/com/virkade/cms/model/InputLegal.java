package com.virkade.cms.model;

import java.sql.Timestamp;

public class InputLegal {
	
	private long legalDocId;
	private String username;
	private String typeCode;
	
	private boolean agree;
	private Timestamp activeDate;
	private Timestamp expireDate;
	private boolean enabled;
	
	/**
	 * @return the legalDocId
	 */
	public long getLegalDocId() {
		return legalDocId;
	}
	/**
	 * @param legalDocId the legalDocId to set
	 */
	public void setLegalDocId(long legalDocId) {
		this.legalDocId = legalDocId;
	}
	
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the typeCode
	 */
	public String getTypeCode() {
		return typeCode;
	}
	/**
	 * @param typeCode the typeCode to set
	 */
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	/**
	 * @return the agree
	 */
	public boolean isAgree() {
		return agree;
	}
	/**
	 * @param agree the agree to set
	 */
	public void setAgree(boolean agree) {
		this.agree = agree;
	}
	/**
	 * @return the activeDate
	 */
	public Timestamp getActiveDate() {
		return activeDate;
	}
	/**
	 * @param activeDate the activeDate to set
	 */
	public void setActiveDate(Timestamp activeDate) {
		this.activeDate = activeDate;
	}
	/**
	 * @return the expireDate
	 */
	public Timestamp getExpireDate() {
		return expireDate;
	}
	/**
	 * @param expireDate the expireDate to set
	 */
	public void setExpireDate(Timestamp expireDate) {
		this.expireDate = expireDate;
	}
	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}
	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Legal [legalDocId=" + legalDocId + ", username=" + username + ", typecode=" + typeCode + ", agree=" + agree + ", activeDate=" + activeDate 
				+ ", expireDate=" + expireDate + ", enabled=" + enabled + "]";
	}
	
}
