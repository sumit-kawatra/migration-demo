package com.markitserv.hawthorne.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markitserv.hawthorne.HawthorneBackend;
import com.markitserv.hawthorne.types.Participant;
import com.markitserv.hawthorne.types.Product;
import com.markitserv.hawthorne.util.HawthorneParamsAndFilters;
import com.markitserv.msws.action.AbstractPaginatedAction;
import com.markitserv.msws.action.ActionFilters;
import com.markitserv.msws.action.ActionParameters;
import com.markitserv.msws.action.ActionResult;
import com.markitserv.msws.action.CommonParamKeys;
import com.markitserv.msws.action.PaginatedActionResult;
import com.markitserv.msws.action.SortOrder;
import com.markitserv.msws.definition.ParamsAndFiltersDefinition;
import com.markitserv.msws.definition.SortingPresetDefinitionBuilder;
import com.markitserv.msws.filters.PaginationFilter;
import com.markitserv.msws.validation.IntegerValidationAndConversion;
import com.markitserv.msws.validation.OptionalValidation;

/**
 * Describe Products
 * 
 * @author swati.choudhari
 * 
 */
@Service
public class DescribeProducts extends AbstractPaginatedAction {

	Logger log = LoggerFactory.getLogger(DescribeProducts.class);

	@Autowired
	private HawthorneBackend data;

	@Override
	protected ParamsAndFiltersDefinition createParameterDefinition() {

		ParamsAndFiltersDefinition def = super.createParameterDefinition();

		// Participant Id
		def.addValidation(HawthorneParamsAndFilters.PARAM_PARTICIPANT_ID,
				new OptionalValidation());
		def.addValidationAndConversion(HawthorneParamsAndFilters.PARAM_PARTICIPANT_ID,
				new IntegerValidationAndConversion());

		// Sorting
		SortingPresetDefinitionBuilder sortBuilder = new SortingPresetDefinitionBuilder();
		sortBuilder = sortBuilder.addSortOption("name");
		sortBuilder = sortBuilder.setDefaultSort("name", SortOrder.Asc);

		def.mergeWith(sortBuilder.build());

		return def;
	}

	@Override
	protected ActionResult performAction(ActionParameters params, ActionFilters filters) {

		Set<Product> productSet;

		if (params.isParameterSet(HawthorneParamsAndFilters.PARAM_PARTICIPANT_ID)) {
			int participantId = params.getParameter(
					HawthorneParamsAndFilters.PARAM_PARTICIPANT_ID, Integer.class);

			Participant participant = data.getParticipant(participantId);
			productSet = participant.getProducts();
		} else {
			// get all products in the system
			productSet = data.getProducts();
		}

		List<Product> products = new ArrayList<Product>(productSet);

		int totalSize = products.size();

		sortByName(products);

		products = paginate(params, products);

		PaginatedActionResult res = new PaginatedActionResult(products);
		res.getPaginatedMetaData().setTotalRecords(totalSize);
		return res;
	}

	/**
	 * NOTE right now this is hardcoded.. needs to be abstracted out if we ever
	 * have to sort by anything else other than name.
	 * 
	 * @param products
	 */
	private void sortByName(List<Product> products) {
		Comparator<Product> byName = new Comparator<Product>() {
			public int compare(Product o1, Product o2) {
				return o1.getName().compareTo(o2.getName());
			}
		};

		Collections.sort(products, byName);
	}

	private List<Product> paginate(ActionParameters params, List<Product> products) {
		// Add pagination
		int pageStartIndex = params.getParameter(CommonParamKeys.PageStartIndex.toString(),
				Integer.class);
		int pageSize = params.getParameter(CommonParamKeys.PageSize.toString(),
				Integer.class);
		products = PaginationFilter.filter(products, pageStartIndex, pageSize);

		return products;
	}
}
