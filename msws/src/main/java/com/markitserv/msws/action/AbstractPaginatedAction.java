package com.markitserv.msws.action;

import static com.markitserv.msws.internal.MswsAssert.mswsAssert;

import com.markitserv.msws.ResponseMetadata;
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

		def.addValidation(CommonParamKeys.PageNumber, new RequiredValidation());
		def.addValidation(CommonParamKeys.PageNumber,
				new IntegerMaxMinValidation(1,
						IntegerMaxMinValidation.UNLIMITED));

		def.addValidation(CommonParamKeys.PageSize, new RequiredValidation());
		def.addValidation(CommonParamKeys.PageSize,
				new PaginationPageSizeValidation(this.getMaxPageSize()));

		def.addDefaultParam(CommonParamKeys.PageNumber.toString(), "1");
		def.addDefaultParam(CommonParamKeys.PageSize.toString(),
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

		mswsAssert(
				metaData.getApproxTotalRecords() != Constants.INTEGER_NOT_SET
						|| metaData.getTotalRecords() != Constants.INTEGER_NOT_SET,
				"Either totalRecords or approxTotalRecords must be set.");

		mswsAssert(
				!(metaData.getApproxTotalRecords() != Constants.INTEGER_NOT_SET && metaData
						.getTotalRecords() != Constants.INTEGER_NOT_SET),
				"Either totalRecords or approxTotalRecords must be set, but not both.");

		mswsAssert(result.getItem() == null,
				"Cannot set an 'item' on a paginated action.  Must set a 'list'");

		mswsAssert(
				result.getList().size() <= this.getMaxPageSize(),
				"List size (%d) cannot be greater than the max page size of %d",
				result.getList().size(), this.getMaxPageSize());
	}
}