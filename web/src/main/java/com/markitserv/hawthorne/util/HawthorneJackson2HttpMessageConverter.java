package com.markitserv.hawthorne.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

public class HawthorneJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
	
	Logger log = LoggerFactory.getLogger(HawthorneJackson2HttpMessageConverter.class);

	@Override
	protected void writeInternal(Object object, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		
		// Jackson doesn't seem to log any of its errors, it just silently throws
		// a 500 to the server.  This will log them, at least, and rethrow.
		try {
			super.writeInternal(object, outputMessage);
		} catch (Exception e) {
			log.error("Could not serialize object.  Rethrowing the following exception:", e);
			throw new HttpMessageNotWritableException(e.getLocalizedMessage());
		}
	}

	@Override
	protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
		return super.readInternal(clazz, inputMessage);
	}
}
