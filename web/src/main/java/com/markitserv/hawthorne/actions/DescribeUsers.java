/**
 * 
 */
package com.markitserv.hawthorne.actions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markitserv.hawthorne.HawthorneBackend;
import com.markitserv.hawthorne.types.User;
import com.markitserv.hawthorne.util.HawthorneParamsAndFilters;
import com.markitserv.msws.action.AbstractPaginatedAction;
import com.markitserv.msws.action.ActionFilters;
import com.markitserv.msws.action.ActionParameters;
import com.markitserv.msws.action.ActionResult;
import com.markitserv.msws.action.CommonParamKeys;
import com.markitserv.msws.action.PaginatedActionResponseMetaData;
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
import com.markitserv.msws.validation.MutuallyExclusiveWithValidation;
import com.markitserv.msws.validation.RequiredIfAllNotProvidedValidation;
import com.markitserv.msws.validation.RequiredValidation;

/**
 * @author kiran.gogula
 * 
 */
@Service
public class DescribeUsers extends AbstractPaginatedAction {

	Logger log = LoggerFactory.getLogger(DescribeUsers.class);

	@Autowired
	private HawthorneBackend data;

	@Override
	protected ParamsAndFiltersDefinition createParameterDefinition() {

		ParamsAndFiltersDefinition def = new ParamsAndFiltersDefinition();

		addUserNameDefs(def);
		addParticipantIdDefs(def);
		addLegalEntityIdDef(def);
		addInterestGroupIdDef(def);
		addSortingDefs(def);

		return def;
	}

	@Override
	protected ActionResult performAction(ActionParameters params, ActionFilters filters) {

		List<User> userList = mungeUserList(params, filters);
		int totalRecords = userList.size();
		// Paginate

		int pageStartIndex = params.getParameter(CommonParamKeys.PageStartIndex.toString(),
				Integer.class);
		int pageSize = params.getParameter(CommonParamKeys.PageSize.toString(),
				Integer.class);

		userList = PaginationFilter.filter(userList, pageStartIndex, pageSize);

		PaginatedActionResult res = new PaginatedActionResult(userList);
		PaginatedActionResponseMetaData paginatedMetaData = res.getPaginatedMetaData();

		paginatedMetaData.setTotalRecords(totalRecords);

		return res;
	}

	private void addSortingDefs(ParamsAndFiltersDefinition def) {
		SortingPresetDefinitionBuilder sortBuilder = new SortingPresetDefinitionBuilder();
		sortBuilder = sortBuilder.setDefaultSort("userId", SortOrder.Asc);

		def.mergeWith(sortBuilder.build());
	}

	private void addInterestGroupIdDef(ParamsAndFiltersDefinition def) {

		def.addValidationAndConversion(HawthorneParamsAndFilters.PARAM_INTERESTGROUP_ID,
				new ForEachValidatorAndConverter(new IntegerMaxMinValidationAndConversion(1,
						IntegerMaxMinValidationAndConversion.UNLIMITED)));
		// Only supporting a single interest group ID at a time (for now)
		def.addValidation(HawthorneParamsAndFilters.PARAM_INTERESTGROUP_ID,
				new CollectionSizeValidation(1, 1));

		// If InterestGroup is provided UserName, ParticipantId & LegalEntityId
		// are not required
		def.addValidation(HawthorneParamsAndFilters.PARAM_INTERESTGROUP_ID,
				new MutuallyExclusiveWithValidation(new String[] {
						HawthorneParamsAndFilters.PARAM_USER_NAME,
						HawthorneParamsAndFilters.PARAM_PARTICIPANT_ID,
						HawthorneParamsAndFilters.PARAM_LEGAL_ENTITY_ID
				}));
		// InterestGroup is required when UserName, ParticipantId & LegalEntityId
		// are not provided
		def.addValidation(HawthorneParamsAndFilters.PARAM_INTERESTGROUP_ID,
				new RequiredIfAllNotProvidedValidation(new String[] {
						HawthorneParamsAndFilters.PARAM_USER_NAME,
						HawthorneParamsAndFilters.PARAM_PARTICIPANT_ID,
						HawthorneParamsAndFilters.PARAM_LEGAL_ENTITY_ID
				}));

	}

