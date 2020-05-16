package com.virkade.cms.model;

public class InputRegion {

	private long regionId;
	private String name;
	private String description;
	private String regionCode;

	/**
	 * @return the regionId
	 */
	public long getRegionId() {
		return regionId;
	}

	/**
	 * @param regionId
	 *            the regionId to set
	 */
	public void setRegionId(long regionId) {
		this.regionId = regionId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
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
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the regionCode
	 */
	public String getRegionCode() {
		return regionCode;
	}

	/**
	 * @param regionCode
	 *            the regionCode to set
	 */
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

}
