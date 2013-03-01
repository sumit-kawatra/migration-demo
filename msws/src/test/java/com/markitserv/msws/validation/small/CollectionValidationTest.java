package com.markitserv.msws.validation.small;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.markitserv.msws.action.ActionCommand;
import com.markitserv.msws.exceptions.ValidationException;
import com.markitserv.msws.testutil.AbstractMswsTest;
import com.markitserv.msws.testutil.ActionAndActionCommandHelpers.TestAction;
import com.markitserv.msws.testutil.ActionAndActionCommandHelpers.TestActionCommandBuilder;
import com.markitserv.msws.validation.CollectionValidation;

public class CollectionValidationTest extends AbstractMswsTest {
	
	@Test
	public void succeedsIfCollectionIsProvided() {
		
		CollectionValidation v = new CollectionValidation();
		ArrayList<String> col = new ArrayList<String>();
		
		assertTrue(v.validateInternal(col, null).isValid());
	}
	
	@Test
	public void failsIfParamNotCollection() {
		CollectionValidation v = new CollectionValidation();
		assertFalse(v.validateInternal("NotACollection", null).isValid());
	}
}