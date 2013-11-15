package com.markitserv.msws.action;

import java.util.ArrayList;

import java.util.List;
import java.util.Stack;

import com.markitserv.msws.util.MswsAssert;
import com.markitserv.msws.validation.ForEachValidator;
import com.markitserv.msws.validation.ListSizesMatchValidation;
import com.markitserv.msws.validation.ListValidation;
import com.markitserv.msws.validation.OneOfValidation;
import com.markitserv.msws.validation.RequiredValidation;
import com.markitserv.msws.validation.SortByValidation;

/**
 * Used to add sorting parameters to your action.
 * 
 * In your createParameterDefinition method of your action.
 * @author roy.truelove
 *
 */
public class SortingParamsDefinition {

	private ParamsAndFiltersDefinition def;
	private Stack<String> validSortByValues;

	private String defaultSortBy = null;
	private SortOrder defaultSortOrder = null;

	public SortingParamsDefinition(String sortBy, SortOrder sortOrder) {

		validSortByValues = new Stack<String>();

		def = new ParamsAndFiltersDefinition();

		def.addValidation(CommonParamKeys.PARAM_SORT_BY, new RequiredValidation());
		def.addValidation(CommonParamKeys.PARAM_SORT_BY, new ListValidation());
		
		this.setDefaultSort(sortBy, sortOrder);
	}

	public SortingParamsDefinition addSortOption(String sortBy) {
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
	public SortingParamsDefinition setDefaultSort(String sortBy, SortOrder sortOrder) {
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
				new ListSizesMatchValidation(CommonParamKeys.PARAM_SORT_BY.toString()));

		// Add default sorts
		List<String> defaultSortByValues = new ArrayList<String>(1);
		defaultSortByValues.add(0, defaultSortBy);
		def.addDefaultParamValue(CommonParamKeys.PARAM_SORT_BY, defaultSortByValues);

		List<String> defaultSortOrders = new ArrayList<String>(1);
		defaultSortOrders.add(0, defaultSortOrder.toString());
		def.addDefaultParamValue(CommonParamKeys.PARAM_SORT_ORDER, defaultSortOrders);

		return def;
	}
}
