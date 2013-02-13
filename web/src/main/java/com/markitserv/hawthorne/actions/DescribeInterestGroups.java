/**
 * 
 */
package com.markitserv.hawthorne.actions;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markitserv.hawthorne.HawthorneBackend;
import com.markitserv.hawthorne.types.InterestGroup;
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

/**
 * @author kiran.gogula
 *
 */
@Service
public class DescribeInterestGroups extends AbstractPaginatedAction{

	
	Logger log = LoggerFactory.getLogger(DescribeInterestGroups.class);

	@Autowired
	private HawthorneBackend data;

	@Override
	protected ParamsAndFiltersDefinition createParameterDefinition() {

		// Add validation
		ParamsAndFiltersDefinition def = new ParamsAndFiltersDefinition();
		
		// Sorting
		SortingPresetDefinitionBuilder sortBuilder = new SortingPresetDefinitionBuilder();
		sortBuilder = sortBuilder.setDefaultSort("name", SortOrder.Asc);

		def.mergeWith(sortBuilder.build());

		return def;
	}
	
	
	
	@Override
	protected ActionResult performAction(ActionParameters params,
			ActionFilters filters) {
		List<InterestGroup> groupList = data.getInterestGroups();

		int totalRecords = groupList.size();

		groupList = applyFilters(params, filters, groupList);

		PaginatedActionResult res = new PaginatedActionResult(groupList);
		res.setTotalRecords(totalRecords);

		return res;
	}
	
	
	private List<InterestGroup> applyFilters(ActionParameters p, ActionFilters f,
			List<InterestGroup> interestGroups) {		

		int pageNumber = p.getParameterAsInt(CommonParamKeys.PageNumber
				.toString());
		int pageSize = p.getParameterAsInt(CommonParamKeys.PageSize.toString());

		interestGroups = PaginationFilter.filter(interestGroups, pageNumber, pageSize);
		return interestGroups;
	}

}
