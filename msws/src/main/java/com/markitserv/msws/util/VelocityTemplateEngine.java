/**
 * 
 */
package com.markitserv.msws.util;

import java.io.StringWriter;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.markitserv.msws.exceptions.ProgrammaticException;
import com.markitserv.msws.internal.MswsAssert;

/**
 * @author kiran.gogula
 *
 */
@Service
public class VelocityTemplateEngine implements InitializingBean{
	
	private static final String CLASS_PATH = "classpath";
	private static final String CLASS_PATH_RESOURCE_LOADER = "classpath.resource.loader.class";
	
	private VelocityEngine velocityEngine = null;
	
	Logger log = LoggerFactory.getLogger(VelocityTemplateEngine.class);
	
	
	/**
	 * <p>This method loads the given velocity template and populate the values for the 
	 *    Corresponding place holders from the map.</p>
	 * @param templateName
	 * @param templatePlaceholder
	 * @param list
	 * @return String  
	 */
	public String generateStringFromTemplate(String templateName, Map<String, Object> paramMap){
		StringWriter stringWriter = null;
		try{
			 MswsAssert.mswsAssert(StringUtils.isNotBlank(templateName), "Template Name must be set");
			 MswsAssert.mswsAssert(paramMap != null, "Param map must be set");
			 // get the template from the given template name
			 Template template = velocityEngine.getTemplate("/emailTemplates/"+templateName+".vm");
			
			 // Create a context and add data to the template place holder
			 VelocityContext context = new VelocityContext();
			 
			 for (String key : paramMap.keySet()) {
				 context.put(key, paramMap.get(key)); 
			 }
			 
			 stringWriter = new StringWriter();
			 template.merge(context, stringWriter );
			 return stringWriter.toString();
			 
		}catch(Exception exception){
			log.error("Exception from getEmailTemplateBody method", exception);	
			throw new ProgrammaticException("Exception from VelocityTemplateEngine", exception);
		}
	}
	
	
	

	@Override
	/**
	 * <p>This method initialize the VelocityEngine. </p>
	 */
	public void afterPropertiesSet() {
		try{
			 // Initializes Velocity engine
			 velocityEngine = new VelocityEngine();
			 velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, CLASS_PATH);
			 velocityEngine.setProperty(CLASS_PATH_RESOURCE_LOADER, ClasspathResourceLoader.class.getName());
			 velocityEngine.init();
		}catch(Exception exception){
			throw new ProgrammaticException("Exception from VelocityTemplateEngine", exception);
		}		
	}
	

}
