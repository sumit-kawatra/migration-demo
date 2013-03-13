package com.markitserv.msws.testutil;

import java.util.Stack;

import com.markitserv.msws.action.AbstractAction;
import com.markitserv.msws.action.AbstractPaginatedAction;
import com.markitserv.msws.action.ActionFilters;
import com.markitserv.msws.action.ActionParameters;
import com.markitserv.msws.action.ActionResult;
import com.markitserv.msws.action.PaginatedActionResponseMetaData;
import com.markitserv.msws.action.PaginatedActionResult;
import com.markitserv.msws.definition.ParamsAndFiltersDefinition;
import com.markitserv.msws.validation.AbstractValidation;
import com.markitserv.msws.validation.IntegerValidationAndConversion;
import com.markitserv.msws.validation.RequiredValidation;

/**
 * Simple fake paginated action, used for testing.  Note that this might 
 * not be useful to expose - might want to move it to the test pacakges
 * @author roy.truelove
 *
 */
public class FakePaginatedAction extends AbstractPaginatedAction {

	private ParamsAndFiltersDefinition paramDef;
	private ParamsAndFiltersDefinition filterDef;
	private boolean dontSetTotalSize = false;
	private boolean dontSetApproxSize = true;

	@Override
	protected ActionResult performAction(ActionParameters params,
			ActionFilters filters) {
		
		int size = params.getParameter("Size", Integer.class);
		
		Stack<FakeType> payload = new Stack<FakeType>();
		
		for(int i=0; i < size; i++) {
			payload.push(new FakeType(true));
		}
		
		PaginatedActionResult res = new PaginatedActionResult(payload);
		PaginatedActionResponseMetaData metaData = res.getPaginatedMetaData();
		
		if (!dontSetTotalSize) {
			metaData.setTotalRecords(payload.size());
		}
		
		return res;
	}

	public void addParameterValdiation(String key, AbstractValidation value) {
		createParameterDefinition().addValidation(key, value);
	}

	public void addFilterValdiation(String key, AbstractValidation value) {
		createFilterDefinition().addValidation(key, value);
	}

	@Override
	protected ParamsAndFiltersDefinition createParameterDefinition() {
		
		if (paramDef == null) {
			
			paramDef = super.createParameterDefinition();
			
			paramDef.addValidation("Size", new IntegerValidationAndConversion());
			paramDef.addValidation("Size", new RequiredValidation());
		}
		return paramDef;
	}

	@Override
	protected ParamsAndFiltersDefinition createFilterDefinition() {
		
		if (filterDef == null) {
			filterDef = super.createFilterDefinition();
		}
		
		return filterDef;
	
	}

	public void addParameterDefault(String key, String value) {
		this.createParameterDefinition().addDefaultParam(key, value);
	}
	
	public void setParameterDefinition(ParamsAndFiltersDefinition def) {
		this.paramDef = def;
	}
	
	public void setFilterDefinition(ParamsAndFiltersDefinition def) {
		this.filterDef = def;
	}

	public void dontSetTotalSize(boolean b) {
		this.dontSetTotalSize = true;
	}
}