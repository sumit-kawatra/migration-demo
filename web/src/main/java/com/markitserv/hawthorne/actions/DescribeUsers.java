/**
 * 
 */
package com.markitserv.hawthorne.actions;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.markitserv.hawthorne.HawthorneBackend;
import com.markitserv.hawthorne.types.User;
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
import com.markitserv.msws.filters.PropertyEqualsReflectionFilter;
import com.markitserv.msws.filters.SubstringReflectionFilter;
import com.markitserv.msws.validation.CollectionSizeValidation;
import com.markitserv.msws.validation.ForEachValidator;
import com.markitserv.msws.validation.IntegerMaxMinValidation;
import com.markitserv.msws.validation.IntegerValidation;
import com.markitserv.msws.validation.RequiredValidation;

/**
 * @author kiran.gogula
 * 
 */
public class DescribeUsers extends AbstractPaginatedAction {

	private static final String FILTER_NAME_SUBSTR_LAST_NAME = "substrLastName";

	Logger log = LoggerFactory.getLogger(DescribeUsers.class);

	@Autowired
	private HawthorneBackend data;

	@Override
	protected ParamsAndFiltersDefinition createParameterDefinition() {

		// Add validation
		ParamsAndFiltersDefinition def = new ParamsAndFiltersDefinition();

		// UserName
		def.addValidation("UserName", new ForEachValidator(new RequiredValidation()));
		// Only supporting a signle userName at a time (for now)
		def.addValidation("UserName", new CollectionSizeValidation(1, 1));
		
		// Participant ID
		def.addValidation("ParticipantId", new IntegerValidation());
		def.addValidation("ParticipantId", new IntegerMaxMinValidation(1, IntegerMaxMinValidation.UNLIMITED));
		def.addValidation("ParticipantId", new CollectionSizeValidation(1, 1));

		// Sorting
		SortingPresetDefinitionBuilder sortBuilder = new SortingPresetDefinitionBuilder();
		sortBuilder = sortBuilder.setDefaultSort("userId", SortOrder.Asc);

		def.mergeWith(sortBuilder.build());

		return def;
	}

	@Override
	protected ActionResult performAction(ActionParameters params,
			ActionFilters filters) {
		List<User> userList = data.getUsers();

		int totalRecords = userList.size();

		userList = applyFilters(params, filters, userList);

		PaginatedActionResult res = new PaginatedActionResult(userList);
		res.setTotalRecords(totalRecords);

		return res;
	}

	private List<User> applyFilters(ActionParameters p, ActionFilters f,
			List<User> users) {
		List<User> userList = new ArrayList<User>();

		if (p.isParameterSet("ParticipantId")) {
			List<String> participantIdList = (List<String>) p.getParameter("ParticipantId");
			for (String string : participantIdList) {
				userList.addAll(PropertyEqualsReflectionFilter.filter(
					users, "participantId", Integer.parseInt(string)));
			}
		}
		
		if (p.isParameterSet("UserName")) {
			List<String> userNameList = (List<String>) p.getParameter("UserName");
			for (String string : userNameList) {
				userList.addAll(PropertyEqualsReflectionFilter.filter(
					users, "userName", string));
			}
		}
		
		if (f.isFilterSet(FILTER_NAME_SUBSTR_LAST_NAME)) {
			userList.addAll(SubstringReflectionFilter.filter(
					users, "lastName",
					f.getSingleFilter(FILTER_NAME_SUBSTR_LAST_NAME)));
		}

		int pageNumber = p.getParameterAsInt(CommonParamKeys.PageNumber
				.toString());
		int pageSize = p.getParameterAsInt(CommonParamKeys.PageSize.toString());

		userList.addAll(PaginationFilter.filter(users,
				pageNumber, pageSize));
		return userList;
	}

}
