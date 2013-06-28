package com.markitserv.msws.internal.small;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.markitserv.msws.action.ActionCommand;
import com.markitserv.msws.action.ActionFilters;
import com.markitserv.msws.action.ActionParameters;
import com.markitserv.msws.action.CommonParamKeys;
import com.markitserv.msws.exceptions.ActionParamMissingException;
import com.markitserv.msws.exceptions.MalformedFiltersException;
import com.markitserv.msws.exceptions.MultipleParameterValuesException;
import com.markitserv.msws.internal.DebugUtils;
import com.markitserv.msws.web.HttpParamsToActionCommand;

public class HttpParamsToActionCommandTest {

	private static final String ACTION_PARAM_NAME = CommonParamKeys.PARAM_ACTION;
	private static final String ACTION_NAME = "SomeAction";

	private HttpParamsToActionCommand target;
	private ActionCommand expectedActionCommand;

	@Before
	public void setupEach() {
		target = new HttpParamsToActionCommand();
		
		// Create a new empty ActionCommand that's populated by each test
		Map<String, Object> paramMap = new HashMap<String, Object>();
		ActionParameters params = new ActionParameters(paramMap);
		
		Map<String, List<Object>> filtersMap = new HashMap<String, List<Object>>();
		ActionFilters filters = new ActionFilters(filtersMap);
		
		expectedActionCommand = new ActionCommand(ACTION_NAME, params, filters);
	}

	// ************************** Building from Http Requests

	@Test
	public void actionAndOneSimpleParam() {

		Map<String, String[]> p = buildHttpParams(null, "Foo", "Bar");

		expectedActionCommand.getParameters().addParameter("Foo", "Bar");
		ActionCommand actual = target.buildActionCommandFromHttpParams(p);

		// Util.printObjectDiffToConsole(expected, actual);
		assertEquals(expectedActionCommand, actual);
	}

	@Test(expected = MultipleParameterValuesException.class)
	public void dupedParamFails() {

		String[] values = { "Bar", "Baz" };
		Map<String, String[]> p = buildHttpParamsWithArray(null, "Foo", values);
		ActionCommand actual = target.buildActionCommandFromHttpParams(p);
	}

	@Test(expected = ActionParamMissingException.class)
	public void missingActionFails() {

		Map<String, String[]> p = buildHttpParams(null, "Foo", "Bar");

		// remove action
		p.remove(ACTION_PARAM_NAME);

		ActionCommand actual = target.buildActionCommandFromHttpParams(p);
	}

	// ************************** Building from Http Requests - Filters

	@Test
	public void basicFilters() {

		Map<String, String[]> p = buildHttpParams(null, "Filter.1.Name", "foo");
		p = buildHttpParams(p, "Filter.1.Value.1", "bar");
		p = buildHttpParams(p, "Filter.2.Name", "boo");
		p = buildHttpParams(p, "Filter.2.Value.1", "baz");

		List<Object> values1 = new ArrayList<Object>();
		values1.add("bar");
		expectedActionCommand.getFilters().addFilter("foo", values1);

		List<Object> values2 = new ArrayList<Object>();
		values2.add("baz");
		expectedActionCommand.getFilters().addFilter("boo", values2);

		// Compare
		ActionCommand actual = target.buildActionCommandFromHttpParams(p);
		assertEquals(expectedActionCommand, actual);
	}

	@Test
	public void filterWithMultipleValues() {

		Map<String, String[]> p = buildHttpParams(null, "Filter.1.Name", "foo");
		p = buildHttpParams(p, "Filter.1.Value.1", "bar");
		p = buildHttpParams(p, "Filter.1.Value.2", "baz");

		List<Object> values1 = new ArrayList<Object>();
		values1.add("bar");
		values1.add("baz");
		expectedActionCommand.getFilters().addFilter("foo", values1);

		// Compare
		ActionCommand actual = target.buildActionCommandFromHttpParams(p);
		
		assertEquals(expectedActionCommand, actual);
	}

	@Test(expected = MalformedFiltersException.class)
	public void filterWithMissingValueFails() {

		Map<String, String[]> p = buildHttpParams(null, "Filter.1.Name", "foo");
		ActionCommand actual = target.buildActionCommandFromHttpParams(p);
	}

	// ************************** Building from Http Requests - Multiple Values

	@Test
	public void paramWithMultipleValues() {

		Map<String, String[]> p = buildHttpParams(null, "Foo.1", "bar");
		p = buildHttpParams(p, "Foo.2", "baz");

		List<String> values = new ArrayList<String>();
		values.add("bar");
		values.add("baz");

		// Compare
		expectedActionCommand.getParameters().addParameter("Foo", values);
		ActionCommand actual = target.buildActionCommandFromHttpParams(p);

		DebugUtils.printObjectDiffToConsole(expectedActionCommand, actual);
		assertEquals(expectedActionCommand, actual);
	}
	
	@Test(expected = MultipleParameterValuesException.class)
	public void paramWithBothCollectionAndSingleValueFails() {

		Map<String, String[]> p = buildHttpParams(null, "Foo", "bar");
		p = buildHttpParams(p, "Foo.1", "baz");

		List<String> values = new ArrayList<String>();
		values.add("bar");
		values.add("baz");

		// Compare
		expectedActionCommand.getParameters().addParameter("Foo", values);
		ActionCommand actual = target.buildActionCommandFromHttpParams(p);

		DebugUtils.printObjectDiffToConsole(expectedActionCommand, actual);
		assertEquals(expectedActionCommand, actual);
	}

	/**
	 * eg :
	 * someItem.1.someItemProp=foo
	 * someItem.1.anotherItemProp=bar
	 * someItem.2.someItemProp=boo
	 * someItem.2.anotherItemProp=baz
	 * 
	 */
	@Test
	@Ignore
	public void paramWithMultipleValueObjects() {
		// Not yet implemented, but must be at some point
	}

	// ****************************** Helpers
	private Map<String, String[]> buildHttpParams(Map<String, String[]> p,
			String key, String value) {

		String[] array = { value };
		return this.buildHttpParamsWithArray(p, key, array);

	}

	private Map<String, String[]> buildHttpParamsWithArray(
			Map<String, String[]> p, String key, String[] value) {

		if (p == null) {
			p = new HashMap<String, String[]>();
			String[] valArray = { ACTION_NAME };
			p.put(ACTION_PARAM_NAME, valArray);
		}

		p.put(key, value);

		return p;
	}
}