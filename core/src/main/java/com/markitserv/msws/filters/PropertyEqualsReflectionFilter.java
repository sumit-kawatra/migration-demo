package com.markitserv.msws.filters;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import com.markitserv.msws.exceptions.ProgrammaticException;

/**
 * Uses reflection to filter when a given property equals the provided value. If
 * this is not 'fast enough', then you should make a custom filter that does not
 * use reflection.
 * 
 * @author roy.truelove
 * 
 */
public class PropertyEqualsReflectionFilter<T,R> extends
		AbstractFilter<T> {

	private String propertyName;
	private R value;

	public static <T, R> List<T> filter(List<T> toFilter,
			String propertyName, R value) {

		PropertyEqualsReflectionFilter<T, R> f = new PropertyEqualsReflectionFilter<T, R>(
				propertyName, value);
		return f.filter(toFilter);
	}

	private PropertyEqualsReflectionFilter(String propertyName, R value) {

		super();
		this.propertyName = propertyName;
		this.value = value;
	}

	@Override
	protected boolean shouldBeFilteredOut(T item) {

		Object propValue;
		
		try {
			propValue = PropertyUtils.getProperty(item, propertyName);
			
		} catch (Exception e) {
			throw new ProgrammaticException(
					"Could not filter by property %s for class type %s.", e,
					propertyName, item.getClass().getSimpleName());
		}
		
		boolean same = !propValue.equals(value);
		return same;
	}
}