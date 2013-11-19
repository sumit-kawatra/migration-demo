package com.markitserv.msws.action.resp;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ActionDescription implements Comparable<ActionDescription>{

	private String name;
	private String description;
	
	private Map<String, List<String>> validations = new HashMap<String, List<String>>();
	private Map<String, List<String>> filters = new HashMap<String, List<String>>();
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void addValidation(String paramName, String desc) {
		
		List<String> validationsForParams = validations.get(paramName);
		if (validationsForParams == null) {
			validationsForParams = new LinkedList<String>();
		}
		
		validationsForParams.add(desc);
		
		this.validations.put(paramName, validationsForParams);
	}
	
	public void addFilter(String filterName, String desc) {
		
		List<String> filtersForParams = filters.get(filterName);
		if (filtersForParams == null) {
			filtersForParams = new LinkedList<String>();
		}
		
		filtersForParams.add(desc);
		
		this.filters.put(filterName, filtersForParams);
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, List<String>> getValidations() {
		return validations;
	}

	public Map<String, List<String>> getFilters() {
		return filters;
	}
	
	@Override
	public int compareTo(ActionDescription o) {
		return this.getName().compareTo(o.getName());
	}

}
