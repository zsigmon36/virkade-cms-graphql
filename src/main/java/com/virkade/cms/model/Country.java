package com.virkade.cms.model;

public class Country {
	private long countryId;
	private Region region;
	
	private String name;
	private String description;
	private String a2;
	private String a3;
	private Audit audit;
	/**
	 * @return the countryId
	 */
	public long getCountryId() {
		return countryId;
	}
	/**
	 * @param countryId the countryId to set
	 */
	public void setCountryId(long countryId) {
		this.countryId = countryId;
	}
	/**
	 * @return the region
	 */
	public Region getRegion() {
		return region;
	}
	/**
	 * @param region the region to set
	 */
	public void setRegion(Region region) {
		this.region = region;
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
	 * @return the a2
	 */
	public String getA2() {
		return a2;
	}
	/**
	 * @param a2 the a2 to set
	 */
	public void setA2(String a2) {
		this.a2 = a2;
	}
	/**
	 * @return the a3
	 */
	public String getA3() {
		return a3;
	}
	/**
	 * @param a3 the a3 to set
	 */
	public void setA3(String a3) {
		this.a3 = a3;
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
		return "Country [countryId=" + countryId + ", region=" + region + ", name=" + name + ", description=" + description + ", a2=" + a2 + ", a3=" + a3 + ", audit=" + audit + "]";
	}
	
}
