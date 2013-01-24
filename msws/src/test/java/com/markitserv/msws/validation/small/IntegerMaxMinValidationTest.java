package com.markitserv.msws.validation.small;

import static org.junit.Assert.*;

import org.junit.Test;

import com.markitserv.msws.action.ActionCommand;
import com.markitserv.msws.exceptions.ValidationException;
import com.markitserv.msws.testutil.AbstractMswsTest;
import com.markitserv.msws.validation.IntegerMaxMinValidation;

public class IntegerMaxMinValidationTest extends AbstractMswsTest {

	@Test
	public void isValidatedWhenMinAndMaxValueIsUnlimited() {

		IntegerMaxMinValidation v = new IntegerMaxMinValidation(
				IntegerMaxMinValidation.UNLIMITED,
				IntegerMaxMinValidation.UNLIMITED);

		assertTrue(v.isValidInternal(10, null).isValid());
	}

	@Test
	public void isValidatedWhenValueIsGreaterThanMinValue() {

		IntegerMaxMinValidation v = new IntegerMaxMinValidation(1,
				IntegerMaxMinValidation.UNLIMITED);

		assertTrue(v.isValidInternal(10, null).isValid());
	}

	@Test
	public void isValidatedWhenValueIsEqualsToMinValue() {

		IntegerMaxMinValidation v = new IntegerMaxMinValidation(10,
				IntegerMaxMinValidation.UNLIMITED);

		assertTrue(v.isValidInternal(10, null).isValid());
	}

	@Test
	public void isValidatedWhenValueIsLessThanMaxValue() {

		IntegerMaxMinValidation v = new IntegerMaxMinValidation(
				IntegerMaxMinValidation.UNLIMITED, 11);

		assertTrue(v.isValidInternal(10, null).isValid());
	}

	@Test
	public void isValidatedWhenValueIsEqualsToMaxValue() {
		
		IntegerMaxMinValidation v = new IntegerMaxMinValidation(
				IntegerMaxMinValidation.UNLIMITED, 10);

		assertTrue(v.isValidInternal(10, null).isValid());
	}

	@Test
	public void isValidatedWhenValueIsBetweenMaxAndMinValues() {
		
		IntegerMaxMinValidation v = new IntegerMaxMinValidation(
				1, 100);

		assertTrue(v.isValidInternal(10, null).isValid());
	}

	@Test
	public void isNotValidatedIfValueIsLessThanMinValue() {
		
		IntegerMaxMinValidation v = new IntegerMaxMinValidation(
				10, IntegerMaxMinValidation.UNLIMITED);

		assertFalse(v.isValidInternal(9, null).isValid());
	}

	@Test
	public void isNotValidatedIfValueIsGreaterThanMaxValue() {
		
		IntegerMaxMinValidation v = new IntegerMaxMinValidation(
				IntegerMaxMinValidation.UNLIMITED, 10);

		assertFalse(v.isValidInternal(11, null).isValid());
	}

	@Test
	public void isNotValidatedIfValueIsNotAnInteger() {
		
		IntegerMaxMinValidation v = new IntegerMaxMinValidation(
				IntegerMaxMinValidation.UNLIMITED, 10);

		assertFalse(v.isValidInternal("foo", null).isValid());
	}
}
