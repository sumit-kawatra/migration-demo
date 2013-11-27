package com.markitserv.msws.filters;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import com.markitserv.msws.action.SortOrder;
import com.markitserv.msws.internal.exceptions.ProgrammaticException;
import com.markitserv.msws.util.MswsAssert;

public class SortingReflectionFilter<T> extends AbstractFilter<T> {

	private List<String> propertyNames;
	private List<SortOrder> sortOrders;

	private SortingReflectionFilter(List<String> propertyName,
			List<SortOrder> order) {

		super();

		MswsAssert.mswsAssert(propertyName != null && order != null,
				"propertyName and order cannot be null");

		// This would be a bug, since this should be checked by the Sorting
		// params builder
		MswsAssert.mswsAssert(propertyName.size() == order.size());

		this.propertyNames = propertyName;
		this.sortOrders = order;

	}

	public static <T> List<T> filter(List<T> toFilter,
			List<String> propertyName, List<SortOrder> order) {

		SortingReflectionFilter<T> f = new SortingReflectionFilter<T>(
				propertyName, order);
		return f.filter(toFilter);
	}

	@Override
	protected List<T> postProcessor(List<T> toFilter) {
		return sort(toFilter);
	}

	private List<T> sort(List<T> toFilter) {

		// for each sorting criteria. Backwards, to give first sort precedence.
		for (int c = propertyNames.size() - 1; c >= 0; c--) {

			String propName = this.propertyNames.get(c);
			SortOrder sortBy = this.sortOrders.get(c);

			toFilter = performSort(toFilter, propName, sortBy);

		}

		return toFilter;
	}

	private List<T> performSort(List<T> toFilter, final String propName,
			final SortOrder sortBy) {
		Collections.sort(toFilter, new Comparator<T>() {

			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public int compare(T o1, T o2) {

				Comparable prop1;
				Comparable prop2;

				try {

					prop1 = (Comparable) PropertyUtils.getNestedProperty(o1,
							propName);
					prop2 = (Comparable) PropertyUtils.getNestedProperty(o2,
							propName);

				} catch (ClassCastException e) {

					throw new ProgrammaticException(
							"Could not sort - value of property '%s' of type"
									+ " '%s' does not implement Comparable.",
							e, propertyNames, o1.getClass().getCanonicalName());

				} catch (Exception e) {
					throw new ProgrammaticException(e);
				}

				int compareRes = prop1.compareTo(prop2);

				if (sortBy.equals(SortOrder.Desc)) {
					compareRes = compareRes * -1;
				}

				return compareRes;

			}
		});

		return toFilter;
	}
}
