package com.markitserv.msws.action;

import java.util.Collection;

import static com.markitserv.msws.internal.MswsAssert.mswsAssert;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.markitserv.msws.exceptions.ProgrammaticException;
import com.markitserv.msws.internal.Constants;
import com.markitserv.msws.internal.MswsAssert;

public class ActionParameters {

	private Map<String, Object> params;

	public ActionParameters(Map<String, Object> paramsMap) {
		this.params = paramsMap;
	}

	public Object getParameter(String key) {
		return params.get(key);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getParameter(String k, Class<T> type) {
		return (T)this.getParameter(k);
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
		final int prime = 31;
		int result = 1;
		result = prime * result + ((params == null) ? 0 : params.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActionParameters other = (ActionParameters) obj;
		if (params == null) {
			if (other.params != null)
				return false;
		} else if (!params.equals(other.params))
			return false;
		return true;
	}

	@Override
	public String toString() {
		final int maxLen = 10;
		StringBuilder builder = new StringBuilder();
		builder.append("ActionParameters [params=")
				.append(params != null ? toString(params.entrySet(), maxLen)
						: null).append("]");
		return builder.toString();
	}

	private String toString(Collection<?> collection, int maxLen) {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		int i = 0;
		for (Iterator<?> iterator = collection.iterator(); iterator.hasNext()
				&& i < maxLen; i++) {
			if (i > 0)
				builder.append(", ");
			builder.append(iterator.next());
		}
		builder.append("]");
		return builder.toString();
	}

}