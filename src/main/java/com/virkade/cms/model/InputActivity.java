package com.virkade.cms.model;

public class InputActivity {
	private long activityId;
	private String name;
	private String description;
	private String webSite;
	private String supportContact;
	private double costpm;
	private int setupMin;
	private String creator;
	private boolean enabled;

	/**
	 * @return the activityId
	 */
	public long getActivityId() {
		return activityId;
	}

	/**
	 * @param activityId the activityId to set
	 */
	public void setActivityId(long activityId) {
		this.activityId = activityId;
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
	 * @return the webSite
	 */
	public String getWebSite() {
		return webSite;
	}

	/**
	 * @param webSite the webSite to set
	 */
	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	/**
	 * @return the supportContact
	 */
	public String getSupportContact() {
		return supportContact;
	}

	/**
	 * @param supportContact the supportContact to set
	 */
	public void setSupportContact(String supportContact) {
		this.supportContact = supportContact;
	}

	/**
	 * @return the cost
	 */
	public double getCostpm() {
		return costpm;
	}

	/**
	 * @param cost the cost to set
	 */
	public void setCostpm(double costpm) {
		this.costpm = costpm;
	}

	public int getSetupMin() {
		return setupMin;
	}

	public void setSetupMin(int setupMin) {
		this.setupMin = setupMin;
	}

	/**
	 * @return the creator
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * @param creator the creator to set
	 */
	public void setCreator(String creator) {
		this.creator = creator;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Activity [activityId=" + activityId + ", name=" + name + ", description=" + description + ", webSite=" + webSite + ", supportContact=" + supportContact + ", costpm=" + costpm
				+ ", creator=" + creator + ", enabled=" + enabled + "]";
	}

}
