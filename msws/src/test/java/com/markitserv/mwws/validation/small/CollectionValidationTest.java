package com.markitserv.mwws.validation.small;

import java.util.ArrayList;

import org.junit.Test;

import com.markitserv.mwws.action.ActionCommand;
import com.markitserv.mwws.exceptions.ValidationException;
import com.markitserv.mwws.testutil.AbstractMswsTest;
import com.markitserv.mwws.testutil.ActionAndActionCommandHelpers.TestAction;
import com.markitserv.mwws.testutil.ActionAndActionCommandHelpers.TestActionCommandBuilder;
import com.markitserv.mwws.validation.CollectionValidation;

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
