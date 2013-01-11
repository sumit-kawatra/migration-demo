package com.markitserv.hawthorne.actions;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markitserv.hawthorne.HawthorneBackend;
import com.markitserv.hawthorne.types.LegalEntity;
import com.markitserv.mwws.action.AbstractPaginatedAction;
import com.markitserv.mwws.action.ActionFilters;
import com.markitserv.mwws.action.ActionParameters;
import com.markitserv.mwws.action.ActionResult;
import com.markitserv.mwws.action.PaginatedActionResult;
import com.markitserv.mwws.action.SortOrder;
import com.markitserv.mwws.definition.ParamsAndFiltersDefinition;
import com.markitserv.mwws.definition.SortingPresetDefinitionBuilder;
import com.markitserv.mwws.filters.SubstringReflectionFilter;
import com.markitserv.mwws.validation.CollectionSizeValidation;
import com.markitserv.mwws.validation.IntegerMaxMinValidation;
import com.markitserv.mwws.validation.IntegerValidation;
import com.markitserv.mwws.validation.RequiredValidation;

@Service
/**
 * Describes all Legal Entities
 * @author roy.truelove
 *
 */
public class DescribeLegalEntities extends AbstractPaginatedAction {

	private static final String FILTER_NAME_SUBSTR = "substr";

	Logger log = LoggerFactory.getLogger(DescribeLegalEntities.class);

	@Autowired
	private HawthorneBackend data;

	@Override
	protected ParamsAndFiltersDefinition createParameterDefinition() {

		// Add validation
		ParamsAndFiltersDefinition def = new ParamsAndFiltersDefinition();

		def.addValidation("ParticipantId", new RequiredValidation());
		def.addValidation("ParticipantId", new IntegerValidation());
		def.addValidation("ParticipantId", new IntegerMaxMinValidation(1,
				IntegerMaxMinValidation.UNLIMITED));

		SortingPresetDefinitionBuilder sortBuilder = new SortingPresetDefinitionBuilder();
		sortBuilder = sortBuilder.setDefaultSort("Id", SortOrder.Asc);
		sortBuilder = sortBuilder.addSortOption("StartDate");

		def.mergeWith(sortBuilder.build());

		return def;
	}

	@Override
	protected ParamsAndFiltersDefinition createFilterDefinition() {
		ParamsAndFiltersDefinition def = new ParamsAndFiltersDefinition();

		def.addValidation(FILTER_NAME_SUBSTR, new CollectionSizeValidation(
				CollectionSizeValidation.UNLIMITED, 1));

		return def;
	}

	@Override
	protected ActionResult performAction(ActionParameters p, ActionFilters f) {

		List<LegalEntity> legalEntities = data.getLegalEntities();

		if (f.isFilterSet(FILTER_NAME_SUBSTR)) {

			legalEntities = SubstringReflectionFilter.filter(legalEntities,
					"name", f.getSingleFilter(FILTER_NAME_SUBSTR));
		}
		
		PaginatedActionResult res = new PaginatedActionResult(legalEntities);
		res.setTotalRecords(legalEntities.size());
		
		return res;
	}
}