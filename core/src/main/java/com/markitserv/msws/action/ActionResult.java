package com.markitserv.msws.action;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import com.markitserv.msws.AbstractWebserviceResult;

public class ActionResult extends AbstractWebserviceResult {

	private List<? extends Object> list;
	private Object item;

	/*
	 * public ActionResult () { super(); }
	 */

	public ActionResult(Object item) {
		super();
		this.item = item;
	}

	public ActionResult(List<? extends Object> list) {
		super();
		this.list = list;
	}

	@JsonInclude(Include.NON_NULL)
	@JsonProperty(value = "list")
	public List<? extends Object> getList() {
		return list;
	}

	public void setList(List<? extends Object> collection) {
		this.list = collection;
	}

	@JsonInclude(Include.NON_NULL)
	public Object getItem() {
		return item;
	}

	public void setItem(Object item) {
		this.item = item;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(list, item);
	}

	@Override
	public boolean equals(Object obj) {
		return Objects.equal(this, obj);
	}

}
