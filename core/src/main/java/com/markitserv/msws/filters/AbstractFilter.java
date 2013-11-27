package com.markitserv.msws.filters;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * Abstract filter class. Subclasses should: 1) implement shouldBeFilteredOut,
 * and 2) provide static factory classes and make the constructors private.
 * 
 * @author roy.truelove
 * 
 * @param <T>
 */
public abstract class AbstractFilter<T> {

	public List<T> filter(List<T> toFilter) {

		Stack<T> newList = new Stack<T>();

		for (Iterator<T> i = toFilter.iterator(); i.hasNext();) {
			T item = (T) i.next();
			if (!shouldBeFilteredOut(item)) {
				newList.push(item);
			}
		}

		return this.postProcessor(newList);
	}

	protected boolean shouldBeFilteredOut(T item) {
		return false;
	}

	/**
	 * Override if the subclass wants to do something after the filter
	 * 
	 * @param toFilter
	 * @return
	 */
	protected List<T> postProcessor(List<T> toFilter) {
		return toFilter;
	}
}
