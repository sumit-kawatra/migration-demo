package com.markitserv.mwws.validation.small;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.mockito.Mockito;

import com.markitserv.mwws.Type;
import com.markitserv.mwws.action.AbstractAction;
import com.markitserv.mwws.action.ActionCommand;
import com.markitserv.mwws.action.ActionFilters;
import com.markitserv.mwws.action.ActionParameters;
import com.markitserv.mwws.action.ActionResult;
import com.markitserv.mwws.definition.ParamsAndFiltersDefinition;
import com.markitserv.mwws.internal.UuidGenerator;
import com.markitserv.mwws.validation.AbstractValidation;

public abstract class AbstractValidationTest {

	protected TestActionCommandBuilder cmdBuilder;
	protected TestAction action;

	// dependencies
	private UuidGenerator uuidGenerator;

	private class BooleanType extends Type {

		public boolean successful;

		public void setSuccessful(boolean successful) {
			this.successful = successful;
		}
	}

	protected class TestAction extends AbstractAction {

		private ParamsAndFiltersDefinition paramDef;
		private ParamsAndFiltersDefinition filterDef;

		@Override
		protected ActionResult performAction(ActionParameters params,
				ActionFilters filters) {

			ActionResult res = new ActionResult();
			BooleanType payload = new BooleanType();
			payload.setSuccessful(true);
			res.setItem(payload);

			return res;
		}

		public void addParameterValdiation(String key, AbstractValidation value) {
			getParameterDefinition().addValidation(key, value);
		}

		public void addFilterValdiation(String key, AbstractValidation value) {
			getFilterDefinition().addValidation(key, value);
		}

		@Override
		protected ParamsAndFiltersDefinition getParameterDefinition() {
			if (paramDef == null) {
				paramDef = super.getParameterDefinition();
			}
			
			return paramDef;
		}

		@Override
		protected ParamsAndFiltersDefinition getFilterDefinition() {
			
			if (filterDef == null) {
				filterDef = super.getFilterDefinition();
			}
			
			return filterDef;
		}
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

	@Before
	public void setupEach() {

		// Stubs
		uuidGenerator = Mockito.mock(UuidGenerator.class);
		Mockito.when(uuidGenerator.generateUuid()).thenReturn("Stubbed UUID");

		cmdBuilder = new TestActionCommandBuilder();
		action = new TestAction();

		action.setUuidGenerator(uuidGenerator);

	}
}
