package com.markitserv.msws.validation.small;

import org.junit.Test;

import com.markitserv.msws.action.ActionCommand;
import com.markitserv.msws.exceptions.ValidationException;
import com.markitserv.msws.testutil.AbstractMswsTest;
import com.markitserv.msws.validation.OneOfValidation;
import com.markitserv.msws.validation.RequiredValidation;

public class OneOfValidationTest extends AbstractMswsTest {

	@Test
	public void isValidatedIfOneOfValuesIsProvided() {

		ActionCommand cmd = actionCommandBuilder.addParam("Value", "foo").build();
		
		String[] x = {"bar", "foo", "baz"};
		
		fakeTestAction.addParameterValdiation("Value", new OneOfValidation(x));
		fakeTestAction.performAction(cmd);
	}

	@Test(expected = ValidationException.class)
	public void isNotValidatedIfValueIsNotInListOfOneOfValues() {
		
		ActionCommand cmd = actionCommandBuilder.addParam("Value", "boo").build();
		
		String[] x = {"bar", "foo", "baz"};
		
		fakeTestAction.addParameterValdiation("Value", new OneOfValidation(x));
		fakeTestAction.performAction(cmd);
	}
}
