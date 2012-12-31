package com.markitserv.hawthorne.disabled;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:META-INF/spring/hawthorne-context.xml")
public class SpringContextDumper {
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ApplicationContext context;

	@Test
	public void dumpContext() {
		
		log.info("** START DUMP**");
		
		String[] beanDefNames = context.getBeanDefinitionNames();
		Arrays.sort(beanDefNames);

		for (String beanName : beanDefNames) {
			
			Object bean = context.getBean(beanName);
			
			log.info("{}: {}", beanName, bean.getClass().getCanonicalName());
		}
		
		log.info("** END DUMP**");
	}
}