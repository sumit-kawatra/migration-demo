package com.markitserv.rest;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * In some cases we use a long value of zero to represent null, and in those
 * cases we don't want the value to be serialized.
 * 
 * @author roy.truelove
 * 
 */
public class RestReferenceSerializer extends JsonSerializer<RestReference> {
	
	Logger log = LoggerFactory.getLogger(RestReferenceSerializer.class);

	@Override
	public void serialize(RestReference value, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		
		jgen.writeStartObject();
		if (value.getId() != 0l) {
			jgen.writeNumberField("id", value.getId());
		}
		if (!StringUtils.isBlank(value.getUri())) {
			jgen.writeStringField("uri", value.getUri());
		}
		jgen.writeEndObject();
	}
}
