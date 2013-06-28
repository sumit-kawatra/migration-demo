package com.markitserv.msws.validation.small;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import com.markitserv.msws.action.ActionCommand;
import com.markitserv.msws.testutil.AbstractMswsTest;
import com.markitserv.msws.validation.OneOfValidation;

public class OneOfValidationTest extends AbstractMswsTest {

	@Test
	public void isValidatedIfOneOfValuesIsProvided() {
		
		OneOfValidation v = new OneOfValidation(new String[] {"bar", "foo", "baz"});
		assertTrue(v.validateInternal("foo", null).isValid());
	}

	public void isNotValidatedIfValueIsNotInListOfOneOfValues() {
		OneOfValidation v = new OneOfValidation(new String[] {"bar", "goo", "baz"});
		assertFalse(v.validateInternal("foo", null).isValid());
		
	}
}
