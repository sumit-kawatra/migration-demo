package com.markitserv.msws.action;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.google.common.base.Objects;
import com.markitserv.msws.util.MswsAssert;

/**
 * Encapsulates filters for an action. Delegates some of the Map functions
 * 
 * @author roy.truelove
 * 
 */
public class ActionFilters {

	private Map<String, List<Object>> filters;

	public ActionFilters(Map<String, List<Object>> filtersMap) {
		this.filters = filtersMap;
	}

	public int size() {
		return filters.size();
	}

	public void addFilter(String key, List<Object> values) {
		filters.put(key, values);
	}

	public Map<String, List<Object>> getAllFilters() {
		return filters;
	}

	public boolean isFilterSet(String key) {
		return filters.containsKey(key);
	}
	
	@Deprecated
	public List<String> getFilter(String key) {
		
		return this.getFilter(key, String.class);
		
	}

	public <T> List<T> getFilter(String key, Class<T> type) {
		
		List<Object> filt = filters.get(key);
		Stack<T> convertedFilters = new Stack<T>();
		
		for (Object filter : filt) {
			convertedFilters.push((T)filter);
		}
		
		return convertedFilters;
	}
	
	@Deprecated
	public String getSingleFilter(String key) {
		return this.getSingleFilter(key, String.class);
	}

	/**
	 * Assumes that there's only one value for this filter
	 * 
	 * @param key
	 * @return
	 */
	public <T> T getSingleFilter(String key, Class<T> type) {

		List<Object> filtersForKey = filters.get(key);
		MswsAssert.mswsAssert(filtersForKey.size() == 1,
				"Expected filter '%s' to have only one value.", key);

		return (T)filtersForKey.get(0);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(filters);
	}

	@Override
	public boolean equals(Object obj) {
		return Objects.equal(this, obj);
	}

	@Override
	public String toString() {
		final int maxLen = 10;
		StringBuilder builder = new StringBuilder();
		builder.append("ActionFilters [filters=")
				.append(filters != null ? toString(filters.entrySet(), maxLen) : null)
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
