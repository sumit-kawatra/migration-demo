package com.markitserv.msws.action.resp;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import com.markitserv.msws.internal.action.AbstractWebserviceResult;

public class ActionResult extends AbstractWebserviceResult {

	private List<? extends Object> items;
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
		this.items = list;
	}
	
	/**
	 * Convenience method to return a success
	 * @return
	 */
	@JsonIgnore
	public static ActionResult success() {
		return new ActionResult(new SuccessFailure(true));
	}

	@JsonInclude(Include.NON_NULL)
	@JsonProperty(value = "items")
	public List<? extends Object> getItems() {
		return items;
	}

	public void setItems(List<? extends Object> collection) {
		this.items = collection;
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
		return Objects.hashCode(items, item);
	}

	@Override
	public boolean equals(Object obj) {
		return Objects.equal(this, obj);
	}
}
