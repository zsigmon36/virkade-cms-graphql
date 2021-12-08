package com.virkade.cms.model;

public class InputDocument {
	
	private long docId;
	private String typeCode;
	private String title;
	private String content;
	private float version;
	private boolean enabled;
	
	public long getDocId() {
		return docId;
	}

	public void setDocId(long docId) {
		this.docId = docId;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public float getVersion() {
		return version;
	}

	public void setVersion(float version) {
		this.version = version;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "InputDocument [docId=" + docId + ", typeCode=" + typeCode + ", title=" + title + ", content=" + content + ", version=" + version + ", enabled=" + enabled + "]";
	}
}
