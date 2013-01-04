package com.markitserv.mwws.action;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.markitserv.mwws.definition.ParamsAndFiltersDefinition;
import com.markitserv.mwws.exceptions.NotYetImplementedException;
import com.markitserv.mwws.exceptions.ProgrammaticException;
import com.markitserv.mwws.internal.UuidGenerator;
import com.markitserv.mwws.validation.RequiredValidation;
import com.markitserv.mwws.validation.AbstractValidation;
import com.markitserv.mwws.validation.ValidationExceptionBuilder;
import com.markitserv.mwws.validation.ValidationExceptionBuilder.InvalidType;
import com.markitserv.mwws.validation.ValidationResponse;

public abstract class AbstractAction implements InitializingBean {

	@Autowired
	private ActionRegistry actionRegistry;

	@Autowired
	private UuidGenerator uuidGenerator;

	public ActionResult performAction(ActionCommand command) {

		ActionParameters parameters = command.getParameters();
		ActionFilters filters = command.getFilters();

		// applyDefaults(parameters, filters);
		validateParametersAndFilters(parameters, filters);

		ActionResult result = this.performAction(parameters, filters);

		result.getMetadata().setRequestId(uuidGenerator.generateUuid());

		validateResult(result);

		return result;
	}

	private void applyDefaults(ActionParameters parameters,
			ActionFilters filters) {
		throw NotYetImplementedException.standardException();
	}

	/**
	 * Ensures that the result is valid before sending back to the client
	 * 
	 * @param result
	 */
	private void validateResult(ActionResult result) {

		String failureMsg = "Failed to validate ActionResult.  ";

		// either collection OR item needs to be set.
		if (result.getCollection() == null && result.getItem() == null) {
			throw new ProgrammaticException(failureMsg
					+ "Either the collection or the item must be set.");
		}

		// cannot have both collection and item set at the same time.
		if (result.getCollection() != null && result.getItem() != null) {
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
	protected ParamsAndFiltersDefinition getParameterDefinition() {
		return new ParamsAndFiltersDefinition();
	}

	/**
	 * Returns the filter definition. By default there is no filter definition.
	 * Expected to be overridden by the subclass
	 * 
	 * @return
	 */
	protected ParamsAndFiltersDefinition getFilterDefinition() {
		return new ParamsAndFiltersDefinition();
	}

	private void validateParametersAndFilters(ActionParameters p,
			ActionFilters f) {

		ValidationExceptionBuilder veb = new ValidationExceptionBuilder();

		veb = validateParameters(InvalidType.param, p.getAllParameters(),
				getParameterDefinition().getValidations(), veb);
		veb = validateParameters(InvalidType.filter, f.getAllFilters(),
				getFilterDefinition().getValidations(), veb);

		veb.buildAndThrowIfInvalid();
	}

	/**
	 * Validates inputs for this action
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

				List<AbstractValidation> validationsForThisParam = validations.get(key);

				// validate this param with all validations. Track failures
				for (AbstractValidation v : validationsForThisParam) {
					Object value = reqParams.get(key);
					ValidationResponse resp = v.isValidInternal(value, reqParams);
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
