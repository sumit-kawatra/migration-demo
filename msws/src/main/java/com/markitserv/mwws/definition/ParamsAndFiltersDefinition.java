package com.markitserv.mwws.definition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.markitserv.mwws.validation.Validation;

public class ParamsAndFiltersDefinition {
	
	private Map<String, List<Validation>> validations = new HashMap<String, List<Validation>>();
	private Map<String, String> defaults = new HashMap<String, String>();

	public void addValidation(String key, Validation value) {
		if (!validations.containsKey(key)) {
			List<Validation> v = new Stack<Validation>();
			validations.put(key, v);
		}
		
		Stack<Validation> v = (Stack<Validation>) validations.get(key);
		v.push(value);
		validations.put(key, v);
	}
	
	public Map<String, List<Validation>> getValidations() {
		return validations;
	}
	
	public Map<String, String> getDefaults() {
		return defaults;
	}
	
	public void addDefault(String key, String value) {
		defaults.put(key, value);
	}
} 