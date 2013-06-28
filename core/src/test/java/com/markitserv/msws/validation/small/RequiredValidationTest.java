package com.markitserv.msws.validation.small;

import static org.junit.Assert.*;

import org.junit.Test;

import com.markitserv.msws.action.ActionCommand;
import com.markitserv.msws.exceptions.ValidationException;
import com.markitserv.msws.testutil.AbstractMswsTest;
import com.markitserv.msws.validation.RequiredValidation;

public class RequiredValidationTest extends AbstractMswsTest {

	@Test
	public void succeedsIfRequiredParamProvided() {
		RequiredValidation v = new RequiredValidation();
		assertTrue(v.validateInternal("notNull", null).isValid());
	}

	@Test
	public void failsIfRequiredParamNotProvided() {
		
		RequiredValidation v = new RequiredValidation();
		assertFalse(v.validateInternal(null, null).isValid());
	}
}