	private void addLegalEntityIdDef(ParamsAndFiltersDefinition def) {
		def.addValidationAndConversion(HawthorneParamsAndFilters.PARAM_LEGAL_ENTITY_ID,
				new ForEachValidatorAndConverter(new IntegerMaxMinValidationAndConversion(1,
						IntegerMaxMinValidationAndConversion.UNLIMITED)));
		// Only supporting a single legal entity ID at a time (for now)
		def.addValidation(HawthorneParamsAndFilters.PARAM_LEGAL_ENTITY_ID,
				new CollectionSizeValidation(1, 1));

		// If LegalEntityId is provided UserName, ParticipantId & InterestGroupId
		// are not required
		def.addValidation(HawthorneParamsAndFilters.PARAM_LEGAL_ENTITY_ID,
				new MutuallyExclusiveWithValidation(new String[] {
						HawthorneParamsAndFilters.PARAM_USER_NAME,
						HawthorneParamsAndFilters.PARAM_PARTICIPANT_ID,
						HawthorneParamsAndFilters.PARAM_INTERESTGROUP_ID
				}));
		// LegalEntityId is required when UserName, ParticipantId & InterestGroup
		// are not provided
		def.addValidation(HawthorneParamsAndFilters.PARAM_LEGAL_ENTITY_ID,
				new RequiredIfAllNotProvidedValidation(new String[] {
						HawthorneParamsAndFilters.PARAM_USER_NAME,
						HawthorneParamsAndFilters.PARAM_PARTICIPANT_ID,
						HawthorneParamsAndFilters.PARAM_INTERESTGROUP_ID
				}));
	}

	private void addParticipantIdDefs(ParamsAndFiltersDefinition def) {

		def.addValidationAndConversion(HawthorneParamsAndFilters.PARAM_PARTICIPANT_ID,
				new ForEachValidatorAndConverter(new IntegerMaxMinValidationAndConversion(1,
						IntegerMaxMinValidationAndConversion.UNLIMITED)));

		// Only supporting a single participant ID at a time (for now)
		def.addValidation(HawthorneParamsAndFilters.PARAM_PARTICIPANT_ID,
				new CollectionSizeValidation(1, 1));

		// If ParticipantId is provided UserName , LegalEntityId & ParticipantId
		// are not required
		def.addValidation(HawthorneParamsAndFilters.PARAM_PARTICIPANT_ID,
				new MutuallyExclusiveWithValidation(new String[] {
						HawthorneParamsAndFilters.PARAM_USER_NAME,
						HawthorneParamsAndFilters.PARAM_LEGAL_ENTITY_ID,
						HawthorneParamsAndFilters.PARAM_INTERESTGROUP_ID
				}));

		// ParticipantId is required when UserName,LegalEntityId & InterestGroupId
		// are not provided
		def.addValidation(HawthorneParamsAndFilters.PARAM_PARTICIPANT_ID,
				new RequiredIfAllNotProvidedValidation(new String[] {
						HawthorneParamsAndFilters.PARAM_USER_NAME,
						HawthorneParamsAndFilters.PARAM_LEGAL_ENTITY_ID,
						HawthorneParamsAndFilters.PARAM_INTERESTGROUP_ID
				}));
	}

	private void addUserNameDefs(ParamsAndFiltersDefinition def) {
		def.addValidation(HawthorneParamsAndFilters.PARAM_USER_NAME, new ForEachValidator(
				new RequiredValidation()));
		// Only supporting a single userName at a time (for now)
		def.addValidation(HawthorneParamsAndFilters.PARAM_USER_NAME,
				new CollectionSizeValidation(1, 1));
		// If UserName is provided ParticipantId , LegalEntityId & InterestGroupId
		// are not required
		def.addValidation(HawthorneParamsAndFilters.PARAM_USER_NAME,
				new MutuallyExclusiveWithValidation(new String[] {
						HawthorneParamsAndFilters.PARAM_PARTICIPANT_ID,
						HawthorneParamsAndFilters.PARAM_LEGAL_ENTITY_ID,
						HawthorneParamsAndFilters.PARAM_INTERESTGROUP_ID
				}));
		// UserName is required when ParticipantId , LegalEntityId &
		// InterestGroupId are not provided
		def.addValidation(HawthorneParamsAndFilters.PARAM_USER_NAME,
				new RequiredIfAllNotProvidedValidation(new String[] {
						HawthorneParamsAndFilters.PARAM_PARTICIPANT_ID,
						HawthorneParamsAndFilters.PARAM_LEGAL_ENTITY_ID,
						HawthorneParamsAndFilters.PARAM_INTERESTGROUP_ID
				}));
	}

