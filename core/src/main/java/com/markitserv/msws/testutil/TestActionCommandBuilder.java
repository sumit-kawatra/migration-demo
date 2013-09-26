package com.markitserv.msws.testutil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.markitserv.msws.action.ActionFilters;
import com.markitserv.msws.action.ActionParameters;
import com.markitserv.msws.action.internal.ActionCommand;

/**
 * Used to create an 'ActionCommand' for testing Actions
 * @author roy.truelove
 *
 */
public class TestActionCommandBuilder {

	private ActionCommand cmd;

	public TestActionCommandBuilder() {

		HashMap<String, Object> paramsMap = new HashMap<String, Object>();
		ActionParameters params = new ActionParameters(paramsMap);

		Map<String, List<Object>> filtersMap = new HashMap<String, List<Object>>();
		ActionFilters filters = new ActionFilters(filtersMap);

		cmd = new ActionCommand("TestAction", params, filters);
	}

	public TestActionCommandBuilder addParam(String key, Object value) {
		cmd.getParameters().addParameter(key, value);
		return this;
	}
	
	public TestActionCommandBuilder addParamCollectionElement(String key, Object value) {
		
		List<Object> col = null;
		
		if (cmd.getParameters().isParameterSet(key)) {
			col = cmd.getParameters().getParameter(key, List.class);
		} else {
			col = new ArrayList<Object>();
		}
		
		col.add(value);
		
		cmd.getParameters().addParameter(key, col);
		return this;
	}

	public TestActionCommandBuilder addFilter(String key, String value) {

		Map<String, List<Object>> map = cmd.getFilters().getAllFilters();

		List<Object> filters = null;
		if (map.containsKey(key)) {
			filters = map.get(key);
		} else {
			filters = new ArrayList<Object>();
		}

		filters.add(value);

		cmd.getFilters().addFilter(key, filters);
		return this;
	}

	public ActionCommand build() {
		return cmd;
	}
}