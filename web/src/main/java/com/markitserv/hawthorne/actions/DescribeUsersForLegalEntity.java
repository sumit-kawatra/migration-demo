package com.markitserv.hawthorne.actions;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markitserv.hawthorne.HawthorneBackend;
import com.markitserv.hawthorne.types.User;
import com.markitserv.mwws.action.AbstractPaginatedAction;
import com.markitserv.mwws.action.ActionFilters;
import com.markitserv.mwws.action.ActionParameters;
import com.markitserv.mwws.action.ActionResult;
import com.markitserv.mwws.action.CommonParamKeys;
import com.markitserv.mwws.action.PaginatedActionResult;
import com.markitserv.mwws.action.SortOrder;
import com.markitserv.mwws.definition.ParamsAndFiltersDefinition;
import com.markitserv.mwws.definition.SortingPresetDefinitionBuilder;
import com.markitserv.mwws.filters.PaginationFilter;
import com.markitserv.mwws.filters.PropertyEqualsReflectionFilter;
import com.markitserv.mwws.filters.SubstringReflectionFilter;
import com.markitserv.mwws.validation.CollectionSizeValidation;
import com.markitserv.mwws.validation.ForEachValidator;
import com.markitserv.mwws.validation.IntegerMaxMinValidation;
import com.markitserv.mwws.validation.IntegerValidation;

/**
 * @author kiran.gogula
 *
 */
@Service
public class DescribeUsersForLegalEntity extends AbstractPaginatedAction {

	private static final String FILTER_NAME_SUBSTR_FIRST_NAME = "substrFirstName";
	private static final String FILTER_NAME_SUBSTR_LAST_NAME = "substrLastName";
	private static final String FILTER_NAME_SUBSTR_USER_NAME = "substrUserName";
	
	@Autowired
	private HawthorneBackend data;

	@Override
	protected ParamsAndFiltersDefinition createParameterDefinition() {

		// Add validation
		ParamsAndFiltersDefinition def = new ParamsAndFiltersDefinition();

		
		// Legal Entity ID
		def.addValidation("LegalEntityId", new ForEachValidator(
				new IntegerValidation()));
		def.addValidation("LegalEntityId", new ForEachValidator(
				new IntegerMaxMinValidation(1,
						IntegerMaxMinValidation.UNLIMITED)));
		// Only supporting a signle legal entity ID at a time (for now)
		def.addValidation("LegalEntityId", new CollectionSizeValidation(1, 1));

		// Sorting
		SortingPresetDefinitionBuilder sortBuilder = new SortingPresetDefinitionBuilder();
		sortBuilder = sortBuilder.setDefaultSort("userId", SortOrder.Asc);		

		def.mergeWith(sortBuilder.build());

		return def;
	}

	
	
	
	@Override
	protected ActionResult performAction(ActionParameters params,
			ActionFilters filters) {

		List<User> legalEntityUsers = data.getUsersForLegalEntity();

		int totalRecords = legalEntityUsers.size();

		legalEntityUsers = applyFilters(params, filters, legalEntityUsers);

		PaginatedActionResult res = new PaginatedActionResult(legalEntityUsers);
		res.setTotalRecords(totalRecords);

		return res;
	}

	
	private List<User> applyFilters(ActionParameters p, ActionFilters f,
			List<User> legalEntityUsers) {
		
		// Note that of course we don't do this in reality but until we get the
		// DB it'll do.  Filters LE's down to a single LE by ID.
		if (p.isParameterSet("LegalEntityId")) {
			List<String> leId = (List<String>) p.getParameter("LegalEntityId");
			legalEntityUsers = PropertyEqualsReflectionFilter.filter(
					legalEntityUsers, "legalEntityId", Integer.parseInt((String) leId.get(0)));
		}
		
		if (f.isFilterSet(FILTER_NAME_SUBSTR_FIRST_NAME)) {

			legalEntityUsers = SubstringReflectionFilter.filter(legalEntityUsers,
					"firstName", f.getSingleFilter(FILTER_NAME_SUBSTR_FIRST_NAME));
		}

		if (f.isFilterSet(FILTER_NAME_SUBSTR_LAST_NAME)) {

			legalEntityUsers = SubstringReflectionFilter.filter(legalEntityUsers,
					"lastName", f.getSingleFilter(FILTER_NAME_SUBSTR_LAST_NAME));
		}
		
		if (f.isFilterSet(FILTER_NAME_SUBSTR_USER_NAME)) {

			legalEntityUsers = SubstringReflectionFilter.filter(legalEntityUsers,
					"userName", f.getSingleFilter(FILTER_NAME_SUBSTR_USER_NAME));
		}

		int pageNumber = p.getParameterAsInt(CommonParamKeys.PageNumber
				.toString());
		int pageSize = p.getParameterAsInt(CommonParamKeys.PageSize.toString());

		legalEntityUsers = PaginationFilter.filter(legalEntityUsers, pageNumber,
				pageSize);
		return legalEntityUsers;
	}
	
	
	
	

}
