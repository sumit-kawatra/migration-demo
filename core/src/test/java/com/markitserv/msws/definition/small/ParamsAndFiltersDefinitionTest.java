package com.markitserv.msws.definition.small;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.markitserv.msws.action.internal.ActionCommand;
import com.markitserv.msws.definition.ParamsAndFiltersDefinition;
import com.markitserv.msws.testutil.AbstractMswsTest;
import com.markitserv.msws.testutil.FakeAction;
import com.markitserv.msws.validation.OneOfValidation;
import com.markitserv.msws.validation.RequiredValidation;

public class ParamsAndFiltersDefinitionTest extends AbstractMswsTest {
	
	FakeAction fakeTestAction;
	
	@Before
	public void setupEach() {
		fakeTestAction = new FakeAction();
	}

	@Test
	public void isAbleToMergeDefaultsWithExistingDefinition() {

		// Create param def to merge with ours
		ParamsAndFiltersDefinition other = new ParamsAndFiltersDefinition();

		other.addDefaultParam("Defaulted", "1");
		other.addValidation("Defaulted", new RequiredValidation());

		// create our param defs
		ParamsAndFiltersDefinition mine = new ParamsAndFiltersDefinition();
		mine.mergeWith(other);
		
		// set our merged params on the action
		fakeTestAction.setParameterDefinition(mine);
		
		// ensures that 'defaulted' is set by the mreged defaults
		String[] required = {"1"};
		fakeTestAction.addParameterValdiation("Defaulted", new OneOfValidation(required));
		
		// build command with no params
		ActionCommand cmd = this.actionCommandBuilder.build();
		fakeTestAction.internalPerformAction(cmd);

	}
	
	@Test
	public void doesNotOverrideDefaultsDuringAMerge() {
		
		// Create param def to merge with ours
		ParamsAndFiltersDefinition other = new ParamsAndFiltersDefinition();

		other.addDefaultParam("Defaulted", "1");
		other.addValidation("Defaulted", new RequiredValidation());

		// create our param defs
		ParamsAndFiltersDefinition mine = new ParamsAndFiltersDefinition();
		mine.mergeWith(other);
		
		// set our merged params on the action
		fakeTestAction.setParameterDefinition(mine);
		
		// ensures that 'defaulted' is set by the mreged defaults
		String[] required = {"1"};
		fakeTestAction.addParameterValdiation("Defaulted", new OneOfValidation(required));
		
		// build command with no params
		ActionCommand cmd = this.actionCommandBuilder.build();
		fakeTestAction.internalPerformAction(cmd);
	}
}
