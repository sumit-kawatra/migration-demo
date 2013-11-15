package com.markitserv.msws.internal.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markitserv.msws.action.AbstractAction;
import com.markitserv.msws.action.ActionCommand;
import com.markitserv.msws.action.ActionFilters;
import com.markitserv.msws.action.ActionParameters;
import com.markitserv.msws.action.ParamsAndFiltersDefinition;
import com.markitserv.msws.action.resp.ActionResult;
import com.markitserv.msws.action.resp.SuccessFailure;
import com.markitserv.msws.svc.AbstractArtificialApplicationStateManager;
import com.markitserv.msws.validation.BooleanValidation;
import com.markitserv.msws.validation.OptionalValidation;
import com.markitserv.msws.validation.RequiredValidation;

@Service
public class ManageArtificialApplicationStates extends AbstractAction {

	private static final String PARAM_STATE_NAME = "StateName";
	private static final String PARAM_STATE_VALUE = "StateValue";
	private static final String PARAM_CLEAR_ALL = "ClearAll";

	private Logger log = LoggerFactory
			.getLogger(ManageArtificialApplicationStates.class);

	@Autowired
	AbstractArtificialApplicationStateManager stateMgr;

	@Override
	protected ActionResult performAction(ActionCommand cmd) {

		ActionParameters params = cmd.getParameters();

		if (params.isParameterSet(PARAM_CLEAR_ALL)) {
			if (params.getParameter(PARAM_CLEAR_ALL, Boolean.class)) {
				stateMgr.clearAll();
			}
		}

		String stateName = params.getParameter(PARAM_STATE_NAME, String.class);
		Boolean stateValue = params.getParameter(PARAM_STATE_VALUE,
				Boolean.class);

		stateMgr.setState(stateName, stateValue);

		return new ActionResult(new SuccessFailure(true));
	}

	@Override
	protected ParamsAndFiltersDefinition createParameterDefinition() {

		ParamsAndFiltersDefinition def = super.createParameterDefinition();

		def.addValidation(PARAM_STATE_NAME, new RequiredValidation());

		def.addValidation(PARAM_STATE_VALUE, new RequiredValidation());
		def.addValidation(PARAM_STATE_VALUE, new BooleanValidation());

		def.addValidation(PARAM_CLEAR_ALL, new OptionalValidation());
		def.addValidation(PARAM_CLEAR_ALL, new BooleanValidation());

		return def;
	}
}
