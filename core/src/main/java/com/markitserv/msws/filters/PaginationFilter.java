package com.markitserv.msws.filters;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides pagination if the pagination is not done in the database. Assumes
 * list is already sorted (if it's expected to be sorted)
 * 
 * @author roy.truelove
 * 
 * @param <T>
 */
public class PaginationFilter<T> extends AbstractFilter<T> {

	private int pageSize;
	private int pageStartIndex;

	/**
	 * @param Collection
	 *           to filter
	 * @param Starting
	 *           index. First element is index # 1
	 * @param Size
	 *           of the page to return
	 * @return
	 */
	public static <T> List<T> filter(List<T> toFilter, int pageStartIndex,
			int pageSize) {

		PaginationFilter<T> f = new PaginationFilter<T>(pageStartIndex, pageSize);
		return f.filter(toFilter);
	}

	private PaginationFilter(int pageStartIndex, int pageSize) {
		super();
		this.pageStartIndex = pageStartIndex;
		this.pageSize = pageSize;
	}

	@Override
	public List<T> filter(List<T> toFilter) {

		int startingIndex = pageStartIndex - 1;
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