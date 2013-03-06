package com.markitserv.hawthorne.oneoffs;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.markitserv.hawthorne.HawthorneBackend;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/spring/hawthorne-context.xml")
public class UserListCacheTest {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ApplicationContext context;

	@Autowired
	private HawthorneBackend hardcodedBkEnd;

	@Before
	public void init() {
		hardcodedBkEnd = context.getBean("hardcodedHawthorneBackend",
				HawthorneBackend.class);
	}

	@Test
	public void userCacheTest() {
		log.debug("FIRST TIME hardcodedHawthorneBackend.getAllUsers() Calling ...");
		hardcodedBkEnd.getAllUsers();
		log.debug("SECONDTIME hardcodedHawthorneBackend.getAllUsers() Calling ...");
		hardcodedBkEnd.getAllUsers();
	}
}
