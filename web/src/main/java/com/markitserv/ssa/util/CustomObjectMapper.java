// TODO move this to the ms-rest library
package com.markitserv.ssa.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.markitserv.rest.RestBeanSerializerModifierModule;

@Service("customObjectMapper")
public class CustomObjectMapper extends ObjectMapper implements
		InitializingBean {

	Logger log = LoggerFactory.getLogger(CustomObjectMapper.class);

	@Override
	public void afterPropertiesSet() throws Exception {
		this.registerModule(new RestBeanSerializerModifierModule());
		log.info("Using custom Jackson JSON Object Mapper");
	}
}
