package com.markitserv.msws.action;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.markitserv.msws.definition.ParamsAndFiltersDefinition;
import com.markitserv.msws.exceptions.ProgrammaticException;
import com.markitserv.msws.internal.UuidGenerator;
import com.markitserv.msws.validation.AbstractConversionValidation;
import com.markitserv.msws.validation.AbstractValidation;
import com.markitserv.msws.validation.RequiredValidation;
import com.markitserv.msws.validation.ValidationExceptionBuilder;
import com.markitserv.msws.validation.ValidationAndConversionResponse;
import com.markitserv.msws.validation.ValidationExceptionBuilder.FilterOrParam;

@Service
public abstract class AbstractAction implements InitializingBean {

	@Autowired
	private ActionRegistry actionRegistry;

	@Autowired
	private UuidGenerator uuidGenerator;

	private ParamsAndFiltersDefinition parameterDefinition;

	private ParamsAndFiltersDefinition filterDefinition;

	public ActionResult performAction(ActionCommand command) {

		ActionParameters parameters = command.getParameters();
		ActionFilters filters = command.getFilters();

		applyDefaults(parameters, filters);
		validateAndConvertParametersAndFilters(parameters, filters);

		ActionResult result = this.performAction(parameters, filters);

		validateResult(result);

		return result;
	}

	private void applyDefaults(ActionParameters parameters, ActionFilters filters) {

		Map<String, Object> paramDefaults = this.getParameterDefinition()
				.getDefaultParams();

		Map<String, Object> params = parameters.getAllParameters();

		for (String defaultKey : paramDefaults.keySet()) {
			if (!params.containsKey(defaultKey)) {
				parameters.addParameter(defaultKey, paramDefaults.get(defaultKey));
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
		if (result.getList() == null && result.getItem() == null) {
			throw new ProgrammaticException(failureMsg
					+ "Either the collection or the item must be set.");
		}

		// cannot have both collection and item set at the same time.
		if (result.getList() != null && result.getItem() != null) {
			throw new ProgrammaticException(failureMsg
					+ "Cannot set both the collection and the item.");
		}
	}

	/**
	 * Where all the fun happens
	 * 
	 * @param params
	 * @param filters
	 * @return
	 */
	protected abstract ActionResult performAction(ActionParameters params,
			ActionFilters filters);

	/**
	 * Registers this action w/ the Registry upon startup
	 */
	private void registerWithActionRegistry() {
		actionRegistry.registerAction(this.getActionName(), this);
	}

	/*
	 * By default the name of the action that's put into the registry is the
	 * simple name of the class. Can be overriden if necessary (but when would it
	 * be necessary?)
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
	private void validateAndConvertParametersAndFilters(ActionParameters p, ActionFilters f) {

		ValidationExceptionBuilder veb = new ValidationExceptionBuilder();
		
		Map<String, List<String>> allFilters = f.getAllFilters();

		veb = validateInputs(FilterOrParam.param, p.getAllParameters(),
				getParameterDefinition().getValidations(), veb);
		veb = validateInputs(FilterOrParam.filter, allFilters, getFilterDefinition()
				.getValidations(), veb);

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
			Map reqParams,
			Map<String, List<AbstractValidation>> validations, ValidationExceptionBuilder veb) {

		// Tracks what validations have not been done. If anything is left over
		// in this set, it means that the user didn't provide it on the request.
		Set<String> unprocessedValidationKeys = new HashSet<String>(validations.keySet());

		// for each parameter that was provided on the request..
		for (Object keyObj : reqParams.keySet()) {
			String key = (String)keyObj;

			// assumes that every parameter has a validation
			if (!validations.containsKey(key)) {
				ValidationAndConversionResponse resp = ValidationAndConversionResponse
						.createInvalidResponse(String.format("Not applicable for action '%s'.",
								this.getActionName()));
				veb.addInvalidValidation(type, resp, key);
			} else {

				unprocessedValidationKeys.remove(key);

				List<AbstractValidation> validationsForThisParam = validations.get(key);

				// validate this param with all validations. Track failures
				for (AbstractValidation v : validationsForThisParam) {

					Object value = reqParams.get(key);
					ValidationAndConversionResponse resp;

					if (v instanceof AbstractConversionValidation) {
						AbstractConversionValidation validatorAndConverter = (AbstractConversionValidation) v;
						resp = validatorAndConverter
								.internalValidateAndConvert(value, reqParams);
						
						// override the original value with the converted value
						reqParams.put(key, resp.getConvertedObj());
						
					} else {
						resp = v.validateInternal(value, reqParams);
					}

					if (!resp.isValid()) {
						veb.addInvalidValidation(type, resp, key);
					}
				}
			}
		}

		// see if any of the unprocessed keys are required. These are keys that
		// are in the validation rules but were not on the request.
		for (String unprocessedValidationKey : unprocessedValidationKeys) {
			List<AbstractValidation> vals = validations.get(unprocessedValidationKey);

			for (AbstractValidation validation : vals) {
				// note - if a field is required, even optionally, it MUST
				// subclass RequiredValidation!

				if (validation instanceof RequiredValidation) {
					// null because it was provided by in the request
					ValidationAndConversionResponse resp = validation.validateInternal(null,
							reqParams);
					if (!resp.isValid()) {
						veb.addInvalidValidation(type, resp, unprocessedValidationKey);
					}
				}
			}
		}
		return veb;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.registerWithActionRegistry();
		
		// populates the definitions at startup instead of at runtime.  Will
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
