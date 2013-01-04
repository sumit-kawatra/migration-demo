package com.markitserv.mwws.validation.small;

import java.util.ArrayList;

import org.junit.Test;

import com.markitserv.mwws.action.ActionCommand;
import com.markitserv.mwws.exceptions.ValidationException;
import com.markitserv.mwws.validation.CollectionValidation;

public class CollectionValidationTest extends AbstractValidationTest {
	
	@Test
	public void succeedsIfCollectionIsProvided() {
		
		ArrayList<String> col = new ArrayList<String>();
		
		ActionCommand cmd = cmdBuilder.addParam("Collection", col).build();
		action.addParameterValdiation("Collection", new CollectionValidation());
		action.performAction(cmd);
	}
	
	@Test(expected = ValidationException.class)
	public void failsIfParamNotCollection() {
		
		ActionCommand cmd = cmdBuilder.addParam("Collection", "col").build();
		action.addParameterValdiation("Collection", new CollectionValidation());
		action.performAction(cmd);
	}
}
