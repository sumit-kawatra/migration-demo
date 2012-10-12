package com.markitserv.rest;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.gaffer.PropertyUtil;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.util.BeanUtil;

/**
 * Returns the id of the bean instead of actually returning the bean
 * 
 * @author roy.truelove
 * 
 */
public class RestReferenceSerializer extends JsonSerializer<Object> {

	Logger log = LoggerFactory.getLogger(RestReferenceSerializer.class);

	@Override
	public void serialize(Object value, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {

		long id = 0;

		try {
			id = (Long) PropertyUtils.getProperty(value, "id");
		} catch (Exception e) {
			throw new RestRuntimeException("Expected bean of type "
					+ value.getClass().getCanonicalName()
					+ " to have a getter for property 'id'.", e);
		}
		jgen.writeNumber(id);
	}
}
