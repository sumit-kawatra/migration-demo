package com.markitserv.msws.filters;

import java.util.ArrayList;
import java.util.List;

import com.markitserv.msws.Type;

/**
 * Provides pagination if the pagination is not done in the database. Assumes
 * list is already sorted (if it's expected to be sorted)
 * 
 * @author roy.truelove
 * 
 * @param <T>
 */
public class PaginationFilter<T extends Type> extends AbstractFilter<T> {

	private int pageNumber;
	private int pageSize;

	public static <T extends Type> List<T> filter(List<T> toFilter,
			int pageNumber, int pageSize) {

		PaginationFilter<T> f = new PaginationFilter<T>(pageNumber, pageSize);
		return f.filter(toFilter);
	}

	private PaginationFilter(int pageNumber, int pageSize) {
		super();
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}

	@Override
	public List<T> filter(List<T> toFilter) {

		int startingIndex = ((pageNumber - 1) * pageSize);
		int endingIndex = startingIndex + pageSize;
		
		// returns an empty list if the pageNumber is too high
		if (startingIndex > toFilter.size() - 1) {
			return new ArrayList<T>();
		}

		if (endingIndex > toFilter.size()) {
			endingIndex = toFilter.size();
		}
		
		List<T> subList = toFilter.subList(startingIndex, endingIndex);
		
		return subList;
	}
}
