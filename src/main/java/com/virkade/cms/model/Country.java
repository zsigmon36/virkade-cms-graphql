package com.virkade.cms.model;

import java.util.Date;

public class Country {
	private long countryId;
	private long regionId;
	
	private String name;
	private String description;
	private char a2;
	private char a3;
	private Date createdAt;
	private Date updatedAt;
	private String createdBy;
	private String updatedBy;
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
	 * @return the regionId
	 */
	public long getRegionId() {
		return regionId;
	}
	/**
	 * @param regionId the regionId to set
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
	public char getA2() {
		return a2;
	}
	/**
	 * @param a2 the a2 to set
	 */
	public void setA2(char a2) {
		this.a2 = a2;
	}
	/**
	 * @return the a3
	 */
	public char getA3() {
		return a3;
	}
	/**
	 * @param a3 the a3 to set
	 */
	public void setA3(char a3) {
		this.a3 = a3;
	}
	/**
	 * @return the createdAt
	 */
	public Date getCreatedAt() {
		return createdAt;
	}
	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	/**
	 * @return the updatedAt
	 */
	public Date getUpdatedAt() {
		return updatedAt;
	}
	/**
	 * @param updatedAt the updatedAt to set
	 */
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return the updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}
	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	
}
