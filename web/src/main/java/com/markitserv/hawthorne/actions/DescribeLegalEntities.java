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
	protected ParamsAndFiltersDefinition getParameterDefinition() {

		// Add validation
		ParamsAndFiltersDefinition def = new ParamsAndFiltersDefinition();

		def.addValidation("foo", new RequiredValidation());
		def.addValidation("foo", new CollectionSizeValidation(1,
				CollectionSizeValidation.UNLIMITED));
		
		def.addValidation("be", new RequiredValidation());
		def.addValidation("be", new CollectionValidation());
		def.addValidation("be", new ForEachValidator(new IntegerValidation()));
		def.addValidation("be", new CollectionSizeValidation(1,
				CollectionSizeValidation.UNLIMITED));
		
		def.addValidation("bar", new OptionalValidation());
		def.addValidation("bar", new IntegerValidation());
		
		return def;
	}

	@Override
	protected ActionResult performAction(ActionParameters p, ActionFilters f) {

		ActionResult res = new ActionResult();
		res.setCollection(data.getLegalEntities());
		return res;
	}

}
