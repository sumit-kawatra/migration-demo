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
		
		List<BeanPropertyDefinition> allProps = beanDesc.findProperties();
		log.debug("Serializing " + beanDesc.getBeanClass().getSimpleName());
		
		BeanPropertyWriter goodBPW = beanProperties.get(0);
		
		for (BeanPropertyDefinition bpw : beanDesc.findProperties()) {
		}
		
		return beanProperties;
	}

	/**
	 * If a bean is annotated with RestLink, don't serialize its content,
	 * but instead serialize it as a link
	 * @param prop
	 */
	private void serializeBeanAsLinkIfAnnotated(
			BeanPropertyDefinition prop) {
		RestLink attr = null;
		attr = prop.getGetter().getAnnotation(RestLink.class);
		if (attr != null) { // if it's a RESTLink
			BeanPropertyWriter bpw = new BeanPropertyWriter(
			prop.assignSerializer(new LinkSerializer(attr
					.relUri()));
		}
	}
} 