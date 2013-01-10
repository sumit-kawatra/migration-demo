package com.markitserv.mwws.filters;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import com.markitserv.mwws.Type;

/**
 * Abstract filter class. Subclasses should: 1) implement shouldBeFilteredOut,
 * and 2) provide static factory classes and make the constructors private.
 * 
 * @author roy.truelove
 * 
 * @param <T>
 */
public abstract class AbstractFilter<T extends Type> {

	public List<T> filter(List<T> toFilter) {

		Stack<T> newList = new Stack<T>();

		for (Iterator<T> i = toFilter.iterator(); i.hasNext();) {
			T item = (T) i.next();
			if (!shouldBeFilteredOut(item)) {
				newList.push(item);
			}
		}
		return newList;
	}

	protected abstract boolean shouldBeFilteredOut(T item);
}
