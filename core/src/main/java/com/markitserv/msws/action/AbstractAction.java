package com.markitserv.msws.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.markitserv.msws.action.internal.ActionCommand;
import com.markitserv.msws.definition.ParamsAndFiltersDefinition;
import com.markitserv.msws.exceptions.ProgrammaticException;
import com.markitserv.msws.internal.MswsAssert;
import com.markitserv.msws.internal.UuidGenerator;
import com.markitserv.msws.types.SessionInfo;
import com.markitserv.msws.validation.AbstractValidation;
import com.markitserv.msws.validation.RequiredValidation;
import com.markitserv.msws.validation.ValidationExceptionBuilder;
import com.markitserv.msws.validation.ValidationResponse;
import com.markitserv.msws.validation.ValidationExceptionBuilder.FilterOrParam;

@Service
public abstract class AbstractAction implements InitializingBean {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ActionRegistry actionRegistry;

	@Autowired
	private UuidGenerator uuidGenerator;

	private ParamsAndFiltersDefinition parameterDefinition;

	private ParamsAndFiltersDefinition filterDefinition;

	public final ActionResult internalPerformAction(ActionCommand command) {
		
		ActionParameters parameters = command.getParameters();
		ActionFilters filters = command.getFilters();

		// has side effects - parameters and filters are changed.
		applyDefaults(parameters, filters);

		// has side effects - parameters and filters are changed.
		validateAndConvertParametersAndFilters(parameters, filters);

		command.setParameters(parameters);
		command.setFilters(filters);

		ActionResult result = this.performAction(parameters, filters);

		// This is for backwards compatibility for the deprecated performAction 
		// method.  Once that method is removed / made final, this can be fixed
		if (result == null) {
			result = this.performAction(command);
		} else {
			log.warn("You are overriding a deprectated method in the "
					+ "AbstractAction class.  Instead use performAction(ActionCommand cmd");
		}

		validateResult(result);
		result = postProcessResult(result);

		return result;
	}

	// Ideally would be abstract but that would break backwards compatibility
	protected ActionResult performAction(ActionCommand command) {
		return null;
	}

	/**
	 * Gives subclasses a place to process a result after it's been performed.
	 * 
	 * @param result
	 * @return
	 */
	protected ActionResult postProcessResult(ActionResult result) {
		return result;
	}

	private void applyDefaults(ActionParameters parameters,
			ActionFilters filters) {

		Map<String, Object> paramDefaults = this.getParameterDefinition()
				.getDefaultParams();

		Map<String, Object> params = parameters.getAllParameters();

		for (String defaultKey : paramDefaults.keySet()) {
			if (!params.containsKey(defaultKey)) {
				parameters.addParameter(defaultKey,
						paramDefaults.get(defaultKey));
			}
		}
	}

	/**
	 * Ensures that the result is valid before sending back to the client
	 * 
	 * @param result
	 */
	protected void validateResult(ActionResult result) {

		String failureMsg = "Failed to validate ActionResult.";

		// either collection OR item needs to be set.
		if (result.getItems() == null && result.getItem() == null) {
			throw new ProgrammaticException(failureMsg
					+ "Either the collection or the item must be set.");
		}

		// cannot have both collection and item set at the same time.
		if (result.getItems() != null && result.getItem() != null) {
			throw new ProgrammaticException(failureMsg
					+ "Cannot set both the collection and the item.");
		}
	}

	/**
	 * Subclass should override performAction(ActionCommand cmd), not this. In a
	 * future release this will be removed / made final
	 * 
	 * @param params
	 * @param filters
	 * @return
	 */
	@Deprecated
	protected ActionResult performAction(ActionParameters params,
			ActionFilters filters) {
		return null;
	}

	/**
	 * Registers this action w/ the Registry upon startup
	 */
	private void registerWithActionRegistry() {
		actionRegistry.registerAction(this.getActionName(), this);
	}

	/*
	 * By default the name of the action that's put into the registry is the
	 * simple name of the class. Can be overriden if necessary (but when would
	 * it be necessary?)
	 */
	protected String getActionName() {
		return this.getClass().getSimpleName();
	}

	/**
	 * Returns the parameter definition. By default there is no parameter
	 * definition. Expected to be overridden by the subclass
	 * 
	 * @return
	 */
	protected ParamsAndFiltersDefinition createParameterDefinition() {
		return new ParamsAndFiltersDefinition();
	}

	/**
	 * Returns the filter definition. By default there is no filter definition.
	 * Expected to be overridden by the subclass
	 * 
	 * @return
	 */
	protected ParamsAndFiltersDefinition createFilterDefinition() {
		return new ParamsAndFiltersDefinition();
	}

