package com.virkade.cms.model;

public class InputComment {
	private String username;
	private String typeCode;
	private String commentContent;
	/**
	 * @return the userName
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the typeCode
	 */
	public String getTypeCode() {
		return typeCode;
	}
	/**
	 * @param typeCode the typeCode to set
	 */
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
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
}
