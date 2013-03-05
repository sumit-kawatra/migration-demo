package com.markitserv.msws.action;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.markitserv.msws.AbstractWebserviceResult;
import com.markitserv.msws.ResponseMetadata;
import com.markitserv.msws.Type;

public class ActionResult extends AbstractWebserviceResult {
	
	private List<? extends Type> list;
	private Type item;
	
	/*
	public ActionResult () {
		super();
	}
	*/
	
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
}
