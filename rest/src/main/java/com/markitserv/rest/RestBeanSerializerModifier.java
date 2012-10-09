package com.markitserv.rest;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

/**
 * Right now this class does nothing.  Keeping it here for when (if)
 * we need to extend Jackson.  Should be removed if it turns out not
 * to be needed.
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
		
		return super.safeChangeProperties(config, beanDesc, beanProperties);
	}
} 