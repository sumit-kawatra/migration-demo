package com.markitserv.msws.testutil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.markitserv.msws.Type;
import com.markitserv.msws.action.AbstractAction;
import com.markitserv.msws.action.ActionCommand;
import com.markitserv.msws.action.ActionFilters;
import com.markitserv.msws.action.ActionParameters;
import com.markitserv.msws.action.ActionResult;
import com.markitserv.msws.definition.ParamsAndFiltersDefinition;
import com.markitserv.msws.validation.AbstractValidation;

public class ActionAndActionCommandHelpers {
	
	public class BooleanType extends Type {

		public boolean successful;

		public void setSuccessful(boolean successful) {
			this.successful = successful;
		}
	}

	public class TestAction extends AbstractAction {

		private ParamsAndFiltersDefinition paramDef;
		private ParamsAndFiltersDefinition filterDef;

		@Override
		protected ActionResult performAction(ActionParameters params,
				ActionFilters filters) {

			BooleanType payload = new BooleanType();
			payload.setSuccessful(true);
			ActionResult res = new ActionResult(payload);

			return res;
		}

		public void addParameterValdiation(String key, AbstractValidation value) {
			createParameterDefinition().addValidation(key, value);
		}

		public void addFilterValdiation(String key, AbstractValidation value) {
			createFilterDefinition().addValidation(key, value);
		}

		@Override
		protected ParamsAndFiltersDefinition createParameterDefinition() {
			if (paramDef == null) {
				paramDef = super.createParameterDefinition();
			}
			
			return paramDef;
		}

		@Override
		protected ParamsAndFiltersDefinition createFilterDefinition() {
			
			if (filterDef == null) {
				filterDef = super.createFilterDefinition();
			}
			
			return filterDef;
		
		}

		public void addParameterDefault(String key, String value) {
			this.createParameterDefinition().addDefaultParam(key, value);
		}
		
		public void setParameterDefinition(ParamsAndFiltersDefinition def) {
			this.paramDef = def;
		}
	}
	
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

			Map<String, List<String>> filtersMap = new HashMap<String, List<String>>();
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
