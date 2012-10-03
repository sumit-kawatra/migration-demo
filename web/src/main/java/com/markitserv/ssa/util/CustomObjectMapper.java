// TODO move this to the ms-rest library
package com.markitserv.ssa.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service("customObjectMapper")
public class CustomObjectMapper extends ObjectMapper implements InitializingBean {
	
	Logger log = LoggerFactory.getLogger(CustomObjectMapper.class);

	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("Using custom Jackson JSON Object Mapper");
	}
}
