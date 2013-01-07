package com.markitserv.mwws.validation.small;

import org.junit.Test;

import com.markitserv.mwws.action.ActionCommand;
import com.markitserv.mwws.exceptions.ValidationException;
import com.markitserv.mwws.testutil.AbstractMswsTest;
import com.markitserv.mwws.validation.IntegerMaxMinValidation;

public class IntegerMaxMinValidationTest extends AbstractMswsTest {

	@Test
	public void isValidatedWhenMinAndMaxValueIsUnlimited() {

		ActionCommand cmd = actionCommandBuilder.addParam("Value", 10).build();

		testAction.addParameterValdiation("Value", new IntegerMaxMinValidation(
				IntegerMaxMinValidation.UNLIMITED,
				IntegerMaxMinValidation.UNLIMITED));
		testAction.performAction(cmd);
	}

	@Test
	public void isValidatedWhenValueIsGreaterThanMinValue() {

		ActionCommand cmd = actionCommandBuilder.addParam("Value", 10).build();

		testAction.addParameterValdiation("Value", new IntegerMaxMinValidation(1,
				IntegerMaxMinValidation.UNLIMITED));
		testAction.performAction(cmd);
	}

	@Test
	public void isValidatedWhenValueIsEqualsToMinValue() {

		ActionCommand cmd = actionCommandBuilder.addParam("Value", 10).build();

		testAction.addParameterValdiation("Value", new IntegerMaxMinValidation(10,
				IntegerMaxMinValidation.UNLIMITED));
		testAction.performAction(cmd);

	}

	@Test
	public void isValidatedWhenValueIsLessThanMaxValue() {

		ActionCommand cmd = actionCommandBuilder.addParam("Value", 10).build();

		testAction.addParameterValdiation("Value", new IntegerMaxMinValidation(
				IntegerMaxMinValidation.UNLIMITED, 11));
		testAction.performAction(cmd);

	}

	@Test
	public void isValidatedWhenValueIsEqualsToMaxValue() {
		
		ActionCommand cmd = actionCommandBuilder.addParam("Value", 10).build();

		testAction.addParameterValdiation("Value", new IntegerMaxMinValidation(
				IntegerMaxMinValidation.UNLIMITED, 10));
		testAction.performAction(cmd);
	}
	
	@Test
	public void isValidatedWhenValueIsBetweenMaxAndMinValues() {
		
		ActionCommand cmd = actionCommandBuilder.addParam("Value", 10).build();

		testAction.addParameterValdiation("Value", new IntegerMaxMinValidation(
				1, 100));
		testAction.performAction(cmd);
	}

	@Test(expected = ValidationException.class)
	public void isNotValidatedIfValueIsLessThanMinValue() {

		ActionCommand cmd = actionCommandBuilder.addParam("Value", 9).build();

		testAction.addParameterValdiation("Value", new IntegerMaxMinValidation(10,
				IntegerMaxMinValidation.UNLIMITED));
		testAction.performAction(cmd);
	}

	@Test(expected = ValidationException.class)
	public void isNotValidatedIfValueIsGreaterThanMaxValue() {
		
		ActionCommand cmd = actionCommandBuilder.addParam("Value", 11).build();

		testAction.addParameterValdiation("Value", new IntegerMaxMinValidation(
				IntegerMaxMinValidation.UNLIMITED, 10));
		testAction.performAction(cmd);
	}

	@Test(expected = ValidationException.class)
	public void isNotValidatedIfValueIsNotAnInteger() {

		ActionCommand cmd = actionCommandBuilder.addParam("Value", "foo").build();

		testAction.addParameterValdiation("Value", new IntegerMaxMinValidation(
				IntegerMaxMinValidation.UNLIMITED, 10));
		testAction.performAction(cmd);
	}
}
