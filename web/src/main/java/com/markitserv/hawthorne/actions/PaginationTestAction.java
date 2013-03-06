package com.markitserv.hawthorne.actions;

import java.util.Random;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.markitserv.hawthorne.types.SuccessFailure;
import com.markitserv.msws.action.AbstractPaginatedAction;
import com.markitserv.msws.action.ActionFilters;
import com.markitserv.msws.action.ActionParameters;
import com.markitserv.msws.action.ActionResult;
import com.markitserv.msws.action.PaginatedActionResponseMetaData;
import com.markitserv.msws.action.PaginatedActionResult;
import com.markitserv.msws.definition.ParamsAndFiltersDefinition;
import com.markitserv.msws.validation.IntegerMaxMinValidationAndConversion;
import com.markitserv.msws.validation.RequiredValidation;

@Service
public class PaginationTestAction extends AbstractPaginatedAction {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	protected ActionResult performAction(ActionParameters params, ActionFilters filters) {
		int size = params.getParameter("Size", Integer.class);

		log.debug(params.toString());

		Random f = new Random();
		Stack<SuccessFailure> all = new Stack<SuccessFailure>();

		for (int i = 0; i < size; i++) {
			SuccessFailure t = new SuccessFailure(f.nextBoolean());
			all.push(t);
		}

		PaginatedActionResult res = new PaginatedActionResult(all);
		PaginatedActionResponseMetaData metaData = res.getPaginatedMetaData();
		metaData.setTotalRecords(all.size());

		res.setMetaData(metaData);
		return res;
	}

	@Override
	protected ParamsAndFiltersDefinition createParameterDefinition() {

		ParamsAndFiltersDefinition def = new ParamsAndFiltersDefinition();

		def.addValidation("Size", new RequiredValidation());
		def.addValidation("Size", new IntegerMaxMinValidationAndConversion(1, 100000));

		return def;
	}
}
