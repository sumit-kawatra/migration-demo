package com.markitserv.mwws;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.springframework.stereotype.Service;

@Service
public class HttpParamsProcessor {
	
	public Map<String, Object> processParameters(
			Map<String, String[]> immutableParams) {

		// Need a mutable copy
		HashMap<String, String[]> params = new HashMap<String, String[]>(
				immutableParams);
		Map<String, List<String>> filters = processFilterParams(params);

		Map processedParams = new HashMap();
		for (String key : params.keySet()) {
			// TODO process other 'list' type params
			// TODO Ensure there's only one value for each param
			String[] valueArr = params.get(key);
			if (valueArr.length != 1) {
				throw MultipleParameterValuesException.standardException(key);
			}
			String value = valueArr[0];
			processedParams.put(key, value);
		}

		// add the processed filters back as a single param
		processedParams.put(CommonParamKeys.Filter.toString(), filters);

		return processedParams;
	}

	/**
	 * Creates a hashmap where the key is the name of the filter, and the value
	 * is a List of values. Side effect is that it removes all filters from the
	 * 'params' argument
	 * 
	 * @param params
	 * @return
	 */
	private Map<String, List<String>> processFilterParams(
			HashMap<String, String[]> params) {

		Map<String, List<String>> filters = new HashMap<String, List<String>>();

		int nameCounter = 0;
		boolean stillHaveFilters = true;

		// is there a more efficient way?
		do {
			nameCounter++;

			String filterNameKey = CommonParamKeys.Filter.toString() + "."
					+ nameCounter + ".Name";
			String[] filterNameArr = params.remove(filterNameKey);

			// if there is a filter
			if (filterNameArr != null) {

				if (filterNameArr.length != 1) {
					MultipleParameterValuesException
							.standardException(filterNameKey);
				}

				String filterName = filterNameArr[0];

				List<String> allValues = processFilterValues(params,
						nameCounter);

				if (allValues.size() == 0) {
					throw new MalformedFiltersException(String.format(
							"Filter number '%d' has no values", nameCounter));
				}

				filters.put(filterName, allValues);

			} else {
				stillHaveFilters = false;
			}

		} while (stillHaveFilters);

		return filters;
	}

	private List<String> processFilterValues(HashMap<String, String[]> params,
			int nameCounter) {
		// iterate through the values of the filter and push them to a stack

		int valueCounter = 0;
		boolean stillHaveFilterValues = true;
		Stack<String> filterValues = new Stack<String>();

		do {
			valueCounter++;

			String filterValueKey = "Filter." + nameCounter + ".Value."
					+ valueCounter;
			String[] filterValueArr = params.remove(filterValueKey);

			if (filterValueArr != null) {

				if (filterValueArr.length != 1) {
					MultipleParameterValuesException
							.standardException(filterValueKey);
				}

				String filterValue = filterValueArr[0];

				filterValues.push(filterValue);
			} else {
				stillHaveFilterValues = false;
			}

		} while (stillHaveFilterValues);

		return filterValues;
	}
}
