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
import com.markitserv.hawthorne.types.InterestGroup;
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
import com.markitserv.msws.filters.PropertyEqualsReflectionFilter;
import com.markitserv.msws.filters.SubstringReflectionFilter;
import com.markitserv.msws.validation.CollectionSizeValidation;
import com.markitserv.msws.validation.IntegerMaxMinValidationAndConversion;
import com.markitserv.msws.validation.IntegerValidationAndConversion;
import com.markitserv.msws.validation.OptionalValidation;
import com.markitserv.msws.validation.RequiredValidation;

/**
 * @author kiran.gogula
 * 
 */
@Service
public class DescribeInterestGroups extends AbstractPaginatedAction {

	Logger log = LoggerFactory.getLogger(DescribeInterestGroups.class);

	@Autowired
	private HawthorneBackend data;

	@Override
	protected ParamsAndFiltersDefinition createParameterDefinition() {
		// parameters ParticipantId,InterestGroupId, UserId ... and filters
		// substrName, substrShortName

		ParamsAndFiltersDefinition def = new ParamsAndFiltersDefinition();

		def.addValidationAndConversion(HawthorneParamsAndFilters.PARAM_PARTICIPANT_ID,
				new IntegerValidationAndConversion());
		def.addValidationAndConversion(HawthorneParamsAndFilters.PARAM_PARTICIPANT_ID,
				new IntegerMaxMinValidationAndConversion(1,
						IntegerMaxMinValidationAndConversion.UNLIMITED));
		def.addValidationAndConversion(HawthorneParamsAndFilters.PARAM_USER_ID,
				new IntegerValidationAndConversion());
		def.addValidationAndConversion(HawthorneParamsAndFilters.PARAM_USER_ID,
				new IntegerMaxMinValidationAndConversion(1,
						IntegerMaxMinValidationAndConversion.UNLIMITED));
		def.addValidationAndConversion(HawthorneParamsAndFilters.PARAM_INTERESTGROUP_ID,
				new IntegerValidationAndConversion());
		def.addValidationAndConversion(HawthorneParamsAndFilters.PARAM_INTERESTGROUP_ID,
				new IntegerMaxMinValidationAndConversion(1,
						IntegerMaxMinValidationAndConversion.UNLIMITED));

		def.addValidation(HawthorneParamsAndFilters.PARAM_PARTICIPANT_ID,
				new RequiredValidation());
		def.addValidation(HawthorneParamsAndFilters.PARAM_USER_ID, new OptionalValidation());
		def.addValidation(HawthorneParamsAndFilters.PARAM_INTERESTGROUP_ID,
				new OptionalValidation());
		// Sorting
		SortingPresetDefinitionBuilder sortBuilder = new SortingPresetDefinitionBuilder();
		sortBuilder = sortBuilder.setDefaultSort("name", SortOrder.Asc);

		def.mergeWith(sortBuilder.build());

		return def;
	}

	@Override
	protected ParamsAndFiltersDefinition createFilterDefinition() {
		ParamsAndFiltersDefinition def = new ParamsAndFiltersDefinition();

		def.addValidation(HawthorneParamsAndFilters.FILTER_SUBSTR_INTERESTGROUP_NAME,
				new CollectionSizeValidation(CollectionSizeValidation.UNLIMITED, 1));
		def.addValidation(HawthorneParamsAndFilters.FILTER_SUBSTR_INTERESTGROUP_SHORT_NAME,
				new CollectionSizeValidation(CollectionSizeValidation.UNLIMITED, 1));

		return def;
	}

	@Override
	protected ActionResult performAction(ActionParameters params, ActionFilters filters) {

		int participantId = params.getParameter(
				HawthorneParamsAndFilters.PARAM_PARTICIPANT_ID, Integer.class);

		List<InterestGroup> groupList = new ArrayList<InterestGroup>(
				data.getInterestGroupsForParticipant(participantId));

		int totalRecords = groupList.size();

		groupList = applyFilters(params, filters, groupList);

		// TODO add 'filter size' to the metadata, after we see how this field is
		// used by datatables

		PaginatedActionResult res = new PaginatedActionResult(groupList);
		res.getPaginatedMetaData().setTotalRecords(totalRecords);

		return res;
	}

	@SuppressWarnings("unchecked")
	private List<InterestGroup> applyFilters(ActionParameters p, ActionFilters f,
			List<InterestGroup> interestGroups) {

		if (p.isParameterSet(HawthorneParamsAndFilters.PARAM_PARTICIPANT_ID)) {
			Integer participantId = p.getParameter(
					HawthorneParamsAndFilters.PARAM_PARTICIPANT_ID, Integer.class);
			interestGroups = PropertyEqualsReflectionFilter.filter(interestGroups,
					"participantId", participantId);
		}
		if (p.isParameterSet(HawthorneParamsAndFilters.PARAM_INTERESTGROUP_ID)) {
			Integer igId = p.getParameter(HawthorneParamsAndFilters.PARAM_INTERESTGROUP_ID,
					Integer.class);
			interestGroups = PropertyEqualsReflectionFilter.filter(interestGroups, "id",
					igId);
		}
		if (p.isParameterSet(HawthorneParamsAndFilters.PARAM_USER_ID)) {
			Integer usrId = p.getParameter(HawthorneParamsAndFilters.PARAM_USER_ID,
					Integer.class);
			interestGroups = PropertyEqualsReflectionFilter.filter(interestGroups, "id",
					usrId);
		}
		if (f.isFilterSet(HawthorneParamsAndFilters.FILTER_SUBSTR_INTERESTGROUP_NAME)) {
			interestGroups = SubstringReflectionFilter
					.filter(
							interestGroups,
							"name",
							f.getSingleFilter(HawthorneParamsAndFilters.FILTER_SUBSTR_INTERESTGROUP_NAME));
		}

		if (f.isFilterSet(HawthorneParamsAndFilters.FILTER_SUBSTR_INTERESTGROUP_SHORT_NAME)) {
			interestGroups = SubstringReflectionFilter
					.filter(
							interestGroups,
							"shortName",
							f.getSingleFilter(HawthorneParamsAndFilters.FILTER_SUBSTR_INTERESTGROUP_SHORT_NAME));
		}

		int pageStartIndex = p.getParameter(CommonParamKeys.PageStartIndex.toString(),
				Integer.class);
		int pageSize = p.getParameter(CommonParamKeys.PageSize.toString(), Integer.class);

		interestGroups = PaginationFilter.filter(interestGroups, pageStartIndex, pageSize);
		return interestGroups;
	}
}
