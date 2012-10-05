package com.markitserv.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

public class SandboxBeanSerializerModifier extends BeanSerializerModifier {
	
	Logger log = LoggerFactory.getLogger(SandboxBeanSerializerModifier.class);
	
    static SandboxBeanSerializerModifier instance() {
        return new SandboxBeanSerializerModifier();
    }
    
	public SandboxBeanSerializerModifier() {
		super();
		log.debug("Creating new singleton instance of SandboxBeanSerializerModifier");
	}

	@Override
	public List<BeanPropertyWriter> changeProperties(
			SerializationConfig config, BeanDescription beanDesc,
			List<BeanPropertyWriter> beanProperties) {
		
		log.debug("Changing props.");
		
		return super.changeProperties(config, beanDesc, beanProperties);
	}

}
