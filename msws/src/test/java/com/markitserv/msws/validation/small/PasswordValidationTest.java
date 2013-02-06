package com.markitserv.msws.validation.small;

import static org.junit.Assert.*;

import org.junit.Test;

import com.markitserv.msws.testutil.AbstractMswsTest;
import com.markitserv.msws.validation.PasswordValidation;

public class PasswordValidationTest extends AbstractMswsTest {
	
	@Test
	public void isNotValidatedWhenLenghLessThanMin() {

		PasswordValidation v = new PasswordValidation();
		assertFalse(v.isValidInternal("Abcd1", null).isValid());
	}
	
	@Test
	public void isNotValidatedWhenLenghGreaterThanMax() {

		PasswordValidation v = new PasswordValidation();
		assertFalse(v.isValidInternal("Abcdefghijklmnop1", null).isValid());
	}
	
	@Test
	public void isNotValidatedWhenContainsWhiteSpaces() {

		PasswordValidation v = new PasswordValidation();
		assertFalse(v.isValidInternal("Abcde f1", null).isValid());
	}
	
	@Test
	public void isNotValidatedWhenNoUpperCaseAlphabet() {

		PasswordValidation v = new PasswordValidation();
		assertFalse(v.isValidInternal("abcdefgh1", null).isValid());
	}
	
	@Test
	public void isNotValidatedWhenNoLowerCaseAlphabet() {

		PasswordValidation v = new PasswordValidation();
		assertFalse(v.isValidInternal("ABCDEFGHIJ1", null).isValid());
	}
	
	@Test
	public void isNotValidatedWhenNoDigit() {

		PasswordValidation v = new PasswordValidation();
		assertFalse(v.isValidInternal("aBCDEFGHIJ", null).isValid());
	}
	
	@Test
	public void isValidatedWhenAtLeastOneDigit() {

		PasswordValidation v = new PasswordValidation();
		assertTrue(v.isValidInternal("aBCDEFGHIJ1", null).isValid());
	}
	
	@Test
	public void isValidatedWhenAtLeastOneUpperCaseAlphabet() {

		PasswordValidation v = new PasswordValidation();
		assertTrue(v.isValidInternal("Abcdefhj1", null).isValid());
	}
	
	@Test
	public void isValidatedWhenAtLeastOneLowerCaseAlphabet() {

		PasswordValidation v = new PasswordValidation();
		assertTrue(v.isValidInternal("aBDFHJGFDDFJ9", null).isValid());
	}
	
	@Test
	public void isValidatedWhenOneOrMoreDigits() {

		PasswordValidation v = new PasswordValidation();
		assertTrue(v.isValidInternal("aB9564545645645", null).isValid());
	}
	
	@Test
	public void isValidatedWhenOneOrMoreUpperCaseAlphabets() {

		PasswordValidation v = new PasswordValidation();
		assertTrue(v.isValidInternal("aBUHGTHREGDF8", null).isValid());
	}
	
	@Test
	public void isValidatedWhenOneOrMoreLowerCaseAlphabets() {

		PasswordValidation v = new PasswordValidation();
		assertTrue(v.isValidInternal("Mfgtujghi5", null).isValid());
	}
	
	@Test
	public void isValidatedWhenContainsSpecialChar() {

		PasswordValidation v = new PasswordValidation();
		assertTrue(v.isValidInternal("Mfgtujghi5#", null).isValid());
	}
	
	@Test
	public void isValidatedWhenAtLeastOneUpperOneLowerOneDigitOneSpChar() {

		PasswordValidation v = new PasswordValidation();
		assertTrue(v.isValidInternal("UserAdmin@2013", null).isValid());
	}
	
	@Test
	public void isValidatedWhenAtLeastOneUpperOneLowerOneDigitNoSpChar() {

		PasswordValidation v = new PasswordValidation();
		assertTrue(v.isValidInternal("UserAdmin2013", null).isValid());
	}
}
