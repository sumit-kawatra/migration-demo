package com.markitserv.hawthorne.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markitserv.hawthorne.util.HardcodedData;
import com.markitserv.mwws.action.AbstractAction;
import com.markitserv.mwws.action.ActionFilters;
import com.markitserv.mwws.action.ActionParameters;
import com.markitserv.mwws.action.ActionResult;
import com.markitserv.mwws.definition.ParamsAndFiltersDefinition;
import com.markitserv.mwws.validation.CollectionValidation;
import com.markitserv.mwws.validation.ForEachValidator;
import com.markitserv.mwws.validation.IntegerValidation;
import com.markitserv.mwws.validation.OptionalValidation;
import com.markitserv.mwws.validation.RequiredValidation;
import com.markitserv.mwws.validation.CollectionSizeValidation;

@Service
/**
 * Describes all Legal Entities
 * @author roy.truelove
 *
 */
public class DescribeLegalEntities extends AbstractAction {

	@Autowired
	private HardcodedData data;

	@Override
	protected ParamsAndFiltersDefinition getFilterDefinition() {

		ParamsAndFiltersDefinition def = new ParamsAndFiltersDefinition();

		def.addValidation("substr", new OptionalValidation());
		def.addValidation("substr", new ForEachValidator(
				new IntegerValidation()));

		return def;
	}

	@Override
	protected ParamsAndFiltersDefinition getParameterDefinition() {

		// Add validation
		ParamsAndFiltersDefinition def = new ParamsAndFiltersDefinition();

		def.addValidation("ParticipantId", new RequiredValidation());
		def.addValidation("ParticipantId",new IntegerValidation());

		return def;
	}

	@Override
	protected ActionResult performAction(ActionParameters p, ActionFilters f) {

		ActionResult res = new ActionResult();
		res.setCollection(data.getLegalEntities());
		return res;
	}

}
