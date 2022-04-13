package com.virkade.cms.model;

import java.sql.Timestamp;

public class News {
	
	private long newsId;
	private Type type;

	private String name;
	private String description;
	private String content;
	private Timestamp activeDate;
	private Timestamp expireDate;
	private boolean enabled;
	private Audit audit;
	
	/**
	 * @return the newsId
	 */
	public long getNewsId() {
		return newsId;
	}
	/**
	 * @param newsId the newsId to set
	 */
	public void setNewsId(long newsId) {
		this.newsId = newsId;
	}
	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(Type type) {
		this.type = type;
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
		return audit;
	}
	/**
	 * @param audit the audit to set
	 */
	public void setAudit(Audit audit) {
		this.audit = audit;
	}
	
}
