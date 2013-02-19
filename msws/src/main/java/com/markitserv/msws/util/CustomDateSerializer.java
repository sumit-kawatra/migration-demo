/**
 * 
 */
package com.markitserv.msws.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.markitserv.msws.exceptions.ProgrammaticException;

/**
 * @author kiran.gogula
 *
 */
public class CustomDateSerializer extends JsonSerializer<DateTime>{

	 private static DateTimeFormatter formatter = DateTimeFormat.forPattern("YYYY-MM-dd");
	 Logger log = LoggerFactory.getLogger(CustomDateSerializer.class);

	 /**
	  * This overridden method serialize the Joda DatTime object to string.
	  */
	@Override
	public void serialize(DateTime value, JsonGenerator jgen,
			SerializerProvider provider)  {		
		try{
			
			jgen.writeString(formatter.print(value));
			
		}catch(Exception exception){
			log.error("Exception from CustomDateSerializer class", exception);
			throw new ProgrammaticException("Exception from CustomDateSerializer class", exception);
		}
		
	}
	
	

}
