package com.virkade.cms.model;

import java.util.Date;

public class Legal {
	
	private long legalDocId;
	private User user;
	private Type type;
	
	private String name;
	private String description;
	private String content;
	private Date activeDate;
	private Date expireDate;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return the activeDate
	 */
	public Date getActiveDate() {
		return activeDate;
	}
	/**
	 * @param activeDate the activeDate to set
	 */
	public void setActiveDate(Date activeDate) {
		this.activeDate = activeDate;
	}
	/**
	 * @return the expireDate
	 */
	public Date getExpireDate() {
		return expireDate;
	}
	/**
	 * @param expireDate the expireDate to set
	 */
	public void setExpireDate(Date expireDate) {
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Legal [legalDocId=" + legalDocId + ", user=" + user.toString() + ", type=" + type.toString() + ", name=" + name
				+ ", description=" + description + ", content=" + content + ", activeDate=" + activeDate
				+ ", expireDate=" + expireDate + ", enabled=" + enabled + ", audit=" + audit.toString() + "]";
	}
	
	
}
