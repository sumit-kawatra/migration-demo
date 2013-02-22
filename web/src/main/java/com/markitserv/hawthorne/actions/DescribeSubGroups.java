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
import com.markitserv.hawthorne.types.SubGroup;
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
import com.markitserv.msws.validation.IntegerMaxMinValidation;
import com.markitserv.msws.validation.IntegerValidation;
import com.markitserv.msws.validation.MutuallyExclusiveWithValidation;
import com.markitserv.msws.validation.RequiredIfAllNotProvidedValidation;

/**
 * @author kiran.gogula
 * 
 */
@Service
public class DescribeSubGroups extends AbstractPaginatedAction {

	Logger log = LoggerFactory.getLogger(DescribeSubGroups.class);
	
	private static final String FILTER_NAME_SUBSTR_SUB_GROUP_NAME = "substrSubGroup";
	private static final String PARAM_NAME_USER_NAME = "UserName";
	private static final String PARAM_PARTICIPANT_ID = "ParticipantId";
	
	
	
	
	@Autowired
	private HawthorneBackend data;

	@Override
	protected ParamsAndFiltersDefinition createParameterDefinition() {

		// Add validation
		ParamsAndFiltersDefinition def = new ParamsAndFiltersDefinition();
		
	   // UserName
		def.addValidation(PARAM_NAME_USER_NAME, new MutuallyExclusiveWithValidation(
				new String[] {
					PARAM_PARTICIPANT_ID
				}));
		def.addValidation(PARAM_NAME_USER_NAME, new RequiredIfAllNotProvidedValidation(
				new String[] {
					PARAM_PARTICIPANT_ID
				}));

		// Participant ID
		def.addValidation(PARAM_PARTICIPANT_ID, new MutuallyExclusiveWithValidation(
				new String[] {
					PARAM_NAME_USER_NAME
				}));
		def.addValidation(PARAM_PARTICIPANT_ID, new RequiredIfAllNotProvidedValidation(
				new String[] {
					PARAM_NAME_USER_NAME
				}));
		def.addValidation(PARAM_PARTICIPANT_ID, new IntegerValidation());
		def.addValidation(PARAM_PARTICIPANT_ID, new IntegerMaxMinValidation(1,
				IntegerMaxMinValidation.UNLIMITED));

		
		// Sorting
		SortingPresetDefinitionBuilder sortBuilder = new SortingPresetDefinitionBuilder();
		sortBuilder = sortBuilder.setDefaultSort("name", SortOrder.Asc);

		return def;
	}

	
	
	@Override
	protected ParamsAndFiltersDefinition createFilterDefinition() {
		ParamsAndFiltersDefinition def = new ParamsAndFiltersDefinition();

		def.addValidation(FILTER_NAME_SUBSTR_SUB_GROUP_NAME,
				new CollectionSizeValidation(
						CollectionSizeValidation.UNLIMITED, 1));

		return def;
	}

	
	
	@Override
	protected ActionResult performAction(ActionParameters params, ActionFilters filters) {
		
		int participantId = 0;
		String userName = null;
		
		if (params.isParameterSet(PARAM_PARTICIPANT_ID)) {
			participantId = Integer.parseInt((String) params
					.getParameter(PARAM_PARTICIPANT_ID));
		}

		if (params.isParameterSet(PARAM_NAME_USER_NAME)) {
			userName = (String) params.getParameter(PARAM_NAME_USER_NAME);
		}
		
		//Get the SubGroup(s) for the corresponding participantId or the UserName
		List<SubGroup> subGroupList = data.getSubGroups(participantId, userName);
		
		//Total record Count
		int totalRecords = subGroupList.size();
		
		// Apply the filter on specific SubGroup Name or ShortName
		subGroupList = applyFilters(params, filters, subGroupList);
		
		PaginatedActionResult res = new PaginatedActionResult(subGroupList);
		res.getPaginatedMetaData().setTotalRecords(totalRecords);

		return res;
	}
	
	
	private List<SubGroup> applyFilters(ActionParameters p, ActionFilters f, List<SubGroup> subGroupList) {
		
		List<SubGroup> subGrpList = new ArrayList<SubGroup>();

		if (f.isFilterSet(FILTER_NAME_SUBSTR_SUB_GROUP_NAME)) {
			subGrpList.addAll(SubstringReflectionFilter.filter(subGroupList, "name",
					f.getSingleFilter(FILTER_NAME_SUBSTR_SUB_GROUP_NAME)));
			
			subGrpList.addAll(SubstringReflectionFilter.filter(subGroupList, "shortName",
					f.getSingleFilter(FILTER_NAME_SUBSTR_SUB_GROUP_NAME)));
		}
		

		int pageNumber = p.getParameterAsInt(CommonParamKeys.PageNumber.toString());
		int pageSize = p.getParameterAsInt(CommonParamKeys.PageSize.toString());

		return PaginationFilter.filter(subGrpList, pageNumber, pageSize);
	}

}