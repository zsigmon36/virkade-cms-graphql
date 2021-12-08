package com.virkade.cms.model;

import java.util.SortedSet;

import com.virkade.cms.hibernate.dao.TypeDAO;

public class Document extends VirkadeModel{
	
	private long docId;
	private Type type;
	private String title;
	private String content;
	private float version;
	private boolean enabled;
	private Audit audit;
	
	public long getDocId() {
		return docId;
	}
	public void setDocId(long docId) {
		this.docId = docId;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
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
	
	@Override
	public String toString() {
		return "Document [docId=" + docId + ", type=" + type + ", title=" + title + ", content=" + content + ", version=" + version + ", enabled=" + enabled + ", audit=" + audit + "]";
	}
	
	/**
	 * @return the attribute sorted list
	 */
	public SortedSet<String> getAttributeList() {
		SortedSet<String> attributes = super.getAttributeList();
		attributes.add("DocId");
		attributes.add("Type");
		attributes.add("Title");
		attributes.add("Content");
		attributes.add("Version");
		attributes.add("Enabled");
		return attributes;
	}

	static Document convertInput(InputDocument inputDoc) {
		Document doc = new Document();
		doc.setType(TypeDAO.getByCode(inputDoc.getTypeCode()));
		doc.setTitle(inputDoc.getTitle());
		doc.setContent(inputDoc.getContent());
		doc.setVersion(inputDoc.getVersion());
		doc.setEnabled(inputDoc.isEnabled());
		return doc;
	}
	
}
