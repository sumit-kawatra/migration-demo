package com.markitserv.ssa.util;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

@Service(value="customJacksonConverter")
public class CustomObjectMapper extends ObjectMapper {
	
	// Right now nothing here.  Might be useful later?  If not, get rid of this and
	// the corresponding Spring config

}
