package com.markitserv.ssa.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

@Component(value="myMessageConverter")
public class MyMessageConverter extends MappingJackson2HttpMessageConverter {
	
	Logger log = LoggerFactory.getLogger(MyMessageConverter.class);

	@Override
	protected void writeInternal(Object object, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		log.info("Using my message converter!!!");
		throw new RuntimeException("WTF");
		//super.writeInternal(object, outputMessage);
	}

	@Override
	protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
		log.info("Using my message converter!!!");
		return super.readInternal(clazz, inputMessage);
	}
	
	
}
