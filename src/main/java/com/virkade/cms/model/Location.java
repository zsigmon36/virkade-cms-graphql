package com.virkade.cms.model;

import com.virkade.cms.hibernate.dao.AddressDAO;
import com.virkade.cms.hibernate.dao.ConstantsDAO;
import com.virkade.cms.hibernate.dao.StateDAO;
import com.virkade.cms.hibernate.dao.StatusDAO;
import com.virkade.cms.hibernate.dao.TypeDAO;

public class Location extends VirkadeModel {

	private long locationId;
	private Address address;

	private String name;
	private String description;
	private String phoneNum;
	private float taxRate;
	private String manager;
	private boolean enabled;
	private Audit audit;

	/**
	 * @return the locationId
	 */
	public long getLocationId() {
		return locationId;
	}

	/**
	 * @param locationId the locationId to set
	 */
	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}

	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
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
	 * @return the phoneNum
	 */
	public String getPhoneNum() {
		return phoneNum;
	}

	/**
	 * @param phoneNum the phoneNum to set
	 */
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public float getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(float taxRate) {
		this.taxRate = taxRate;
	}

	/**
	 * @return the manager
	 */
	public String getManager() {
		return manager;
	}

	/**
	 * @param manager the manager to set
	 */
	public void setManager(String manager) {
		this.manager = manager;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Location [locationId=" + locationId + ", address=" + address + ", name=" + name + ", description=" + description + ", phoneNum=" + phoneNum + ", manager=" + manager + ", enabled="
				+ enabled + ", audit=" + audit + "]";
	}

	static Location convertInput(InputLocation inputLocation) {
		Location location = new Location();
		Address address = new Address();
		address.setApt(inputLocation.getApt());
		address.setUnit(inputLocation.getUnit());
		address.setCity(inputLocation.getCity());
		address.setPostalCode(inputLocation.getPostalCode());
		address.setState(StateDAO.fetchById(inputLocation.getStateId()));
		address.setStreet(inputLocation.getStreet());
		address.setType(TypeDAO.fetchByCode(ConstantsDAO.PHYSICAL_ADDRESS));

		Address existingAddress = AddressDAO.fetch(address);
		if (existingAddress != null) {
			address = existingAddress;
		}

		location.setAddress(address);
		location.setDescription(inputLocation.getDescription());
		location.setEnabled(true);
		location.setLocationId(inputLocation.getLocationId());
		location.setManager(inputLocation.getManager());
		location.setName(inputLocation.getName());
		location.setPhoneNum(inputLocation.getPhoneNum());
		location.setTaxRate(inputLocation.getTaxRate());
		return location;
	}

}
