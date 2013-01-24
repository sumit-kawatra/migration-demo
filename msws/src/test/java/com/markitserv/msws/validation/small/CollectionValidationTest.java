package com.markitserv.msws.validation.small;

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
		
		ArrayList<String> col = new ArrayList<String>();
		
		ActionCommand cmd = this.actionCommandBuilder.addParam("Collection", col).build();
		fakeTestAction.addParameterValdiation("Collection", new CollectionValidation());
		fakeTestAction.performAction(cmd);
	}
	
	@Test(expected = ValidationException.class)
	public void failsIfParamNotCollection() {
		
		ActionCommand cmd = this.actionCommandBuilder.addParam("Collection", "col").build();
		fakeTestAction.addParameterValdiation("Collection", new CollectionValidation());
		fakeTestAction.performAction(cmd);
	}
}
