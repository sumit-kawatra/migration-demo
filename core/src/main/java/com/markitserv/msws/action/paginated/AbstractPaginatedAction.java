package com.markitserv.msws.action.paginated;

import static com.markitserv.msws.util.MswsAssert.mswsAssert;

import java.util.Stack;

import com.markitserv.msws.action.AbstractAction;
import com.markitserv.msws.action.ActionCommand;
import com.markitserv.msws.action.CommonParamKeys;
import com.markitserv.msws.action.ParamsAndFiltersDefinition;
import com.markitserv.msws.action.resp.ActionResult;
import com.markitserv.msws.internal.Constants;
import com.markitserv.msws.internal.action.paginated.PaginatedActionResponseMetaData;
import com.markitserv.msws.validation.IntegerMaxMinValidation;
import com.markitserv.msws.validation.PaginationPageSizeValidation;
import com.markitserv.msws.validation.RequiredValidation;

/**
 * Actions that return paginated results should subclass this class.
 * 
 * Two parameters are included on the request by default. These can be
 * overridden by the caller:
 * 
 * PageStartIndex: The starting page index. Is 1 by default PageSize: The size
 * of the page that should be returned. If not provided by the caller, the
 * default value is provided by the getDefaultPageSize() method, which can be
 * overridden by your subclass. By default, this is set to 100.
 * 
 * Your subclass can also override the getMaxPageSize() to specify the maximum
 * amount of items the caller can request at once. Also set to 100.
 * 
 * It is up to the caller to ensure that the returned list size is equal to or
 * lower than the value provided by the PageSize.
 * 
 * If you need to paginate manually (eg it's not happening on the database) you
 * can use the PaginationFilter class
 * 
 * @author roy.truelove
 * 
 */
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

		def.addValidation(CommonParamKeys.PARAM_PAGE_START_INDEX,
				new RequiredValidation());
		def.addValidation(CommonParamKeys.PARAM_PAGE_START_INDEX,
				new IntegerMaxMinValidation(1,
						IntegerMaxMinValidation.UNLIMITED));

		def.addValidation(CommonParamKeys.PARAM_PAGE_SIZE,
				new RequiredValidation());
		def.addValidation(CommonParamKeys.PARAM_PAGE_SIZE,
				new PaginationPageSizeValidation(this.getMaxPageSize()));

		def.addDefaultParamValue(CommonParamKeys.PARAM_PAGE_START_INDEX, "1");
		def.addDefaultParamValue(CommonParamKeys.PARAM_PAGE_SIZE,
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

		mswsAssert(metaData.getTotalRecords() != Constants.INTEGER_NOT_SET,
				"Total Records is requried.");

		mswsAssert(result.getItems().size() <= this.getMaxPageSize(),
				"Action needs to ensure that list size (%d) is not "
						+ "greater than the max page size of %d", result
						.getItems().size(), this.getMaxPageSize());
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

	protected abstract PaginatedActionResult performPaginatedAction(
			ActionCommand command);

	@Override
	protected ActionResult performAction(ActionCommand command) {
		return this.performPaginatedAction(command);
	}

}