package com.markitserv.ssa.res;

import org.joda.time.DateTime;

public class AuditEntry {
	
	public enum AuditType {CREATED, DELETED, CHANGED };
	
	private long id;
	private User changedBy;
	private DateTime changedAt;
	private User approvedBy;
	private DateTime approvedAt;
	private AuditType type;
	
	// for changes only
	private String field;
	private String oldValue;
	private String newValue;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public User getChangedBy() {
		return changedBy;
	}
	public void setChangedBy(User changedBy) {
		this.changedBy = changedBy;
	}
	public DateTime getChangedAt() {
		return changedAt;
	}
	public void setChangedAt(DateTime changedAt) {
		this.changedAt = changedAt;
	}
	public User getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(User approvedBy) {
		this.approvedBy = approvedBy;
	}
	public DateTime getApprovedAt() {
		return approvedAt;
	}
	public void setApprovedAt(DateTime approvedAt) {
		this.approvedAt = approvedAt;
	}
	public AuditType getType() {
		return type;
	}
	public void setType(AuditType type) {
		this.type = type;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getOldValue() {
		return oldValue;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	public String getNewValue() {
		return newValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
}
