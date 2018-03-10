package com.virkade.cms.model;

public class Address {
	private long addressId;
	private long countryId;
	private long stateId;
	private long typeId;
	
	private String street;
	private char unit;
	private char apt;
	private String city;
	private int postalCode;
	private Audit audit;
	
	/**
	 * @return the addressId
	 */
	public long getAddressId() {
		return addressId;
	}
	/**
	 * @param addressId the addressId to set
	 */
	public void setAddressId(long addressId) {
		this.addressId = addressId;
	}
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
	 * @return the stateId
	 */
	public long getStateId() {
		return stateId;
	}
	/**
	 * @param stateId the stateId to set
	 */
	public void setStateId(long stateId) {
		this.stateId = stateId;
	}
	/**
	 * @return the typeId
	 */
	public long getTypeId() {
		return typeId;
	}
	/**
	 * @param typeId the typeId to set
	 */
	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}
	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}
	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}
	/**
	 * @return the unit
	 */
	public char getUnit() {
		return unit;
	}
	/**
	 * @param unit the unit to set
	 */
	public void setUnit(char unit) {
		this.unit = unit;
	}
	/**
	 * @return the apt
	 */
	public char getApt() {
		return apt;
	}
	/**
	 * @param apt the apt to set
	 */
	public void setApt(char apt) {
		this.apt = apt;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the postalCode
	 */
	public int getPostalCode() {
		return postalCode;
	}
	/**
	 * @param postalCode the postalCode to set
	 */
	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
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
	
	
}
