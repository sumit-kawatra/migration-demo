package com.markitserv.rest;

import java.io.IOException;
import java.lang.reflect.AnnotatedElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class LinkSerializer extends JsonSerializer<Object> {
	
	Logger log = LoggerFactory.getLogger(LinkSerializer.class);
	
	@Override
	public void serialize(Object value, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonGenerationException {
		
		jgen.writeObject(value);
	}
}
