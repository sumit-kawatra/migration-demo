package com.markitserv.hawthorne.actions;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markitserv.hawthorne.HawthorneBackend;
import com.markitserv.hawthorne.types.LegalEntity;
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
import com.markitserv.msws.validation.ForEachValidatorAndConverter;
import com.markitserv.msws.validation.IntegerMaxMinValidation;
import com.markitserv.msws.validation.IntegerValidationAndConversion;
import com.markitserv.msws.validation.RequiredValidation;

@Service
/**
 * Describes all Legal Entities
 * @author roy.truelove
 *
 */
public class DescribeLegalEntities extends AbstractPaginatedAction {

	private static final String FILTER_NAME_SUBSTR_NAME = "substrName";
	private static final String FILTER_NAME_SUBSTR_BIC = "substrBic";

	Logger log = LoggerFactory.getLogger(DescribeLegalEntities.class);

	@Autowired
	private HawthorneBackend data;

	@Override
	protected ParamsAndFiltersDefinition createParameterDefinition() {

		// Add validation
		ParamsAndFiltersDefinition def = new ParamsAndFiltersDefinition();

		// Participant ID
		def.addValidation("ParticipantId", new RequiredValidation());
		def.addValidation("ParticipantId", new IntegerValidationAndConversion());
		def.addValidationAndConversion("ParticipantId", new IntegerMaxMinValidation(1,
				IntegerMaxMinValidation.UNLIMITED));

		// Legal Entity ID
		def.addValidation("LegalEntityId", new ForEachValidator(new IntegerValidationAndConversion()));
		def.addValidationAndConversion("LegalEntityId", new ForEachValidatorAndConverter(
				new IntegerMaxMinValidation(1, IntegerMaxMinValidation.UNLIMITED)));
		// Only supporting a signle legal entity ID at a time (for now)
		def.addValidation("LegalEntityId", new CollectionSizeValidation(1, 1));

		// Sorting
		SortingPresetDefinitionBuilder sortBuilder = new SortingPresetDefinitionBuilder();
		sortBuilder = sortBuilder.setDefaultSort("Id", SortOrder.Asc);
		sortBuilder = sortBuilder.addSortOption("StartDate");

		def.mergeWith(sortBuilder.build());

		return def;
	}

	@Override
	protected ParamsAndFiltersDefinition createFilterDefinition() {
		ParamsAndFiltersDefinition def = new ParamsAndFiltersDefinition();

		def.addValidation(FILTER_NAME_SUBSTR_NAME, new CollectionSizeValidation(
				CollectionSizeValidation.UNLIMITED, 1));

		def.addValidation(FILTER_NAME_SUBSTR_BIC, new CollectionSizeValidation(
				CollectionSizeValidation.UNLIMITED, 1));

		return def;
	}

	@Override
	protected ActionResult performAction(ActionParameters params, ActionFilters filters) {

		List<LegalEntity> legalEntities = data.getLegalEntities();

		int totalRecords = legalEntities.size();

		legalEntities = applyFilters(params, filters, legalEntities);

		PaginatedActionResult res = new PaginatedActionResult(legalEntities);
		res.getPaginatedMetaData().setTotalRecords(totalRecords);

		return res;
	}

	private List<LegalEntity> applyFilters(ActionParameters p, ActionFilters f,
			List<LegalEntity> legalEntities) {

		// Note that of course we don't do this in reality but until we get the
		// DB it'll do. Filters LE's down to a single LE by ID.
		if (p.isParameterSet("LegalEntityId")) {
			List<String> leId = (List<String>) p.getParameter("LegalEntityId");
			legalEntities = PropertyEqualsReflectionFilter.filter(legalEntities, "id",
					Integer.parseInt((String) leId.get(0)));
		}

		if (f.isFilterSet(FILTER_NAME_SUBSTR_NAME)) {

			legalEntities = SubstringReflectionFilter.filter(legalEntities, "name",
					f.getSingleFilter(FILTER_NAME_SUBSTR_NAME));
		}

		if (f.isFilterSet(FILTER_NAME_SUBSTR_BIC)) {

			legalEntities = SubstringReflectionFilter.filter(legalEntities, "bic",
					f.getSingleFilter(FILTER_NAME_SUBSTR_BIC));
		}

		int pageNumber = p.getParameterAsInt(CommonParamKeys.PageNumber.toString());
		int pageSize = p.getParameterAsInt(CommonParamKeys.PageSize.toString());

		legalEntities = PaginationFilter.filter(legalEntities, pageNumber, pageSize);
		return legalEntities;
	}
}