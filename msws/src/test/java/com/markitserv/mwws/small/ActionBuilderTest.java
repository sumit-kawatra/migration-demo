package com.markitserv.mwws.small;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import com.markitserv.mwws.ActionBuilder;
import com.markitserv.mwws.ActionCommand;
import com.markitserv.mwws.CommonParamKeys;
import com.markitserv.mwws.exceptions.MalformedFiltersException;
import com.markitserv.mwws.exceptions.MultipleParameterValuesException;
import com.markitserv.mwws.exceptions.ActionParamMissingException;
import com.markitserv.mwws.testutil.Util;

public class ActionBuilderTest {

	private ActionBuilder target;
	private static final String ACTION_PARAM_NAME = CommonParamKeys.Action
			.toString();
	private static final String ACTION_NAME = "SomeAction";

	@Before
	public void setupEach() {
		target = new ActionBuilder();
	}

	// ************************** Building from Http Requests

	@Test
	public void canBuildFromHttpParamsWithActionAndOneSimpleParam() {

		Map<String, String[]> p = buildHttpParams(null, "Foo", "Bar");

		ActionCommand expected = buildActionCmd(null, "Foo", "Bar");
		ActionCommand actual = target.buildActionFromHttpParams(p);

		// Util.printObjectDiffToConsole(expected, actual);
		assertEquals(expected, actual);
	}

	@Test(expected = MultipleParameterValuesException.class)
	public void httpParamsWithDupedParamsFail() {

		String[] values = { "Bar", "Baz" };
		Map<String, String[]> p = buildHttpParamsWithArray(null, "Foo", values);
		ActionCommand actual = target.buildActionFromHttpParams(p);
	}

	@Test(expected=ActionParamMissingException.class)
	public void httpParamsWithMissingAction() {
		
		Map<String, String[]> p = buildHttpParams(null, "Foo", "Bar");
		
		// remove action
		p.remove(ACTION_PARAM_NAME);
		
		ActionCommand actual = target.buildActionFromHttpParams(p);
	}

	// ************************** Building from Http Requests - Filters
		
	@Test
	public void canBuildFromHttpParamsWithBasicFilters() {
		
		Map<String, String[]> p = buildHttpParams(null, "Filter.1.Name", "foo");
		p = buildHttpParams(p, "Filter.1.Value.1", "bar");
		p = buildHttpParams(p, "Filter.2.Name", "boo");
		p = buildHttpParams(p, "Filter.2.Value.1", "baz");
		
		// build filters
		Map<String, List<String>> filter = new HashMap<String, List<String>>();
		
		List<String> values1 = new ArrayList<String>();
		values1.add("bar");
		filter.put("foo", values1);
		
		List<String> values2 = new ArrayList<String>();
		values2.add("baz");
		filter.put("boo", values2);
		
		// Compare
		ActionCommand expected = buildActionCmd(null, "Filter", filter);
		ActionCommand actual = target.buildActionFromHttpParams(p);
		
		assertEquals(expected, actual);
	}

	@Test
	public void canBuildFromHttpParamsWithFilterWithMultipleValues() {
		
		Map<String, String[]> p = buildHttpParams(null, "Filter.1.Name", "foo");
		p = buildHttpParams(p, "Filter.1.Value.1", "bar");
		p = buildHttpParams(p, "Filter.1.Value.2", "baz");
		
		// build filters
		Map<String, List<String>> filter = new HashMap<String, List<String>>();
		
		List<String> values1 = new ArrayList<String>();
		values1.add("bar");
		values1.add("baz");
		filter.put("foo", values1);
		
		// Compare
		ActionCommand expected = buildActionCmd(null, "Filter", filter);
		ActionCommand actual = target.buildActionFromHttpParams(p);
		
		assertEquals(expected, actual);
	}

	@Test(expected = MalformedFiltersException.class)
	public void httpParamsFilterWithMissingValue() {
		
		Map<String, String[]> p = buildHttpParams(null, "Filter.1.Name", "foo");
		ActionCommand actual = target.buildActionFromHttpParams(p);
	}

	// ************************** Building from Http Requests - Multiple Values
	
	@Test
	public void httpParamsFilterWithMultipleValuesForParam() {
		
		Map<String, String[]> p = buildHttpParams(null, "Foo.1", "bar");
		p = buildHttpParams(p, "Foo.2", "baz");
		
		List<String> values = new ArrayList<String>();
		values.add("bar");
		values.add("baz");
		
		// Compare
		ActionCommand expected = buildActionCmd(null, "Foo", values);
		ActionCommand actual = target.buildActionFromHttpParams(p);
		
		//Util.printObjectDiffToConsole(expected, actual);
		assertEquals(expected, actual);
	}
	
	@Test
	@Ignore
	public void httpParamsFilterWithMultipleValueObjectsForParam() {
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

	private ActionCommand buildActionCmd(ActionCommand cmd, String key,
			Object value) {

		Map<String, Object> p;

		if (cmd == null) {
			p = new HashMap<String, Object>();
			cmd = new ActionCommand(ACTION_NAME, p);
		} else {
			p = cmd.getParams();
		}

		p.put(key, value);

		cmd.setParams(p);

		return cmd;
	}
}
