package com.markitserv.msws.validation.small;

import static org.junit.Assert.*;

import org.junit.Test;

import com.markitserv.msws.testutil.AbstractMswsTest;
import com.markitserv.msws.validation.PasswordValidation;

public class PasswordValidationTest extends AbstractMswsTest {
	
	@Test
	public void isNotValidatedWhenLenghLessThanMin() {

		PasswordValidation v = new PasswordValidation();
		assertFalse(v.validateInternal("Abcd1", null).isValid());
	}
	
	@Test
	public void isNotValidatedWhenLenghGreaterThanMax() {

		PasswordValidation v = new PasswordValidation();
		assertFalse(v.validateInternal("Abcdefghijklmnop1", null).isValid());
	}
	
	@Test
	public void isNotValidatedWhenContainsWhiteSpaces() {

		PasswordValidation v = new PasswordValidation();
		assertFalse(v.validateInternal("Abcde f1", null).isValid());
	}
	
	@Test
	public void isNotValidatedWhenNoUpperCaseAlphabet() {

		PasswordValidation v = new PasswordValidation();
		assertFalse(v.validateInternal("abcdefgh1", null).isValid());
	}
	
	@Test
	public void isNotValidatedWhenNoLowerCaseAlphabet() {

		PasswordValidation v = new PasswordValidation();
		assertFalse(v.validateInternal("ABCDEFGHIJ1", null).isValid());
	}
	
	@Test
	public void isNotValidatedWhenNoDigit() {

		PasswordValidation v = new PasswordValidation();
		assertFalse(v.validateInternal("aBCDEFGHIJ", null).isValid());
	}
	
	@Test
	public void isValidatedWhenAtLeastOneDigit() {

		PasswordValidation v = new PasswordValidation();
		assertTrue(v.validateInternal("aBCDEFGHIJ1", null).isValid());
	}
	
	@Test
	public void isValidatedWhenAtLeastOneUpperCaseAlphabet() {

		PasswordValidation v = new PasswordValidation();
		assertTrue(v.validateInternal("Abcdefhj1", null).isValid());
	}
	
	@Test
	public void isValidatedWhenAtLeastOneLowerCaseAlphabet() {

		PasswordValidation v = new PasswordValidation();
		assertTrue(v.validateInternal("aBDFHJGFDDFJ9", null).isValid());
	}
	
	@Test
	public void isValidatedWhenOneOrMoreDigits() {

		PasswordValidation v = new PasswordValidation();
		assertTrue(v.validateInternal("aB9564545645645", null).isValid());
	}
	
	@Test
	public void isValidatedWhenOneOrMoreUpperCaseAlphabets() {

		PasswordValidation v = new PasswordValidation();
		assertTrue(v.validateInternal("aBUHGTHREGDF8", null).isValid());
	}
	
	@Test
	public void isValidatedWhenOneOrMoreLowerCaseAlphabets() {

		PasswordValidation v = new PasswordValidation();
		assertTrue(v.validateInternal("Mfgtujghi5", null).isValid());
	}
	
	@Test
	public void isValidatedWhenContainsSpecialChar() {

		PasswordValidation v = new PasswordValidation();
		assertTrue(v.validateInternal("Mfgtujghi5#", null).isValid());
	}
	
	@Test
	public void isValidatedWhenAtLeastOneUpperOneLowerOneDigitOneSpChar() {

		PasswordValidation v = new PasswordValidation();
		assertTrue(v.validateInternal("UserAdmin@2013", null).isValid());
	}
	
	@Test
	public void isValidatedWhenAtLeastOneUpperOneLowerOneDigitNoSpChar() {

		PasswordValidation v = new PasswordValidation();
		assertTrue(v.validateInternal("UserAdmin2013", null).isValid());
	}
}
