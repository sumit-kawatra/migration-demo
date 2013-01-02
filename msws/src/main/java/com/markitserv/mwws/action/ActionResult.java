package com.markitserv.mwws.action;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.markitserv.mwws.Type;

public class ActionResult {
	
	private ActionResponseMetadata metaData = new ActionResponseMetadata();
	private Set<? extends Type> collection;
	private Type item;

	@JsonProperty(value="set")
	public Set<? extends Type> getCollection() {
		return collection;
	}

	public void setCollection(Set<? extends Type> collection) {
		this.collection = collection;
	}

	public Type getItem() {
		return item;
	}

	public void setItem(Type item) {
		this.item = item;
	}

	@JsonProperty(value="responseMetadata")
	public ActionResponseMetadata getMetadata() {
		return metaData;
	}

	public void setMetaData(ActionResponseMetadata metaData) {
		this.metaData = metaData;
	}
}
