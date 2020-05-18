package com.virkade.cms.model;

import java.util.SortedSet;

import org.apache.log4j.Logger;

import com.virkade.cms.hibernate.dao.StateDAO;
import com.virkade.cms.hibernate.dao.TypeDAO;

public class Address extends VirkadeModel {
	private static final Logger LOG = Logger.getLogger(Address.class);
	
	private long addressId;
	private State state;
	private Type type;
	private String street;
	private String unit;
	private String apt;
	private String city;
	private String postalCode;
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
	 * @return the state
	 */
	public State getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(State state) {
		this.state = state;
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
	public String getUnit() {
		return unit;
	}
	/**
	 * @param unit the unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}
	/**
	 * @return the apt
	 */
	public String getApt() {
		return apt;
	}
	/**
	 * @param apt the apt to set
	 */
	public void setApt(String apt) {
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
	public String getPostalCode() {
		return postalCode;
	}
	/**
	 * @param postalCode the postalCode to set
	 */
	public void setPostalCode(String postalCode) {
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
	
	/**
	 * @return the attribute sorted list
	 */
	public SortedSet<String> getUserAttributeList() {
		SortedSet<String> attributes = super.getAttributeList();
		attributes.add("AddressId");
		attributes.add("State");
		attributes.add("Type");
		attributes.add("Street");
		attributes.add("Unit");
		attributes.add("Apt");
		attributes.add("City");
		attributes.add("PostalCode");
		
		return attributes;
	}
	
	static Address convertInput(InputAddress inputAddress) throws Exception {
		Address address = new Address();
		
		if (inputAddress.getStateCode() != null) {
			address.setState(StateDAO.getByCode(inputAddress.getStateCode()));
		} else if (inputAddress.getStateId() > 0) {
			address.setState(StateDAO.getById(inputAddress.getStateId()));
		}
		
		address.setAddressId(inputAddress.getAddressId());
		address.setApt(inputAddress.getApt());
		address.setCity(inputAddress.getCity());
		address.setPostalCode(inputAddress.getPostalCode());
		address.setStreet(inputAddress.getStreet());
		address.setType(TypeDAO.getByCode(inputAddress.getTypeCode()));
		address.setUnit(inputAddress.getUnit());
		
		return address;
	}
}
