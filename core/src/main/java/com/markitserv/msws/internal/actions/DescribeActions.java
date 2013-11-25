package com.markitserv.msws.internal.actions;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markitserv.msws.action.AbstractAction;
import com.markitserv.msws.action.ActionCommand;
import com.markitserv.msws.action.ParamsAndFiltersDefinition;
import com.markitserv.msws.action.resp.ActionDescription;
import com.markitserv.msws.action.resp.ActionResult;
import com.markitserv.msws.internal.action.ActionRegistry;
import com.markitserv.msws.validation.AbstractValidation;
import com.markitserv.msws.validation.OptionalValidation;

@Service
public class DescribeActions extends AbstractAction {

	private static final String PARAM_ACTION_NAME = "ActionName";
	@Autowired
	ActionRegistry registry;

	@Override
	protected ActionResult performAction(ActionCommand command) {
		
		List<ActionDescription> aDescs = new LinkedList<ActionDescription>();
		//Map<String, ActionDescription> aDescs = new HashMap<String, ActionDescription>();
		Map<String, AbstractAction> allActionsMap = registry.getAllActions();
		
		Collection<AbstractAction> allActions = allActionsMap.values();
		
		// will be null if the action name was not provided
		String showOnlyActionName = command.getParameters().getParameter(PARAM_ACTION_NAME, String.class);
		
		for (Iterator<AbstractAction> i = allActions.iterator(); i.hasNext();) {
			
			AbstractAction action = i.next();
			
			if (action.isPrivate()) {
				continue;
			}
			
			if (showOnlyActionName != null) {
				if (!action.getName().equals(showOnlyActionName)) {
					continue;
				}
			}
			
			ActionDescription desc = new ActionDescription();
			
			desc.setName(action.getName());
			desc.setDescription(action.getDescription());
			
			// Parameter validations
			
			ParamsAndFiltersDefinition params = action.getParameterDefinition();
			Map<String, List<AbstractValidation>> paramValidations = params.getValidations();
			
			Set<String> paramNames = paramValidations.keySet();
			
			// for each param
			for (String paramName : paramNames) {
				
				List<AbstractValidation> validations = paramValidations.get(paramName);
				
				// for each validation for that param
				for (AbstractValidation validation : validations) {
					
					desc.addValidation(paramName, validation.getDescription());
					
				}
			}
			
			// Filter validations
			
			ParamsAndFiltersDefinition filters = action.getFilterDefinition();
			Map<String, List<AbstractValidation>> filterValidations = filters.getValidations();
			
			Set<String> filterNames = filterValidations.keySet();
			
			// for each param
			for (String paramName : filterNames) {
				
				List<AbstractValidation> validations = filterValidations.get(paramName);
				
				// for each validation for that param
				for (AbstractValidation validation : validations) {
					
					desc.addFilter(paramName, validation.getDescription());
					
				}
			}
			
			aDescs.add(desc);
			Collections.sort(aDescs);
		}
		
		return new ActionResult(aDescs);
	}

	@Override
	protected ParamsAndFiltersDefinition createParameterDefinition() {
		
		ParamsAndFiltersDefinition def = super.createParameterDefinition();
		def.addValidation(PARAM_ACTION_NAME, new OptionalValidation());
		
		return def;
	}
	

	@Override
	public String getDescription() {
		return "Describes all actions available to the logged-in user";
	}
}
