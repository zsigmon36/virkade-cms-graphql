package com.virkade.cms.model;

import java.sql.Timestamp;

public class InputLegal {
	
	private long legalDocId;
	private String username;
	private String typeCode;
	private long docId;
	private String pSig;
	private String gSig;
	private boolean minor;
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
	public long getDocId() {
		return docId;
	}
	public void setDocId(long docId) {
		this.docId = docId;
	}
	public String getpSig() {
		return pSig;
	}
	public void setpSig(String pSig) {
		this.pSig = pSig;
	}
	public String getgSig() {
		return gSig;
	}
	public void setgSig(String gSig) {
		this.gSig = gSig;
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
	public boolean isMinor() {
		return minor;
	}
	public void setMinor(boolean minor) {
		this.minor = minor;
	}
	@Override
	public String toString() {
		return "InputLegal [legalDocId=" + legalDocId + ", username=" + username + ", typeCode=" + typeCode + ", docId=" + docId + ", pSig=" + pSig + ", gSig=" + gSig + ", minor=" + minor + ", agree="
				+ agree + ", activeDate=" + activeDate + ", expireDate=" + expireDate + ", enabled=" + enabled + "]";
	}
	
}
