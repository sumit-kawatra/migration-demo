package com.markitserv.mwws.action;

import com.markitserv.mwws.Util.Constants;
import com.markitserv.mwws.definition.ParamsAndFiltersDefinition;
import static com.markitserv.mwws.internal.MwwsAssert.mwwsAssert;
import com.markitserv.mwws.validation.IntegerMaxMinValidation;
import com.markitserv.mwws.validation.PaginationPageSizeValidation;
import com.markitserv.mwws.validation.RequiredValidation;

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

		mwwsAssert(result instanceof PaginatedActionResult,
				"Paginated actions must return an instance of PaginatedActionResult.");

		PaginatedActionResult pRes = (PaginatedActionResult) result;

		mwwsAssert(pRes.getApproxTotalRecords() != Constants.INTEGER_NOT_SET
				|| pRes.getTotalRecords() != Constants.INTEGER_NOT_SET,
				"Either totalRecords or approxTotalRecords must be set.");

		mwwsAssert(
				!(pRes.getApproxTotalRecords() != Constants.INTEGER_NOT_SET && pRes
						.getTotalRecords() != Constants.INTEGER_NOT_SET),
				"Either totalRecords or approxTotalRecords must be set, but not both.");

		mwwsAssert(result.getItem() == null,
				"Cannot set an 'item' on a paginated action.  Must set a 'list'");

		mwwsAssert(
				result.getList().size() <= this.getMaxPageSize(),
				"List size (%d) cannot be greater than the max page size of %d",
				result.getList().size(), this.getMaxPageSize());
	}

	/**
	 * Adds the pagination information
	 */
	@Override
	protected ActionResult addResponseMetadata(ActionResult result) {

		PaginatedActionResult pResult = (PaginatedActionResult) result;
		ActionResponseMetadata metaD = pResult.getMetadata();
		metaD.setApproxTotalRecords(pResult.getApproxTotalRecords());
		metaD.setTotalRecords(pResult.getTotalRecords());

		result = super.addResponseMetadata(pResult);

		return result;
	}
}