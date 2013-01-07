package com.markitserv.mwws.action;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Encapsulates filters for an action. Delegates some of the Map functions
 * 
 * @author roy.truelove
 * 
 */
public class ActionFilters {

	private Map<String, List<String>> filters;

	public ActionFilters(Map<String, List<String>> filtersMap) {
		this.filters = filtersMap;
	}

	public int size() {
		return filters.size();
	}

	public void addFilter(String key, List<String> values) {
		filters.put(key, values);
	}

	public Map<String, List<String>> getAllFilters() {
		return filters;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((filters == null) ? 0 : filters.hashCode());
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
		ActionFilters other = (ActionFilters) obj;
		if (filters == null) {
			if (other.filters != null)
				return false;
		} else if (!filters.equals(other.filters))
			return false;
		return true;
	}

	@Override
	public String toString() {
		final int maxLen = 10;
		StringBuilder builder = new StringBuilder();
		builder.append("ActionFilters [filters=")
				.append(filters != null ? toString(filters.entrySet(), maxLen)
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
