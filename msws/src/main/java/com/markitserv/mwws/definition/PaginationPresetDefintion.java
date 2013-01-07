package com.markitserv.mwws.definition;

import com.markitserv.mwws.action.CommonParamKeys;
import com.markitserv.mwws.validation.IntegerMaxMinValidation;
import com.markitserv.mwws.validation.RequiredValidation;

public class PaginationPresetDefintion extends ParamsAndFiltersDefinition {

	public final int DEFAULT_PAGE_SIZE = 100;
	public final int DEFAULT_MAX_PAGE_SIZE = 100;

	public int pageSize;
	public int maxPageSize;

	public PaginationPresetDefintion() {
		
		this.pageSize = DEFAULT_PAGE_SIZE;
		this.maxPageSize = DEFAULT_MAX_PAGE_SIZE;
		initDefinition();
	}
	
	public PaginationPresetDefintion(int pageSize, int maxPageSize) {
		this.pageSize = pageSize;
		this.maxPageSize = maxPageSize;
		initDefinition();
	}

	private void initDefinition() {
		this.addValidation(CommonParamKeys.PageNumber.toString(),
				new RequiredValidation());
		this.addValidation(CommonParamKeys.PageNumber.toString(),
				new IntegerMaxMinValidation(1,
						IntegerMaxMinValidation.UNLIMITED));

		this.addValidation(CommonParamKeys.PageSize.toString(),
				new RequiredValidation());
		this.addValidation(CommonParamKeys.PageSize.toString(),
				new IntegerMaxMinValidation(1, maxPageSize));

		this.addDefaultParam(CommonParamKeys.PageNumber.toString(), "1");
		this.addDefaultParam(CommonParamKeys.PageSize.toString(),
				pageSize + "");
	}
}