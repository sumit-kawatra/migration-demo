package com.markitserv.msws.validation.small;

import static org.junit.Assert.*;

import org.junit.Test;

import com.markitserv.msws.action.ActionCommand;
import com.markitserv.msws.exceptions.ValidationException;
import com.markitserv.msws.testutil.AbstractMswsTest;
import com.markitserv.msws.validation.IntegerMaxMinValidation;
import com.markitserv.msws.validation.ValidationResponse;

public class IntegerMaxMinValidationTest extends AbstractMswsTest {

	@Test
	public void isValidatedWhenMinAndMaxValueIsUnlimited() {

		IntegerMaxMinValidation v = new IntegerMaxMinValidation(
				IntegerMaxMinValidation.UNLIMITED, IntegerMaxMinValidation.UNLIMITED);

		assertTrue(v.validateInternal(10, null).isValid());
	}

	@Test
	public void isValidatedWhenValueIsGreaterThanMinValue() {

		IntegerMaxMinValidation v = new IntegerMaxMinValidation(1,
				IntegerMaxMinValidation.UNLIMITED);

		assertTrue(v.validateInternal(10, null).isValid());
	}

	@Test
	public void isValidatedWhenValueIsEqualsToMinValue() {

		IntegerMaxMinValidation v = new IntegerMaxMinValidation(10,
				IntegerMaxMinValidation.UNLIMITED);

		assertTrue(v.validateInternal(10, null).isValid());
	}

	@Test
	public void isValidatedWhenValueIsLessThanMaxValue() {

		IntegerMaxMinValidation v = new IntegerMaxMinValidation(
				IntegerMaxMinValidation.UNLIMITED, 11);

		ValidationResponse resp = v.validateInternal(10, null);
		assertNotNull(resp);
		assertTrue(resp.isValid());
	}

	@Test
	public void isValidatedWhenValueIsEqualsToMaxValue() {

		IntegerMaxMinValidation v = new IntegerMaxMinValidation(
				IntegerMaxMinValidation.UNLIMITED, 10);

		assertTrue(v.validateInternal(10, null).isValid());
	}

	@Test
	public void isValidatedWhenValueIsBetweenMaxAndMinValues() {

		IntegerMaxMinValidation v = new IntegerMaxMinValidation(1, 100);

		assertTrue(v.validateInternal(10, null).isValid());
	}

	@Test
	public void isNotValidatedIfValueIsLessThanMinValue() {

		IntegerMaxMinValidation v = new IntegerMaxMinValidation(10,
				IntegerMaxMinValidation.UNLIMITED);

		assertFalse(v.validateInternal(9, null).isValid());
	}

	@Test
	public void isNotValidatedIfValueIsGreaterThanMaxValue() {

		IntegerMaxMinValidation v = new IntegerMaxMinValidation(
				IntegerMaxMinValidation.UNLIMITED, 10);

		assertFalse(v.validateInternal(11, null).isValid());
	}

	@Test
	public void isNotValidatedIfValueIsNotAnInteger() {

		IntegerMaxMinValidation v = new IntegerMaxMinValidation(
				IntegerMaxMinValidation.UNLIMITED, 10);

		assertFalse(v.validateInternal("foo", null).isValid());
	}
}
