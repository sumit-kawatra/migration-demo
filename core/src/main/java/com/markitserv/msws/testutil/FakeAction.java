package com.markitserv.msws.testutil;

import com.markitserv.msws.action.AbstractAction;
import com.markitserv.msws.action.ActionFilters;
import com.markitserv.msws.action.ActionParameters;
import com.markitserv.msws.action.ActionResult;
import com.markitserv.msws.definition.ParamsAndFiltersDefinition;
import com.markitserv.msws.validation.AbstractValidation;

/**
 * Simple fake action, used for testing
 * @author roy.truelove
 *
 */
public class FakeAction extends AbstractAction {

	private ParamsAndFiltersDefinition paramDef;
	private ParamsAndFiltersDefinition filterDef;

	@Override
	protected ActionResult performAction(ActionParameters params,
			ActionFilters filters) {

		FakeType payload = new FakeType(true);
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
	
	public void setFilterDefinition(ParamsAndFiltersDefinition def) {
		this.filterDef = def;
	}
}