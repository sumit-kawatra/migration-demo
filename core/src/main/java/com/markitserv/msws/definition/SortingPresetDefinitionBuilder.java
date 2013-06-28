package com.markitserv.msws.definition;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.markitserv.msws.action.CommonParamKeys;
import com.markitserv.msws.action.SortOrder;
import com.markitserv.msws.internal.MswsAssert;
import com.markitserv.msws.validation.CollectionSizeValidation;
import com.markitserv.msws.validation.CollectionSizesMatchValidation;
import com.markitserv.msws.validation.CollectionValidation;
import com.markitserv.msws.validation.ForEachValidator;
import com.markitserv.msws.validation.OneOfValidation;
import com.markitserv.msws.validation.RequiredValidation;
import com.markitserv.msws.validation.SortByValidation;

public class SortingPresetDefinitionBuilder {

	private ParamsAndFiltersDefinition def;
	private Stack<String> validSortByValues;

	private String defaultSortBy = null;
	private SortOrder defaultSortOrder = null;

	public SortingPresetDefinitionBuilder() {

		validSortByValues = new Stack<String>();

		def = new ParamsAndFiltersDefinition();

		def.addValidation(CommonParamKeys.PARAM_SORT_BY, new RequiredValidation());
		def.addValidation(CommonParamKeys.PARAM_SORT_BY, new CollectionValidation());
	}

	public SortingPresetDefinitionBuilder addSortOption(String sortBy) {
		if (!validSortByValues.contains(sortBy)) {
			validSortByValues.push(sortBy);
		}
		return this;
	}

	/**
	 * Right now only one default sort order is supported.
	 * @param sortBy
	 * @param sortOrder
	 * @return
	 */
	public SortingPresetDefinitionBuilder setDefaultSort(String sortBy, SortOrder sortOrder) {
		defaultSortBy = sortBy;
		defaultSortOrder = sortOrder;
		
		
		addSortOption(sortBy);
		return this;
	}

	public ParamsAndFiltersDefinition build() {

		MswsAssert.mswsAssert(
				defaultSortBy != null && defaultSortOrder != null,
				"If you're using sorting you need to specify a default sort");

		def.addValidation(CommonParamKeys.PARAM_SORT_BY, new ForEachValidator(
				new SortByValidation(validSortByValues)));

		// ensure that they have the correct values and count for sort order
		String[] sortOrders = { SortOrder.Asc.toString(),
				SortOrder.Desc.toString() };
		def.addValidation(CommonParamKeys.PARAM_SORT_ORDER, new ForEachValidator(
				new OneOfValidation(sortOrders)));
		
		def.addValidation(CommonParamKeys.PARAM_SORT_ORDER,
				new CollectionSizesMatchValidation(CommonParamKeys.PARAM_SORT_BY.toString()));

		// Add default sorts
		List<String> defaultSortByValues = new ArrayList<String>(1);
		defaultSortByValues.add(0, defaultSortBy);
		def.addDefaultParam(CommonParamKeys.PARAM_SORT_BY, defaultSortByValues);

		List<String> defaultSortOrders = new ArrayList<String>(1);
		defaultSortOrders.add(0, defaultSortOrder.toString());
		def.addDefaultParam(CommonParamKeys.PARAM_SORT_ORDER, defaultSortOrders);

		return def;
	}
}
