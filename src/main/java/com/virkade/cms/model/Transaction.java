package com.virkade.cms.model;

import java.util.ArrayList;
import java.util.List;

import com.virkade.cms.hibernate.dao.PlaySessionDAO;

public class Transaction extends VirkadeModel {

	private long transactionId;
	private List<PlaySession> sessions;
	private List<Long> sessionIds;
	private String serviceName;
	private String description;
	private String refId;
	private String approvalCode;
	private double payment;
	private Audit audit;

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

	public String getApprovalCode() {
		return approvalCode;
	}

	public void setApprovalCode(String approvalCode) {
		this.approvalCode = approvalCode;
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
	public List<PlaySession> getSessions() {
		return sessions;
	}

	/**
	 * @param session the session to set
	 */
	public void setSessions(List<PlaySession> sessions) {
		this.sessions = sessions;
	}

	public List<Long> getSessionIds() {
		List<Long> ids = new ArrayList<>();
		for (PlaySession session : sessions) {
			ids.add(session.getSessionId());
		}
		return ids;
	}

	public void setSessionIds(List<Long> sessionIds) {
		this.sessionIds = sessionIds;
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

	static Transaction convertInput(InputTransaction inputTransaction) throws Exception {
		Transaction transaction = new Transaction();
		List<PlaySession> sessions = new ArrayList<>();
		for (long id : inputTransaction.getSessionIds()) {
			PlaySession curSession = PlaySessionDAO.fetchSession(id);
			if (curSession != null) {
				sessions.add(curSession);
			}
		}
		transaction.setSessions(sessions);
		transaction.setDescription(inputTransaction.getDescription());
		transaction.setPayment(inputTransaction.getPayment());
		transaction.setRefId(inputTransaction.getRefId());
		transaction.setApprovalCode(inputTransaction.getApprovalCode());
		transaction.setServiceName(inputTransaction.getServiceName());
		transaction.setTransactionId(inputTransaction.getTransactionId());
		return transaction;
	}
}
