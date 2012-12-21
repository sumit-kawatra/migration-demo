package com.markitserv.mwws.small;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.markitserv.mwws.ActionBuilder;
import com.markitserv.mwws.ActionCommand;
import com.markitserv.mwws.CommonParamKeys;

import de.danielbechler.diff.ObjectDiffer;
import de.danielbechler.diff.ObjectDifferFactory;
import de.danielbechler.diff.node.Node;
import de.danielbechler.diff.visitor.PrintingVisitor;

import com.markitserv.mwws.testutil.Util;

public class ActionBuilderTest {

	private ActionBuilder target;
	private static final String ACTION_PARAM_NAME = CommonParamKeys.Action
			.toString();

	@Before
	public void setupEach() {
		target = new ActionBuilder();
	}

	@Test
	public void canBuildFromHttpParamsWithActionAndOneSimpleParam() {

		String action = "SomeAction";

		Map<String, String[]> p = buildHttpParams(null, ACTION_PARAM_NAME,
				action);
		p = buildHttpParams(p, "Foo", "Bar");

		ActionCommand expected = new ActionCommand(action, null);
		expected = buildActionCmdParams(expected, "Foo", "Bar");

		ActionCommand actual = target.buildActionFromHttpParams(p);
		
		// Util.printObjectDiffToConsole(expected, actual);
		assertEquals(expected, actual);
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
		}

		p.put(key, value);

		return p;
	}

	private ActionCommand buildActionCmdParams(ActionCommand cmd, String key,
			Object value) {

		Map<String, Object> p = cmd.getParams();

		if (p == null) {
			p = new HashMap<String, Object>();
		}

		p.put(key, value);

		cmd.setParams(p);

		return cmd;
	}
}
