package com.markitserv.msws.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.apache.commons.lang3.StringUtils;

import com.markitserv.msws.util.MswsAssert;
import com.markitserv.msws.validation.AbstractOptionalValidation;
import com.markitserv.msws.validation.AbstractValidation;
import com.markitserv.msws.validation.ForEachValidator;

public class ParamsAndFiltersDefinition {

	private Map<String, List<AbstractValidation>> validations = new HashMap<String, List<AbstractValidation>>();
	private Map<String, Object> defaults = new HashMap<String, Object>();

	public ParamsAndFiltersDefinition addValidation(String key,
			AbstractValidation value) {

		String firstLetter = StringUtils.left(key, 1);
		MswsAssert.mswsAssert(firstLetter.equals(firstLetter.toUpperCase()),
				"Parameter names must be capitalized");

		/*
		 * MswsAssert .mswsAssert( !(value instanceof
		 * AbstractConversionValidation), "Cannot perform validation for key '"
		 * + key + "'.  " + "Class '" + value.getClass().getSimpleName() +
		 * "' requires that when you register with the Param / Filter definition, "
		 * + "you use 'addValidationAndConvertion' instead of 'addValidation'");
		 */

		if (!validations.containsKey(key)) {
			List<AbstractValidation> v = new Stack<AbstractValidation>();
			validations.put(key, v);
		}

		Stack<AbstractValidation> v = (Stack<AbstractValidation>) validations
				.get(key);
		v.push(value);
		validations.put(key, v);

		return this;
	}

	public ParamsAndFiltersDefinition addValidationForEachListElement(
			String key, AbstractValidation validation) {
		this.addValidation(key, new ForEachValidator(validation));

		return this;
	}

	@Deprecated
	public void addValidation(CommonParamKeys key,
			AbstractOptionalValidation value) {
		this.addValidation(key.toString(), value);
	}

	@Deprecated
	public void addValidationAndConversion(String key,
			AbstractOptionalValidation value) {

		this.addValidation(key, value);
	}

	@Deprecated
	public void addValidation(CommonParamKeys key, AbstractValidation value) {

		this.addValidation(key.toString(), value);
	}

	public Map<String, List<AbstractValidation>> getValidations() {
		return validations;
	}

	public Map<String, Object> getDefaultParams() {
		return defaults;
	}

	/**
	 * Use addDefaultParamValue
	 * 
	 * @param key
	 * @param value
	 */
	@Deprecated
	public void addDefaultParam(String key, Object value) {
		defaults.put(key, value);
	}

	public ParamsAndFiltersDefinition addDefaultParamValue(String key,
			Object value) {
		defaults.put(key, value);
		return this;
	}

	/**
	 * Use addDefaultParamValue with string
	 * 
	 * @param key
	 * @param value
	 */
	@Deprecated
	public void addDefaultParam(CommonParamKeys key, Object value) {
		this.addDefaultParam(key.toString(), value);
	}

	public boolean isDefaultParamSet(String key) {
		return this.defaults.containsKey(key);
	}

	/**
	 * Use 'addAll' instead
	 * 
	 * @param otherDefinition
	 */
	@Deprecated
	public void mergeWith(ParamsAndFiltersDefinition otherDefinition) {
		this.addAll(otherDefinition);
	}

	public ParamsAndFiltersDefinition addAll(
			ParamsAndFiltersDefinition otherDefinition) {

		// Merge other validations
		Map<String, List<AbstractValidation>> otherValidations = otherDefinition
				.getValidations();

		for (String otherValidationKey : otherValidations.keySet()) {

			List<AbstractValidation> validationsForThisKey = otherValidations
					.get(otherValidationKey);

			for (AbstractValidation otherValidation : validationsForThisKey) {
				this.addValidation(otherValidationKey, otherValidation);
			}
		}
		// Merge defaults. Defaults set on this definition will override those
		// of the other

		Map<String, Object> otherDefaults = otherDefinition.getDefaultParams();

		Set<String> otherDefaultKeys = otherDefaults.keySet();
		for (String otherDefaultKey : otherDefaultKeys) {
			if (!this.isDefaultParamSet(otherDefaultKey)) {
				this.addDefaultParam(otherDefaultKey,
						otherDefaults.get(otherDefaultKey));
			}
		}

		return this;
	}
}