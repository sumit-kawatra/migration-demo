package com.markitserv.msws.action;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import com.google.common.base.Objects;

public class ActionParameters {

	private Map<String, Object> params;

	public ActionParameters(Map<String, Object> paramsMap) {
		this.params = paramsMap;
	}

	/**
	 * Use getParameter(String key, Class type) instead
	 * 
	 * @param key
	 * @return
	 */
	@Deprecated
	public Object getParameter(String key) {
		return params.get(key);
	}

	@SuppressWarnings("unchecked")
	public <T> T getParameter(String k, Class<T> type) {
		return (T) this.params.get(k);
	}

	@Deprecated
	public int getParameterAsInt(String key) {
		return this.getParameter(key, Integer.class);
	}

	@Deprecated
	public boolean getParameterAsBoolean(String key) {
		return this.getParameter(key, Boolean.class);
	}

	public void addParameter(String key, Object value) {
		params.put(key, value);
	}

	public Map<String, Object> getAllParameters() {
		return params;
	}

	public boolean isParameterSet(String key) {
		return params.containsKey(key);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(params);
	}

	@Override
	public boolean equals(Object obj) {
		return Objects.equal(this, obj);
	}

	@Override
	public String toString() {
		final int maxLen = 10;
		StringBuilder builder = new StringBuilder();
		builder.append("ActionParameters [params=")
				.append(params != null ? toString(params.entrySet(), maxLen) : null)
				.append("]");
		return builder.toString();
	}

	private String toString(Collection<?> collection, int maxLen) {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		int i = 0;
		for (Iterator<?> iterator = collection.iterator(); iterator.hasNext() && i < maxLen; i++) {
			if (i > 0)
				builder.append(", ");
			builder.append(iterator.next());
		}
		builder.append("]");
		return builder.toString();
	}

}