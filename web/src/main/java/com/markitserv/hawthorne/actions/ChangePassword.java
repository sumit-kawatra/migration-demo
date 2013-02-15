package com.markitserv.hawthorne.actions;

import org.springframework.stereotype.Service;

import com.markitserv.hawthorne.types.SuccessFailure;
import com.markitserv.msws.action.AbstractAction;
import com.markitserv.msws.action.ActionFilters;
import com.markitserv.msws.action.ActionParameters;
import com.markitserv.msws.action.ActionResult;
import com.markitserv.msws.definition.ParamsAndFiltersDefinition;
import com.markitserv.msws.validation.PasswordValidation;
import com.markitserv.msws.validation.RequiredValidation;

/**
 * Action changing the password
 * 
 * @author swati.choudhari
 * 
 */

@Service
public class ChangePassword extends AbstractAction {
	private static final String PARAM_PASSWORD = "Password";

	protected ActionResult performAction(ActionParameters params, ActionFilters filters) {
		// TODO - DB call to change password
		// TODO - DB call to Set Password expiry to 30 days
		// Considering successful action execution - Password Changed
		// successfully.
		SuccessFailure successFailure = new SuccessFailure(true);
		return new ActionResult(successFailure);
	}

	@Override
	protected ParamsAndFiltersDefinition createParameterDefinition() {

		ParamsAndFiltersDefinition params = super.createParameterDefinition();
		params.addValidation(PARAM_PASSWORD, new RequiredValidation());
		params.addValidation(PARAM_PASSWORD, new PasswordValidation());
		return params;
	}
}
