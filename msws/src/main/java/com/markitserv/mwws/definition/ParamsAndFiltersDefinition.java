package com.markitserv.mwws.definition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.markitserv.mwws.validation.AbstractValidation;

public class ParamsAndFiltersDefinition {
	
	private Map<String, List<AbstractValidation>> validations = new HashMap<String, List<AbstractValidation>>();
	private Map<String, String> defaults = new HashMap<String, String>();

	public void addValidation(String key, AbstractValidation value) {
		if (!validations.containsKey(key)) {
			List<AbstractValidation> v = new Stack<AbstractValidation>();
			validations.put(key, v);
		}
		
		Stack<AbstractValidation> v = (Stack<AbstractValidation>) validations.get(key);
		v.push(value);
		validations.put(key, v);
	}
	
	public Map<String, List<AbstractValidation>> getValidations() {
		return validations;
	}
	
	public Map<String, String> getDefaults() {
		return defaults;
	}
	
	public void addDefault(String key, String value) {
		defaults.put(key, value);
	}
} 