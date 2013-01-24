package com.markitserv.msws.definition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import com.markitserv.msws.action.CommonParamKeys;
import com.markitserv.msws.validation.AbstractValidation;

public class ParamsAndFiltersDefinition {

	private Map<String, List<AbstractValidation>> validations = new HashMap<String, List<AbstractValidation>>();
	private Map<String, Object> defaults = new HashMap<String, Object>();

	public void addValidation(String key, AbstractValidation value) {
		if (!validations.containsKey(key)) {
			List<AbstractValidation> v = new Stack<AbstractValidation>();
			validations.put(key, v);
		}

		Stack<AbstractValidation> v = (Stack<AbstractValidation>) validations
				.get(key);
		v.push(value);
		validations.put(key, v);
	}
	
	public void addValidation(CommonParamKeys key, AbstractValidation value) {
		this.addValidation(key.toString(), value);
	}
	
	public Map<String, List<AbstractValidation>> getValidations() {
		return validations;
	}

	public Map<String, Object> getDefaultParams() {
		return defaults;
	}

	public void addDefaultParam(String key, Object value) {
		defaults.put(key, value);
	}
	
	public void addDefaultParam(CommonParamKeys key, Object value) {
		this.addDefaultParam(key.toString(), value);
	}

	public boolean isDefaultParamSet(String key) {
		return this.defaults.containsKey(key);
	}

	public void mergeWith(ParamsAndFiltersDefinition otherDefinition) {

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
	}
}