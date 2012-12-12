package com.markitserv.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerBuilder;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

/**
 * The default BeanSerializerModifier does not log runtime errors, it
 * simply fails serialization and doesnt say why.  This class wraps all BeanSerializerModifiers
 * methods in a try catch to at least log the error.  It should be used for
 * all instances of BeanSerializerModifier extensions.  Never extend 
 * BeanSerializerModifer directly, instead extend these safe versions.
 * @author roy.truelove
 *
 */
public abstract class SafeBeanSerializerModifier extends BeanSerializerModifier {
	
	Logger log = LoggerFactory.getLogger(SafeBeanSerializerModifier.class);

	@Override
	public List<BeanPropertyWriter> changeProperties(
			SerializationConfig config, BeanDescription beanDesc,
			List<BeanPropertyWriter> beanProperties) {
		List<BeanPropertyWriter> changeProperties = null;
		try {
			changeProperties = this.safeChangeProperties(config, beanDesc, beanProperties);
		} catch (Exception e) {
			log.error("Could not change properties for bean " + beanDesc, e);
			throw new RestRuntimeException(e);
		}
		return changeProperties;
	}

	@Override
	public List<BeanPropertyWriter> orderProperties(SerializationConfig config,
			BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
		List<BeanPropertyWriter> safeOrderProperties;
		try {
			safeOrderProperties = this.safeOrderProperties(config, beanDesc, beanProperties);
		} catch (Exception e) {
			log.error("Could not order properties for bean " + beanDesc, e);
			throw new RestRuntimeException(e);
		}
		return safeOrderProperties;
	}

	@Override
	public BeanSerializerBuilder updateBuilder(SerializationConfig config,
			BeanDescription beanDesc, BeanSerializerBuilder builder) {
		// TODO Auto-generated method stub
		BeanSerializerBuilder updateBuilder = null;
		try {
			updateBuilder = this.safeUpdateBuilder(config, beanDesc, builder);
		} catch (Exception e) {
			log.error("Could not update builder for bean " + beanDesc, e);
			throw new RestRuntimeException(e);
		}
		return updateBuilder;
	}

	@Override
	public JsonSerializer<?> modifySerializer(SerializationConfig config,
			BeanDescription beanDesc, JsonSerializer<?> serializer) {
		// TODO Auto-generated method stub
		JsonSerializer<?> modifySerializer = null;
		try {
			modifySerializer = super.modifySerializer(config, beanDesc, serializer);
		} catch (Exception e) {
			log.error("Could not modify serializer for bean " + beanDesc, e);
			throw new RestRuntimeException(e);
		}
		return modifySerializer;
	}
	
	public List<BeanPropertyWriter> safeChangeProperties(
			SerializationConfig config, BeanDescription beanDesc,
			List<BeanPropertyWriter> beanProperties) {
		return super.changeProperties(config, beanDesc, beanProperties);
	}

	public List<BeanPropertyWriter> safeOrderProperties(SerializationConfig config,
			BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
		return super.orderProperties(config, beanDesc, beanProperties);
	}

	public BeanSerializerBuilder safeUpdateBuilder(SerializationConfig config,
			BeanDescription beanDesc, BeanSerializerBuilder builder) {
		return super.updateBuilder(config, beanDesc, builder);
	}

	public JsonSerializer<?> safeModifySerializer(SerializationConfig config,
			BeanDescription beanDesc, JsonSerializer<?> serializer) {
		return super.modifySerializer(config, beanDesc, serializer);
	}
} 