	/**
	 * Validates params and filters, and converts types from String to the
	 * appropriate type
	 * 
	 * @param p
	 * @param f
	 */
	private void validateAndConvertParametersAndFilters(ActionParameters p,
			ActionFilters f) {

		ValidationExceptionBuilder veb = new ValidationExceptionBuilder();

		Map<String, List<Object>> allFilters = f.getAllFilters();

		veb = validateInputs(FilterOrParam.param, p.getAllParameters(),
				getParameterDefinition().getValidations(), veb);
		veb = validateInputs(FilterOrParam.filter, allFilters,
				getFilterDefinition().getValidations(), veb);

		veb.buildAndThrowIfInvalid();
	}

	private ParamsAndFiltersDefinition getFilterDefinition() {

		if (this.filterDefinition == null) {
			filterDefinition = this.createFilterDefinition();
		}
		return filterDefinition;
	}

	private ParamsAndFiltersDefinition getParameterDefinition() {
		if (this.parameterDefinition == null) {
			parameterDefinition = this.createParameterDefinition();
			parameterDefinition = this
					.addAdditionalParameterDefinitions(parameterDefinition);
		}
		return parameterDefinition;
	}

	/**
	 * Allows subclasses to modify the parameter def upon creation
	 * 
	 * @param def
	 * @return
	 */
	protected ParamsAndFiltersDefinition addAdditionalParameterDefinitions(
			ParamsAndFiltersDefinition def) {
		return def;
	}

	/**
	 * Validates and converts inputs for this action. Side effect - will change
	 * reqParams from strings to objects if they're being converted
	 * 
	 * @param type
	 * @param reqParams
	 * @param validations
	 * @param veb
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private ValidationExceptionBuilder validateInputs(FilterOrParam type,
			Map reqParams, Map<String, List<AbstractValidation>> validations,
			ValidationExceptionBuilder veb) {

		// Tracks what validations have not been done. If anything is left over
		// in this set, it means that the user didn't provide it on the request.
		Set<String> unprocessedValidationKeys = new HashSet<String>(
				validations.keySet());

		// for each parameter that was provided on the request..
		for (Object keyObj : reqParams.keySet()) {
			String key = (String) keyObj;

			// assumes that every parameter has a validation
			if (!validations.containsKey(key)) {
				ValidationResponse resp = ValidationResponse
						.createInvalidResponse(String.format(
								"Not applicable for action '%s'.",
								this.getActionName()));
				veb.addInvalidValidation(type, resp, key);
			} else {

				unprocessedValidationKeys.remove(key);

				List<AbstractValidation> validationsForThisParam = validations
						.get(key);

				// validate this param with all validations. Track failures
				for (AbstractValidation v : validationsForThisParam) {

					Object value = reqParams.get(key);
					ValidationResponse resp;

					resp = v.validateInternal(value, reqParams);

					if (!resp.isValid()) {
						veb.addInvalidValidation(type, resp, key);
					} else {
						// it's valid, see if there's a conversion
						if (resp.getConvertedObj() != null) {
							// override the original value with the converted
							// value
							reqParams.put(key, resp.getConvertedObj());
						}
					}
				}
			}
		}

		// see if any of the unprocessed keys are required. These are keys that
		// are in the validation rules but were not on the request.
		for (String unprocessedValidationKey : unprocessedValidationKeys) {
			List<AbstractValidation> vals = validations
					.get(unprocessedValidationKey);

			for (AbstractValidation validation : vals) {
				// note - if a field is required, even optionally, it MUST
				// subclass RequiredValidation!

				if (validation instanceof RequiredValidation) {
					// null because it was provided by in the request
					ValidationResponse resp = validation.validateInternal(null,
							reqParams);
					MswsAssert.mswsAssert(resp != null,
							"Response from validation cannot be null: %s",
							validation.getClass().getName());
					if (!resp.isValid()) {
						veb.addInvalidValidation(type, resp,
								unprocessedValidationKey);
					}
				}
			}
		}
		return veb;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.registerWithActionRegistry();

		// populates the definitions at startup instead of at runtime. Will
		// help to determine errors upfront instead of waiting for when the
		// action is run.
		this.getFilterDefinition();
		this.getParameterDefinition();
	}

	public UuidGenerator getUuidGenerator() {
		return uuidGenerator;
	}

	public void setUuidGenerator(UuidGenerator uuidGenerator) {
		this.uuidGenerator = uuidGenerator;
	}

}
