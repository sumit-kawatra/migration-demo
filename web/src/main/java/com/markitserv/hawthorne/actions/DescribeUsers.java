/**
 * 
 */
package com.markitserv.hawthorne.actions;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.markitserv.msws.filters.SubstringReflectionFilter;
import com.markitserv.msws.validation.CollectionSizeValidation;
import com.markitserv.msws.validation.ForEachValidator;
import com.markitserv.msws.validation.ForEachValidatorAndConverter;
import com.markitserv.msws.validation.IntegerMaxMinValidationAndConversion;
import com.markitserv.msws.validation.IntegerValidationAndConversion;
import com.markitserv.msws.validation.MutuallyExclusiveWithValidation;
import com.markitserv.msws.validation.RequiredIfAllNotProvidedValidation;
import com.markitserv.msws.validation.RequiredValidation;

/**
 * @author kiran.gogula
 * 
 */
@Service
public class DescribeUsers extends AbstractPaginatedAction {

	private static final String FILTER_NAME_SUBSTR_LAST_NAME = "substrLastName";
	private static final String FILTER_NAME_SUBSTR_FIRST_NAME = "substrFirstName";
	private static final String FILTER_NAME_SUBSTR_USER_NAME = "substrUserName";
	private static final String PARAM_NAME_USER_NAME = "UserName";
	private static final String PARAM_PARTICIPANT_ID = "ParticipantId";
	private static final String PARAM_NAME_LEGAL_ENTITY_ID = "LegalEntityId";

	Logger log = LoggerFactory.getLogger(DescribeUsers.class);

	@Autowired
	private HawthorneBackend data;

	@Override
	protected ParamsAndFiltersDefinition createParameterDefinition() {

		// Add validation
		ParamsAndFiltersDefinition def = new ParamsAndFiltersDefinition();

		// UserName
		def.addValidation(PARAM_NAME_USER_NAME, new ForEachValidator(
				new RequiredValidation()));
		// Only supporting a signle userName at a time (for now)
		def.addValidation(PARAM_NAME_USER_NAME, new CollectionSizeValidation(1, 1));
		// If UserName is provided ParticipantId & LegalEntityId are not required
		def.addValidation(PARAM_NAME_USER_NAME, new MutuallyExclusiveWithValidation(
				new String[] {
						PARAM_PARTICIPANT_ID, PARAM_NAME_LEGAL_ENTITY_ID
				}));
		// UserName is required when neither ParticipantId nor LegalEntityId is
		// provided
		def.addValidation(PARAM_NAME_USER_NAME, new RequiredIfAllNotProvidedValidation(
				new String[] {
						PARAM_PARTICIPANT_ID, PARAM_NAME_LEGAL_ENTITY_ID
				}));

		// Participant ID

		def.addValidationAndConversion(PARAM_PARTICIPANT_ID,
				new ForEachValidatorAndConverter(new IntegerValidationAndConversion()));

		def.addValidationAndConversion(PARAM_PARTICIPANT_ID,
				new ForEachValidatorAndConverter(new IntegerMaxMinValidationAndConversion(1,
						IntegerMaxMinValidationAndConversion.UNLIMITED)));

		// Only supporting a single legal entity ID at a time (for now)
		def.addValidation(PARAM_PARTICIPANT_ID, new CollectionSizeValidation(1, 1));

		// If ParticipantId is provided UserName & LegalEntityId are not required
		def.addValidation(PARAM_PARTICIPANT_ID, new MutuallyExclusiveWithValidation(
				new String[] {
						PARAM_NAME_USER_NAME, PARAM_NAME_LEGAL_ENTITY_ID
				}));

		// ParticipantId is required when neither UserName nor LegalEntityId is
		// provided
		def.addValidation(PARAM_PARTICIPANT_ID, new RequiredIfAllNotProvidedValidation(
				new String[] {
						PARAM_NAME_USER_NAME, PARAM_NAME_LEGAL_ENTITY_ID
				}));

		// Legal Entity ID
		def.addValidation(PARAM_NAME_LEGAL_ENTITY_ID, new ForEachValidator(
				new IntegerValidationAndConversion()));
		def.addValidationAndConversion(PARAM_NAME_LEGAL_ENTITY_ID,
				new ForEachValidatorAndConverter(new IntegerMaxMinValidationAndConversion(1,
						IntegerMaxMinValidationAndConversion.UNLIMITED)));
		// Only supporting a single legal entity ID at a time (for now)
		def.addValidation(PARAM_NAME_LEGAL_ENTITY_ID, new CollectionSizeValidation(1, 1));

		// If LegalEntityId is provided UserName & ParticipantId are not required
		def.addValidation(PARAM_NAME_LEGAL_ENTITY_ID, new MutuallyExclusiveWithValidation(
				new String[] {
						PARAM_NAME_USER_NAME, PARAM_PARTICIPANT_ID
				}));
		// LegalEntityId is required when neither UserName nor ParticipantId is
		// provided
		def.addValidation(PARAM_NAME_LEGAL_ENTITY_ID,
				new RequiredIfAllNotProvidedValidation(new String[] {
						PARAM_NAME_USER_NAME, PARAM_PARTICIPANT_ID
				}));

		// Sorting
		SortingPresetDefinitionBuilder sortBuilder = new SortingPresetDefinitionBuilder();
		sortBuilder = sortBuilder.setDefaultSort("userId", SortOrder.Asc);

		def.mergeWith(sortBuilder.build());

		return def;
	}

