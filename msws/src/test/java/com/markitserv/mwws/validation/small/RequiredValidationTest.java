package com.markitserv.mwws.validation.small;

import org.junit.Test;

import com.markitserv.mwws.action.ActionCommand;
import com.markitserv.mwws.exceptions.ValidationException;
import com.markitserv.mwws.validation.RequiredValidation;

public class RequiredValidationTest extends AbstractValidationTest {

	@Test
	public void succeedsIfRequiredParamProvided() {

		ActionCommand cmd = cmdBuilder.addParam("Required", "foo").build();
		action.addParameterValdiation("Required", new RequiredValidation());
		action.performAction(cmd);
	}

	@Test(expected = ValidationException.class)
	public void failsIfRequiredParamNotProvided() {
		
		action.addParameterValdiation("Required", new RequiredValidation());

		ActionCommand cmd = cmdBuilder.build();
		action.performAction(cmd);
	}
}
