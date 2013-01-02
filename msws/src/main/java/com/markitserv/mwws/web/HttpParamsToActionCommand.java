package com.markitserv.mwws.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.markitserv.mwws.action.ActionCommand;
import com.markitserv.mwws.action.ActionFilters;
import com.markitserv.mwws.action.ActionParameters;
import com.markitserv.mwws.action.CommonParamKeys;
import com.markitserv.mwws.exceptions.ActionParamMissingException;
import com.markitserv.mwws.exceptions.MalformedFiltersException;
import com.markitserv.mwws.exceptions.MultipleParameterValuesException;

/**
 * Builds an ActionCommand from HTTP paramas.
 * 
 * @author roy.truelove
 * 
 */
@Service
public class HttpParamsToActionCommand {

	public ActionCommand buildActionCommandFromHttpParams(
			Map<String, String[]> httpParams) {

		// Need a mutable copy
		HashMap<String, String[]> params = new HashMap<String, String[]>(
				httpParams);

		ActionFilters filters = processFilterParams(params);

		String action = null;

		Map<String, Object> processedParamsMap = new HashMap<String, Object>();
		for (String key : params.keySet()) {

			String[] valueArr = params.get(key);

			if (valueArr.length != 1) {
				throw MultipleParameterValuesException.standardException(key);
			}

			String value = valueArr[0];

			if (key.equals(CommonParamKeys.Action.toString())) {
				action = value;
				continue; // so that it's not added to the params
			}

			if (isMultiValue(key)) {
				processedParamsMap = processParamWithMultipleValues(
						processedParamsMap, key, value);
			} else {
				processedParamsMap.put(key, value);
			}
		}

		if (action == null) {
			throw ActionParamMissingException.standardException();
		}

		ActionParameters actionParams = new ActionParameters(processedParamsMap);
		ActionCommand actionCmd = new ActionCommand(action, actionParams,
				filters);

		return actionCmd;
	}

	private boolean isMultiValue(String key) {
		return key.matches("(.*)\\.(\\d+)");
	}

	private Map<String, Object> processParamWithMultipleValues(
			Map<String, Object> params, String mValKey, String value) {

		// Strip off the last numbers
		String key = StringUtils.substringBeforeLast(mValKey, ".");
		int index = Integer.parseInt(StringUtils.substringAfterLast(mValKey,
				"."));

		// populate the list @ zero even though we get '1' from the filter
		index = index - 1;

		if (!params.containsKey(key)) {
			ArrayList<String> x = new ArrayList<String>();
			params.put(key, x);
		}

		@SuppressWarnings({ "unchecked", "unchecked" })
		List<String> values = (List<String>) params.get(key);

		// pad everything before the index with null values, since we don't
		// always start at zero
		if (values.size() < index) {
			for (int i = 0; i <= index; i++) {
				values.add(i, null);
			}
		}

		values.set(index, value);
		params.put(key, values);

		return params;
	}

	/**
	 * Creates a hashmap where the key is the name of the filter, and the value
	 * is a List of values. Side effect is that it removes all filters from the
	 * 'params' argument
	 * 
	 * @param params
	 * @return
	 */
	private ActionFilters processFilterParams(HashMap<String, String[]> params) {

		Map<String, List<String>> filtersMap = new HashMap<String, List<String>>();

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

				filtersMap.put(filterName, allValues);

			} else {
				stillHaveFilters = false;
			}

		} while (stillHaveFilters);

		ActionFilters filters = new ActionFilters(filtersMap);

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
