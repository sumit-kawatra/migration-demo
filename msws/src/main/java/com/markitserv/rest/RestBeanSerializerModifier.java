package com.markitserv.rest;

import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.util.NameTransformer;

/**
 * Right now this class does nothing. Keeping it here for when (if) we need to
 * extend Jackson. Should be removed if it turns out not to be needed.
 * 
 * @author roy.truelove
 * 
 */
public class RestBeanSerializerModifier extends SafeBeanSerializerModifier {

	Logger log = LoggerFactory.getLogger(RestBeanSerializerModifier.class);

	static RestBeanSerializerModifier instance() {
		return new RestBeanSerializerModifier();
	}

	public RestBeanSerializerModifier() {
		super();
	}

	@Override
	public List<BeanPropertyWriter> safeChangeProperties(
			SerializationConfig config, BeanDescription beanDesc,
			List<BeanPropertyWriter> beanProperties) {

		List<BeanPropertyWriter> newBeanProperties = new ArrayList<BeanPropertyWriter>(
				beanProperties.size());

		for (BeanPropertyWriter bpw : beanProperties) {

			bpw = prependRestReferencesWithAt(bpw);
			newBeanProperties.add(bpw);
		}

		return super.safeChangeProperties(config, beanDesc, newBeanProperties);
	}

	/**
	 * If the getter is annotated with RestReference, prepend it with an at
	 * sign, to signify that it is an id
	 * 
	 * @param bpw
	 * @return
	 */
	private BeanPropertyWriter prependRestReferencesWithAt(
			BeanPropertyWriter bpw) {
		RestReference rr = null;

		// see if the property has a RestReference annotation
		AnnotatedMember member = bpw.getMember();
		if (member != null) {
			AnnotatedElement annotated = member.getAnnotated();
			if (annotated != null) {
				rr = annotated.getAnnotation(RestReference.class);
			}
		}

		if (rr != null) {
			// why underscore? Coffeescript doesn't like @, and $ looks
			// like an Angular global.
			bpw = bpw.rename(NameTransformer.simpleTransformer("_", ""));
		}
		return bpw;
	}
}