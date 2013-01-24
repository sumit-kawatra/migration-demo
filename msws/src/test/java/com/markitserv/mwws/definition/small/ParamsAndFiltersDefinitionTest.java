package com.markitserv.mwws.definition.small;

import static org.junit.Assert.*;

import org.junit.Test;

import com.markitserv.mwws.action.ActionCommand;
import com.markitserv.mwws.definition.ParamsAndFiltersDefinition;
import com.markitserv.mwws.testutil.AbstractMswsTest;
import com.markitserv.mwws.testutil.ActionAndActionCommandHelpers;
import com.markitserv.mwws.testutil.ActionAndActionCommandHelpers.TestActionCommandBuilder;
import com.markitserv.mwws.validation.OneOfValidation;
import com.markitserv.mwws.validation.RequiredValidation;

public class ParamsAndFiltersDefinitionTest extends AbstractMswsTest {

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
		fakeTestAction.performAction(cmd);

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
		fakeTestAction.performAction(cmd);
	}
}
