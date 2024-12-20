package com.markitserv.msws.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.markitserv.msws.action.ActionCommand;
import com.markitserv.msws.action.ActionFilters;
import com.markitserv.msws.action.ActionParameters;
import com.markitserv.msws.action.CommonParamKeys;
import com.markitserv.msws.exceptions.ActionParamMissingException;
import com.markitserv.msws.exceptions.MalformedFiltersException;
import com.markitserv.msws.exceptions.MultipleParameterValuesException;
import com.markitserv.msws.exceptions.MswsException;

/**
 * Builds an ActionCommand from HTTP paramas.
 * 
 * @author roy.truelove
 * 
 */
@Service
public class HttpParamsToActionCommand {

	public ActionCommand buildActionCommandFromHttpParams(
			Map<String, String[]> httpParams) throws MswsException {

		// Need a mutable copy
		HashMap<String, String[]> params = new HashMap<String, String[]>(
				httpParams);

		ActionFilters filters = processFilterParams(params);

		String action = null;

		Map<String, Object> processedParamsMap = new HashMap<String, Object>();
		for (String key : params.keySet()) {
			
			if (isIgnoreableParam(key))
				continue;
			
			String[] valueArr = params.get(key);

			if (valueArr.length != 1) {
				throw MultipleParameterValuesException.standardException(key);
			}

			String value = valueArr[0];

			if (key.equals(CommonParamKeys.PARAM_ACTION)) {
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

	/**
	 * jsonp parameters should be removed.  Hate doing this here, would rather do it in the filter, but we need Servlet 3 api
	 * These are the params set by jquery.  Also ignores anything with an _, which because of IE bullshit, are used to make AJAX
	 * Calls unique.
	 * @param key
	 * @return
	 */
	private boolean isIgnoreableParam(String key) {
		return (key.equalsIgnoreCase("callback") || key.startsWith("_"));
	}

	private boolean isMultiValue(String key) {
		return key.matches("(.*)\\.(\\d+)");
	}

	private Map<String, Object> processParamWithMultipleValues(
			Map<String, Object> params, String mValKey, String value) throws MswsException {

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

		// If they do something like 'foo.1' and then 'foo'
		if ((params.get(key) instanceof String)) {
			throw MultipleParameterValuesException.standardException(key);
		}
		
		List<String> values = (List<String>) params.get(key);

		// pad everything before the index with null values, since we don't
		// always start at zero
		if (values.size() <= index) {
			for (int i = values.size(); i <= index; i++) {
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
	private ActionFilters processFilterParams(HashMap<String, String[]> params) throws MswsException {

		Map<String, List<Object>> filtersMap = new HashMap<String, List<Object>>();

		int nameCounter = 0;
		boolean stillHaveFilters = true;

		// is there a more efficient way?
		do {
			nameCounter++;

			String filterNameKey = CommonParamKeys.PARAM_FILTER + "."
					+ nameCounter + ".Name";
			String[] filterNameArr = params.remove(filterNameKey);

			// if there is a filter
			if (filterNameArr != null) {

				if (filterNameArr.length != 1) {
					MultipleParameterValuesException
							.standardException(filterNameKey);
				}

				String filterName = filterNameArr[0];

				List<Object> allValues = processFilterValues(params,
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

	private List<Object> processFilterValues(HashMap<String, String[]> params,
			int nameCounter) throws MswsException {
		// iterate through the values of the filter and push them to a stack

		int valueCounter = 0;
		boolean stillHaveFilterValues = true;
		Stack<Object> filterValues = new Stack<Object>();

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