	@Override
	protected ParamsAndFiltersDefinition createFilterDefinition() {
		ParamsAndFiltersDefinition def = new ParamsAndFiltersDefinition();

		def.addValidation(HawthorneParamsAndFilters.FILTER_SUBSTR_LAST_NAME,
				new CollectionSizeValidation(CollectionSizeValidation.UNLIMITED, 1));

		def.addValidation(HawthorneParamsAndFilters.FILTER_SUBSTR_FIRST_NAME,
				new CollectionSizeValidation(CollectionSizeValidation.UNLIMITED, 1));

		def.addValidation(HawthorneParamsAndFilters.FILTER_SUBSTR_USER_NAME,
				new CollectionSizeValidation(CollectionSizeValidation.UNLIMITED, 1));

		return def;
	}

	private List<User> mungeUserList(ActionParameters p, ActionFilters f) {
		List<User> userList = new ArrayList<User>(getUsers(p));
		userList = applyFilters(f, userList);

		return userList;
	}

	private Set<User> getUsers(ActionParameters p) {
		Set<User> userSet = new HashSet<User>();
		if (p.isParameterSet(HawthorneParamsAndFilters.PARAM_PARTICIPANT_ID)) {
			List<Integer> participantIdList = p.getParameter(
					HawthorneParamsAndFilters.PARAM_PARTICIPANT_ID, List.class);
			for (Integer id : participantIdList) {
				userSet = data.getUsersForParticipant(id);
			}
		} else if (p.isParameterSet(HawthorneParamsAndFilters.PARAM_USER_NAME)) {
			List<String> userNameList = p.getParameter(
					HawthorneParamsAndFilters.PARAM_USER_NAME, List.class);
			for (String string : userNameList) {
				data.getUser(string);
				userSet = data.getUser(string);
			}
		} else if (p.isParameterSet(HawthorneParamsAndFilters.PARAM_LEGAL_ENTITY_ID)) {
			List<Integer> legalEntityIdList = (List<Integer>) p.getParameter(
					HawthorneParamsAndFilters.PARAM_LEGAL_ENTITY_ID, List.class);
			for (Integer id : legalEntityIdList) {
				userSet = data.getUsersForLegalEntity(id);
			}
		} else if (p.isParameterSet(HawthorneParamsAndFilters.PARAM_INTERESTGROUP_ID)) {
			List<Integer> interestGroupIdList = (List<Integer>) p.getParameter(
					HawthorneParamsAndFilters.PARAM_INTERESTGROUP_ID, List.class);
			for (Integer id : interestGroupIdList) {
				userSet = data.getUsersForInterestGrp(id);
			}
		}
		return userSet;
	}

	private List<User> applyFilters(ActionFilters f, List<User> userList) {
		if (f.isFilterSet(HawthorneParamsAndFilters.FILTER_SUBSTR_LAST_NAME)) {
			userList = SubstringReflectionFilter.filter(userList, "lastName",
					f.getSingleFilter(HawthorneParamsAndFilters.FILTER_SUBSTR_LAST_NAME));
		}

		if (f.isFilterSet(HawthorneParamsAndFilters.FILTER_SUBSTR_FIRST_NAME)) {

			userList = SubstringReflectionFilter.filter(userList, "firstName",
					f.getSingleFilter(HawthorneParamsAndFilters.FILTER_SUBSTR_FIRST_NAME));
		}

		if (f.isFilterSet(HawthorneParamsAndFilters.FILTER_SUBSTR_USER_NAME)) {

			userList = SubstringReflectionFilter.filter(userList, "userName",
					f.getSingleFilter(HawthorneParamsAndFilters.FILTER_SUBSTR_USER_NAME));
		}
		return userList;
	}

	public void setHawthorneBackend(HawthorneBackend data) {
		this.data = data;
	}

}
