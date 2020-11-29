package com.virkade.cms.model;

import java.util.List;

public class InputTransaction {
	
	private long transactionId;
	private List<Long> sessionIds;
	private String serviceName;
	private String description;
	private String refId;
	private double payment;
	
	/**
	 * @return the transactionId
	 */
	public long getTransactionId() {
		return transactionId;
	}
	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}
	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}
	/**
	 * @param serviceName the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
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
	 * @return the refId
	 */
	public String getRefId() {
		return refId;
	}
	/**
	 * @param refId the refId to set
	 */
	public void setRefId(String refId) {
		this.refId = refId;
	}
	/**
	 * @return the payment
	 */
	public double getPayment() {
		return payment;
	}
	/**
	 * @param payment the payment to set
	 */
	public void setPayment(double payment) {
		this.payment = payment;
	}
	/**
	 * @return the session
	 */
	public List<Long> getSessionIds() {
		return sessionIds;
	}
	/**
	 * @param session the session to set
	 */
	public void setSessions(List<Long> sessionIds) {
		this.sessionIds = sessionIds;
	}
	
}