	@Override
	protected ParamsAndFiltersDefinition createFilterDefinition() {
		ParamsAndFiltersDefinition def = new ParamsAndFiltersDefinition();

		def.addValidation(FILTER_NAME_SUBSTR_LAST_NAME, new CollectionSizeValidation(
				CollectionSizeValidation.UNLIMITED, 1));

		def.addValidation(FILTER_NAME_SUBSTR_FIRST_NAME, new CollectionSizeValidation(
				CollectionSizeValidation.UNLIMITED, 1));

		def.addValidation(FILTER_NAME_SUBSTR_USER_NAME, new CollectionSizeValidation(
				CollectionSizeValidation.UNLIMITED, 1));

		return def;
	}

	@Override
	protected ActionResult performAction(ActionParameters params, ActionFilters filters) {
		List<User> userList = data.getAllUsers();
		int totalRecords = userList.size();
		userList = applyFilters(params, filters, userList);
		PaginatedActionResult res = new PaginatedActionResult(userList);
		res.getPaginatedMetaData().setTotalRecords(totalRecords);
		return res;
	}

	private List<User> applyFilters(ActionParameters p, ActionFilters f, List<User> users) {
		List<User> userList = new ArrayList<User>();

		if (p.isParameterSet(PARAM_PARTICIPANT_ID)) {
			List<Integer> participantIdList = p.getParameter(PARAM_PARTICIPANT_ID,
					List.class);
			for (Integer id : participantIdList) {
				userList = data.getUsersForParticipant(id);
			}
		} else if (p.isParameterSet(PARAM_NAME_USER_NAME)) {
			List<String> userNameList = p.getParameter(PARAM_NAME_USER_NAME, List.class);
			for (String string : userNameList) {
				data.getUser(string);
				userList = data.getUser(string);
			}
		} else if (p.isParameterSet(PARAM_NAME_LEGAL_ENTITY_ID)) {
			List<String> legalEntityIdList = (List<String>) p.getParameter(
					PARAM_NAME_LEGAL_ENTITY_ID, List.class);
			for (String id : legalEntityIdList) {
				userList = data.getUsersForLegalEntity(Integer.parseInt(id));
			}
		}

		if (f.isFilterSet(FILTER_NAME_SUBSTR_LAST_NAME)) {
			userList = SubstringReflectionFilter.filter(userList, "lastName",
					f.getSingleFilter(FILTER_NAME_SUBSTR_LAST_NAME));
		}

		if (f.isFilterSet(FILTER_NAME_SUBSTR_FIRST_NAME)) {

			userList = SubstringReflectionFilter.filter(userList, "firstName",
					f.getSingleFilter(FILTER_NAME_SUBSTR_FIRST_NAME));
		}

		if (f.isFilterSet(FILTER_NAME_SUBSTR_USER_NAME)) {

			userList = SubstringReflectionFilter.filter(userList, "userName",
					f.getSingleFilter(FILTER_NAME_SUBSTR_USER_NAME));
		}

		int pageNumber = p.getParameterAsInt(CommonParamKeys.PageNumber.toString());
		int pageSize = p.getParameterAsInt(CommonParamKeys.PageSize.toString());

		userList = PaginationFilter.filter(userList, pageNumber, pageSize);
		return userList;
	}

	public void setHawthorneBackend(HawthorneBackend data) {
		this.data = data;
	}

}
