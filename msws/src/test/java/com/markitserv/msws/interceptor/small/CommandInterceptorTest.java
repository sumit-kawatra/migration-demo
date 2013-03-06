package com.markitserv.msws.interceptor.small;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.markitserv.msws.internal.Constants;
import com.markitserv.msws.testutil.AbstractMswsTest;
import com.markitserv.msws.web.CommandInterceptor;

public class CommandInterceptorTest extends AbstractMswsTest {
	
	private final String STUBBED_UUID_VALUE = "Stubbed UUID";
	private CommandInterceptor interceptor;

	@Test
	public void testCommandInterceptor()
		throws Exception {
		assertNotNull("Interceptor is null" + interceptor);
	}

	@Test
	public void testPreHandle()
		throws Exception {
			MockHttpServletRequest request = new MockHttpServletRequest();
			MockHttpServletResponse response = new MockHttpServletResponse();
			Object handler = new Object();
			boolean result = interceptor.preHandle(request, response, handler);
			// mocked Uuidgenerator generates a stubbed uuid value
			assertEquals(STUBBED_UUID_VALUE, request.getAttribute(Constants.UUID));
			if(!result) {
				fail("interceptor failed");
			}
	}

	@Before
	public void setUp() {
		interceptor = new CommandInterceptor();
		interceptor.setUuidGenerator(CommandInterceptorTest.uuidGeneratorMock);
	}
}
