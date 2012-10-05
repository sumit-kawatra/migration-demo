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
import com.markitserv.rest.SandboxBeanSerializerModifierModule;

@Service("customObjectMapper")
public class CustomObjectMapper extends ObjectMapper implements
		InitializingBean {

	@Override
	public boolean canSerialize(Class<?> type) {
		log.info("Can serialize");
		return super.canSerialize(type);
	}

	Logger log = LoggerFactory.getLogger(CustomObjectMapper.class);

	@Override
	public void afterPropertiesSet() throws Exception {
		this.registerModule(new SandboxBeanSerializerModifierModule());
		log.info("Using custom Jackson JSON Object Mapper");
	}

	@Override
	public JsonFactory getJsonFactory() {
		log.info("SOMEthing is happening at least.");
		return super.getJsonFactory();
	}

	@Override
	public void writeValue(JsonGenerator jgen, Object value)
			throws IOException, JsonGenerationException, JsonMappingException {
		log.info("SOMEthing is happening at least.");
		super.writeValue(jgen, value);
	}
}
