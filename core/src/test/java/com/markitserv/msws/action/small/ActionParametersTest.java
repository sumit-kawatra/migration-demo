package com.markitserv.msws.action.small;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.markitserv.msws.action.ActionParameters;

public class ActionParametersTest {
	
	private Map<String, Object> paramsMap;
	private ActionParameters target;
	
	@Before
	public void setupEach() {
		paramsMap = new HashMap<String, Object>();
		target = new ActionParameters(paramsMap);
	}
		
	@Test
	public void canCreateActionParameters() {
		assertNotNull(target);
	}
	
	@Test
	public void canCheckIfParameterIsSet() {
		target.addParameter("foo", "bar");
		assertTrue(target.isParameterSet("foo"));
	}
	
	@Test
	public void canCheckIfParameterIsNotSet() {
		target.addParameter("foo", "bar");
		assertFalse(target.isParameterSet("baz"));
	}
	
	@Test
	public void canSetAndGetParameters() {
		target.addParameter("foo", "bar");
		assertEquals("bar", target.getParameter("foo", String.class));
	}
	
	@Test
	public void canGetParamAsBooleanWhenBooleanTrueProvided() {
		target.addParameter("foo", true);
		assertTrue(target.getParameter("foo", Boolean.class));
	}
	
	public void canGetParamAsBooleanWhenBooleanFalseProvided() {
		target.addParameter("foo", false);
		assertFalse(target.getParameter("foo", Boolean.class));
	}
	
	@Test
	public void canGetParamAsBooleanWhenStringProvided() {
		
	}
}
