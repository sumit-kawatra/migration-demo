package com.markitserv.hawthorne.oneoffs;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:META-INF/spring/hawthorne-context.xml")
public class EnvDumper {
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private Environment env;

	@Test
	public void dumpEnv() {
		log.error(env.getProperty("foo"));		
	}
}