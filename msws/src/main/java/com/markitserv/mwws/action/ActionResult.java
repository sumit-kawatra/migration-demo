package com.markitserv.mwws.action;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.markitserv.mwws.Type;

public class ActionResult {
	
	private ActionResponseMetadata metaData = new ActionResponseMetadata();
	private List<? extends Type> list;
	private Type item;
	
	public ActionResult () {
		super();
	}
	
	public ActionResult(Type item) {
		super();
		this.item = item;
	}

	public ActionResult(List<? extends Type> list) {
		super();
		this.list = list;
	}

	@JsonInclude(Include.NON_NULL)
	@JsonProperty(value="list")
	public List<? extends Type> getList() {
		return list;
	}

	public void setList(List<? extends Type> collection) {
		this.list = collection;
	}

	@JsonInclude(Include.NON_NULL)
	public Type getItem() {
		return item;
	}

	public void setItem(Type item) {
		this.item = item;
	}

	@JsonInclude(Include.NON_NULL)
	@JsonProperty(value="responseMetadata")
	public ActionResponseMetadata getMetadata() {
		return metaData;
	}
}
