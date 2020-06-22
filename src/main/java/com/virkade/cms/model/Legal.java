package com.virkade.cms.model;

import java.sql.Timestamp;
import java.util.SortedSet;

import com.virkade.cms.hibernate.dao.TypeDAO;
import com.virkade.cms.hibernate.dao.UserDAO;

public class Legal extends VirkadeModel{
	
	private long legalDocId;
	private User user;
	private Type type;
	
	private boolean agree;
	private Timestamp activeDate;
	private Timestamp expireDate;
	private boolean enabled;
	private Audit audit;
	
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
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUser(User user) {
		this.user = user;
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
	 * @param audit the audit to set
	 */
	public void setAudit(Audit audit) {
		this.audit = audit;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(Type type) {
		this.type = type;
	}
	/**
	 * @param type the type to set
	 */
	public Type getType() {
		return type;
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Legal [legalDocId=" + legalDocId + ", user=" + user + ", type=" + type + ", agree=" + agree + ", activeDate=" + activeDate 
				+ ", expireDate=" + expireDate + ", enabled=" + enabled + ", audit=" + audit + "]";
	}
	
	/**
	 * @return the attribute sorted list
	 */
	public SortedSet<String> getAttributeList() {
		SortedSet<String> attributes = super.getAttributeList();
		attributes.add("LegalDocId");
		attributes.add("User");
		attributes.add("Type");
		attributes.add("Agree");
		attributes.add("ActiveDate");
		attributes.add("ExpireDate");
		attributes.add("Enabled");
		return attributes;
	}

	static Legal convertInput(InputLegal inputLegal) {
		Legal legal = new Legal();
		legal.setUser(UserDAO.fetchByUsername(inputLegal.getUsername()));
		legal.setActiveDate(inputLegal.getActiveDate());
		legal.setExpireDate(inputLegal.getExpireDate());
		legal.setType(TypeDAO.getByCode(inputLegal.getTypeCode()));
		legal.setAgree(inputLegal.isAgree());
		legal.setEnabled(inputLegal.isEnabled());
		return legal;
	}
	
}
