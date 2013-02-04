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
import com.markitserv.msws.validation.AbstractValidation;
import com.markitserv.msws.validation.RequiredValidation;
import com.markitserv.msws.validation.ValidationExceptionBuilder;
import com.markitserv.msws.validation.ValidationResponse;
import com.markitserv.msws.validation.ValidationExceptionBuilder.InvalidType;
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
		validate(parameters, filters);

		ActionResult result = this.performAction(parameters, filters);

		validateResult(result);

		return result;
	}

	protected ActionResult addResponseMetadata(ActionResult result) {
		result.getMetaData().setRequestId(uuidGenerator.generateUuid());
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

	private void validate(ActionParameters p, ActionFilters f) {

		ValidationExceptionBuilder veb = new ValidationExceptionBuilder();

		veb = validateParameters(InvalidType.param, p.getAllParameters(),
				getParameterDefinition().getValidations(), veb);
		veb = validateParameters(InvalidType.filter, f.getAllFilters(),
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
	 * Validates inputs for this action
	 * 
	 * @param type
	 * @param reqParams
	 * @param validations
	 * @param veb
	 * @return
	 */
	private ValidationExceptionBuilder validateParameters(InvalidType type,
			Map<String, ? extends Object> reqParams,
			Map<String, List<AbstractValidation>> validations,
			ValidationExceptionBuilder veb) {

		// Tracks what validations have not been done. If anything is left over
		// in this set, it means that the user didn't provide it on the request.
		Set<String> unprocessedValidationKeys = new HashSet<String>(
				validations.keySet());

		// for each parameter that was provided on the request..
		for (String key : reqParams.keySet()) {

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
					ValidationResponse resp = v.isValidInternal(value,
							reqParams);
					if (!resp.isValid()) {
						veb.addInvalidValidation(type, resp, key);
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
					ValidationResponse resp = validation.isValidInternal(null,
							reqParams);
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
	}

	public UuidGenerator getUuidGenerator() {
		return uuidGenerator;
	}

	public void setUuidGenerator(UuidGenerator uuidGenerator) {
		this.uuidGenerator = uuidGenerator;
	}

}