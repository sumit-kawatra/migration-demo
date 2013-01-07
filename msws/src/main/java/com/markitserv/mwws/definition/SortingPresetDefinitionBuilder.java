package com.markitserv.mwws.definition;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.markitserv.mwws.action.CommonParamKeys;
import com.markitserv.mwws.action.SortOrder;
import com.markitserv.mwws.internal.MwwsAssert;
import com.markitserv.mwws.validation.CollectionSizesMatchValidation;
import com.markitserv.mwws.validation.CollectionSizeValidation;
import com.markitserv.mwws.validation.CollectionValidation;
import com.markitserv.mwws.validation.ForEachValidator;
import com.markitserv.mwws.validation.OneOfValidation;
import com.markitserv.mwws.validation.RequiredValidation;
import com.markitserv.mwws.validation.SortByValidation;

public class SortingPresetDefinitionBuilder {

	private ParamsAndFiltersDefinition def;
	private Stack<String> validSortByValues;

	private String defaultSortBy = null;
	private SortOrder defaultSortOrder = null;

	public SortingPresetDefinitionBuilder() {

		validSortByValues = new Stack<String>();

		def = new ParamsAndFiltersDefinition();

		def.addValidation(CommonParamKeys.SortBy, new RequiredValidation());
		def.addValidation(CommonParamKeys.SortBy, new CollectionValidation());
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

		MwwsAssert.mwwsAssert(
				defaultSortBy != null && defaultSortOrder != null,
				"If you're using sorting you need to specify a default sort");

		def.addValidation(CommonParamKeys.SortBy, new ForEachValidator(
				new SortByValidation(validSortByValues)));

		// ensure that they have the correct values and count for sort order
		String[] sortOrders = { SortOrder.Asc.toString(),
				SortOrder.Desc.toString() };
		def.addValidation(CommonParamKeys.SortOrder, new ForEachValidator(
				new OneOfValidation(sortOrders)));
		
		def.addValidation(CommonParamKeys.SortOrder,
				new CollectionSizesMatchValidation(CommonParamKeys.SortBy.toString()));

		// Add default sorts
		List<String> defaultSortByValues = new ArrayList<String>(1);
		defaultSortByValues.add(0, defaultSortBy);
		def.addDefaultParam(CommonParamKeys.SortBy, defaultSortByValues);

		List<String> defaultSortOrders = new ArrayList<String>(1);
		defaultSortOrders.add(0, defaultSortOrder.toString());
		def.addDefaultParam(CommonParamKeys.SortOrder, defaultSortOrders);

		return def;
	}
}
