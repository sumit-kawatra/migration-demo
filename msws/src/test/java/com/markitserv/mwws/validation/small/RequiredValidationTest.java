package com.markitserv.mwws.validation.small;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.markitserv.mwws.Type;
import com.markitserv.mwws.action.AbstractAction;
import com.markitserv.mwws.action.ActionCommand;
import com.markitserv.mwws.action.ActionFilters;
import com.markitserv.mwws.action.ActionParameters;
import com.markitserv.mwws.action.ActionResult;
import com.markitserv.mwws.definition.ParamsAndFiltersDefinition;
import com.markitserv.mwws.internal.UuidGenerator;
import com.markitserv.mwws.validation.RequiredValidation;

public class RequiredValidationTest extends AbstractValidationTest {
	
	private TestActionCommandBuilder cmdBuilder;
	private TestAction action;
	
	// dependencies
	private UuidGenerator uuidGenerator;
	
	@Before
	public void setupEach() {
		
		// Stubs
		uuidGenerator = Mockito.mock(UuidGenerator.class);
		Mockito.when(uuidGenerator.generateUuid()).thenReturn("Stubbed UUID");
		
		cmdBuilder = new TestActionCommandBuilder();
		action = new TestAction();
		
		action.setUuidGenerator(uuidGenerator);
		
	}

	/**
	 * Used simply to return 'true' if the action was successfully validated
	 * 
	 * @author roy.truelove
	 * 
	 */
	private class BooleanType extends Type {

		public boolean successful;

		public boolean isSuccessful() {
			return successful;
		}

		public void setSuccessful(boolean successful) {
			this.successful = successful;
		}
	}

	private class TestAction extends AbstractAction {

		@Override
		protected ActionResult performAction(ActionParameters params,
				ActionFilters filters) {

			ActionResult res = new ActionResult();
			res.setItem(new BooleanType());

			return res;
		}

		@Override
		protected ParamsAndFiltersDefinition getParameterDefinition() {

			ParamsAndFiltersDefinition def = new ParamsAndFiltersDefinition();
			def.addValidation("Required", new RequiredValidation());

			return def;
		}
	}

	@Test
	public void succeedsIfRequiredParamProvided() {
		
		ActionCommand cmd = cmdBuilder.addParam("Required", "foo").build();
		action.performAction(cmd);
	}
	
	@Test
	public void failsIfRequiredParamNotProvided() {
		
		ActionCommand cmd = cmdBuilder.build();
		action.performAction(cmd);
	}
	protected class TestActionCommandBuilder {

		private ActionCommand cmd;

		public TestActionCommandBuilder() {

			HashMap<String, Object> paramsMap = new HashMap<String, Object>();
			ActionParameters params = new ActionParameters(paramsMap);

			Map<String, List<String>> filtersMap = new HashMap<String, List<String>>();
			ActionFilters filters = new ActionFilters(filtersMap);

			cmd = new ActionCommand("TestAction", params, filters);
		}

		public TestActionCommandBuilder addParam(String key, Object value) {
			cmd.getParameters().addParameter(key, value);
			return this;
		}

		public TestActionCommandBuilder addFilter(String key, String value) {

			Map<String, List<String>> map = cmd.getFilters().getAllFilters();

			List<String> filters = null;
			if (map.containsKey(key)) {
				filters = map.get(key);
			} else {
				filters = new ArrayList<String>();
			}

			filters.add(value);

			cmd.getFilters().addFilter(key, filters);
			return this;
		}

		public ActionCommand build() {
			return cmd;
		}
	}
}
