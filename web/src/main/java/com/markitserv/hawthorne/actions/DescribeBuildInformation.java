package com.markitserv.hawthorne.actions;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markitserv.hawthorne.BuildInformation;
import com.markitserv.hawthorne.types.Build;
import com.markitserv.msws.action.AbstractAction;
import com.markitserv.msws.action.ActionFilters;
import com.markitserv.msws.action.ActionParameters;
import com.markitserv.msws.action.ActionResult;
import com.markitserv.msws.definition.ParamsAndFiltersDefinition;
import com.markitserv.msws.validation.BooleanValidation;
import com.markitserv.msws.validation.OptionalValidation;

@Service
public class DescribeBuildInformation extends AbstractAction {
	
	private static final String PARAM_SHORT = "Short";

	@Override
	protected ParamsAndFiltersDefinition createParameterDefinition() {
		
		ParamsAndFiltersDefinition params = super.createParameterDefinition();
		
		params.addValidation(PARAM_SHORT, new OptionalValidation());
		params.addValidation(PARAM_SHORT, new BooleanValidation());
		params.addDefaultParam(PARAM_SHORT, "false");
		
		return params;
		
	}

	@Autowired
	BuildInformation buildInfo;

	@Override
	protected ActionResult performAction(ActionParameters params,
			ActionFilters filters) {
		
		String sha = buildInfo.getBuild();
		
		if (params.getParameterAsBoolean(PARAM_SHORT)) {
			sha = StringUtils.left(sha, 7);
		}
		
		return new ActionResult(new Build(sha));
	}
}
