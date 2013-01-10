package com.markitserv.mwws.filters;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import com.markitserv.mwws.Type;
import com.markitserv.mwws.exceptions.ProgrammaticException;

/**
 * Uses reflection to do a substring filter. Note that if this is not 'fast
 * enough', then you should make a custom filter that does not use reflection.
 * 
 * @author roy.truelove
 * 
 */
public class SubstringReflectionFilter<T extends Type> extends
		AbstractFilter<T> {

	private String propertyName;
	private String substr;
	private boolean caseSentitive;

	public static <T extends Type> List<T> filter(List<T> toFilter,
			String propertyName, String substr, boolean caseSenstitive) {

		SubstringReflectionFilter<T> x = new SubstringReflectionFilter<T>(
				propertyName, substr, caseSenstitive);
		return x.filter(toFilter);

	}
	
	public static <T extends Type> List<T> filter(List<T> toFilter,
			String propertyName, String substr) {

		SubstringReflectionFilter<T> x = new SubstringReflectionFilter<T>(
				propertyName, substr);
		return x.filter(toFilter);

	}

	private SubstringReflectionFilter(String propertyName, String substr,
			boolean caseSenstitive) {

		super();

		this.propertyName = propertyName;
		this.substr = substr;
		this.caseSentitive = caseSenstitive;

		if (caseSenstitive) {
			this.propertyName = this.propertyName.toUpperCase();
		}
	}

	/**
	 * Runs case insensitive filter
	 * 
	 * @param propertyName
	 * @param substr
	 */
	private SubstringReflectionFilter(String propertyName, String substr) {
		this(propertyName, substr, false);
	}

	@Override
	protected boolean shouldBeFilteredOut(T item) {

		String propValue;
		try {
			propValue = BeanUtils.getSimpleProperty(item, propertyName);
		} catch (Exception e) {
			throw new ProgrammaticException(
					"Could not filter by property %s for class type %s.", e,
					propertyName, item.getClass().getSimpleName());
		}

		if (caseSentitive) {
			propValue = propValue.toUpperCase();
		}

		return !StringUtils.contains(propValue, substr);
	}
}