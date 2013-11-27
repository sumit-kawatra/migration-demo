package com.markitserv.msws.validation;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * Ensures that a value is part of an enum.
 * 
 * @author roy.truelove
 * 
 * @param <T>
 */
@SuppressWarnings("rawtypes")
public class EnumValidation<T extends Enum> extends AbstractOptionalValidation {

	private T enumValue;
	private OneOfValidation oneOfValidator;

	/**
	 * Takes a value of an enum, any value. This is hackey. Need to find a way
	 * to pass in the class of that enum instead, and deprecate this.
	 * 
	 * @param enumValue
	 */
	public EnumValidation(T enumValue) {
		super();
		this.enumValue = enumValue;
		this.oneOfValidator = buildValueValuesFromEnum(enumValue);
	}

	@SuppressWarnings({ "unchecked" })
	private OneOfValidation buildValueValuesFromEnum(T enumValue) {

		LinkedList<String> asStrings = new LinkedList<String>();

		Set<? extends Enum> x = EnumSet.allOf(enumValue.getClass());

		for (Iterator<? extends Enum> iterator = x.iterator(); iterator
				.hasNext();) {

			T value = (T) iterator.next();
			asStrings.addLast(value.toString());
		}

		OneOfValidation v = new OneOfValidation(asStrings);

		return v;
	}

	@SuppressWarnings({ "unchecked", "static-access" })
	@Override
	protected ValidationResponse validate(Object target,
			Map<String, ? extends Object> otherValues) {

		ValidationResponse validation = oneOfValidator.validate(target,
				otherValues);

		if (validation.isValid()) {
			String enumValueName = (String) validation.getConvertedObj();
			Enum enumVal = enumValue.valueOf(enumValue.getClass(),
					enumValueName);
			validation.setConvertedObj(enumVal);
		}

		return validation;
	}

	@Override
	public String getDescription() {
		return oneOfValidator.getDescription();
	}
}
