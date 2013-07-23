package com.markitserv.msws.action;

import static com.markitserv.msws.internal.MswsAssert.mswsAssert;

import com.markitserv.msws.definition.ParamsAndFiltersDefinition;
import com.markitserv.msws.internal.Constants;
import com.markitserv.msws.validation.IntegerMaxMinValidation;
import com.markitserv.msws.validation.PaginationPageSizeValidation;
import com.markitserv.msws.validation.RequiredValidation;

public abstract class AbstractPaginatedAction extends AbstractAction {

	public final int DEFAULT_PAGE_SIZE = 100;
	public final int DEFAULT_MAX_PAGE_SIZE = 100;

	/**
	 * Overriden by the subclass if it doesn't want to use the defaults
	 * 
	 * @return
	 */
	protected int getDefaultPageSize() {
		return DEFAULT_PAGE_SIZE;
	}

	/**
	 * Overriden by the subclass if it doesn't want to use the defaults
	 * 
	 * @return
	 */
	protected int getMaxPageSize() {
		return DEFAULT_MAX_PAGE_SIZE;
	}

	@Override
	protected ParamsAndFiltersDefinition addAdditionalParameterDefinitions(
			ParamsAndFiltersDefinition def) {

		def.addValidation(CommonParamKeys.PARAM_PAGE_START_INDEX, new RequiredValidation());
		def.addValidation(CommonParamKeys.PARAM_PAGE_START_INDEX,
				new IntegerMaxMinValidation(1,
						IntegerMaxMinValidation.UNLIMITED));

		def.addValidation(CommonParamKeys.PARAM_PAGE_SIZE, new RequiredValidation());
		def.addValidation(CommonParamKeys.PARAM_PAGE_SIZE,
				new PaginationPageSizeValidation(this.getMaxPageSize()));

		def.addDefaultParam(CommonParamKeys.PARAM_PAGE_START_INDEX, "1");
		def.addDefaultParam(CommonParamKeys.PARAM_PAGE_SIZE,
				this.getDefaultPageSize() + "");

		return def;
	}
	
	@Override
	protected void validateResult(ActionResult result) {
		super.validateResult(result);

		mswsAssert(result instanceof PaginatedActionResult,
				"Paginated actions must return an instance of PaginatedActionResult.");

		PaginatedActionResult pRes = (PaginatedActionResult) result;
		PaginatedActionResponseMetaData metaData = pRes.getPaginatedMetaData();

		mswsAssert(result.getItem() == null,
				"Cannot set an 'item' on a paginated action.  Must set a 'list'");
		
		mswsAssert(metaData.getTotalRecords() != Constants.INTEGER_NOT_SET, "Total Records is requried.");
		
		mswsAssert(
				result.getItems().size() <= this.getMaxPageSize(),
				"List size (%d) cannot be greater than the max page size of %d",
				result.getItems().size(), this.getMaxPageSize());
	}
	
	/**
	 * Tacks the totalResults onto the response metadata
	 */
	@Override
	protected ActionResult postProcessResult(ActionResult result) {
		
		PaginatedActionResult pRes = (PaginatedActionResult) result;
		PaginatedActionResponseMetaData metaData = pRes.getPaginatedMetaData();
		metaData.setRequestRecords(pRes.getItems().size());
		result.setMetaData(metaData);
		return result;
		
	}
}