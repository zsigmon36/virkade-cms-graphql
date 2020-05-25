package com.virkade.cms.model;

import java.util.SortedSet;

import com.virkade.cms.hibernate.dao.TypeDAO;

public class Comment extends VirkadeModel{
	private long commentId;
	private User user;
	private Type type;
	private String commentContent;
	private Audit audit;
	
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	/**
	 * @return the userId
	 */
	public long getUserId() {
		return user.getUserId();
	}

	/**
	 * @return the userName
	 */
	public String getUsername() {
		return user.getUsername();
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
	 * @return the commentId
	 */
	public long getCommentId() {
		return commentId;
	}
	/**
	 * @param commentId the commentId to set
	 */
	public void setCommentId(long commentId) {
		this.commentId = commentId;
	}
	/**
	 * @return the commentContent
	 */
	public String getCommentContent() {
		return commentContent;
	}
	/**
	 * @param commentContent the commentContent to set
	 */
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
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
	public SortedSet<String> getAttributeList() {
		SortedSet<String> attributes = super.getAttributeList();
		attributes.add("PhoneId");
		attributes.add("User");
		attributes.add("Type");
		attributes.add("Number");
		attributes.add("CountryCode");
		return attributes;
	}

	static Comment convertInput(InputComment inputComment) {
		Comment comment = new Comment();
		comment.setType(TypeDAO.fetchByCode(inputComment.getTypeCode()));
		comment.setCommentContent(inputComment.getCommentContent());
		return comment;
	}
}
