package com.markitserv.hawthorne.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markitserv.hawthorne.HawthorneBackend;
import com.markitserv.hawthorne.util.HardcodedHawthorneBackend;
import com.markitserv.mwws.action.AbstractAction;
import com.markitserv.mwws.action.ActionFilters;
import com.markitserv.mwws.action.ActionParameters;
import com.markitserv.mwws.action.ActionResult;
import com.markitserv.mwws.action.SortOrder;
import com.markitserv.mwws.definition.PaginationPresetDefintion;
import com.markitserv.mwws.definition.ParamsAndFiltersDefinition;
import com.markitserv.mwws.definition.SortingPresetDefinitionBuilder;
import com.markitserv.mwws.validation.CollectionValidation;
import com.markitserv.mwws.validation.IntegerValidation;
import com.markitserv.mwws.validation.RequiredValidation;

@Service
/**
 * Describes all Legal Entities
 * @author roy.truelove
 *
 */
public class DescribeTradingRequestStatuses extends AbstractAction {

	Logger log = LoggerFactory.getLogger(DescribeTradingRequestStatuses.class);

	@Autowired
	private HawthorneBackend data;

	@Override
	protected ParamsAndFiltersDefinition getParameterDefinition() {

		// Add validation
		ParamsAndFiltersDefinition def = new ParamsAndFiltersDefinition();

		def.mergeWith(new PaginationPresetDefintion());

		return def;
	}

	@Override
	protected ActionResult performAction(ActionParameters p, ActionFilters f) {

		log.info(p.toString());
		log.info(f.toString());

		ActionResult res = new ActionResult();
		res.setCollection(data.getLegalEntities());
		res.setCollection(data.getTradingRequestStatuses());
		return res;
	}

}
