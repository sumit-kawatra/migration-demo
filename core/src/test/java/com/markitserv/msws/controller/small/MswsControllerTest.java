package com.markitserv.msws.controller.small;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.RETURNS_SMART_NULLS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import com.markitserv.msws.ExceptionResult;
import com.markitserv.msws.AbstractWebserviceResult;
import com.markitserv.msws.action.ActionResult;
import com.markitserv.msws.command.CommandDispatcher;
import com.markitserv.msws.command.ReqRespCommand;
import com.markitserv.msws.exceptions.MswsException;
import com.markitserv.msws.exceptions.UnknownActionException;
import com.markitserv.msws.exceptions.ValidationException;
import com.markitserv.msws.testutil.AbstractMswsTest;
import com.markitserv.msws.web.HttpParamsToActionCommand;
import com.markitserv.msws.web.MswsController;
import com.markitserv.msws.web.RequestContextHolderWrapper;

/**
 * This testcase tests Controller methods and Exception handling
 * 
 * @author prasanth.sudarsanan
 * 
 */
public class MswsControllerTest extends AbstractMswsTest {

	private MswsController controller;
	private HttpParamsToActionCommand actionCmdBuilder;
	private CommandDispatcher dispatcher;
	private RequestContextHolderWrapper reqContextHolder;
	
	private class FakeType {
		
	}

	@Before
	public void setupEach() {

		controller = new MswsController();
		
		dispatcher = mock(CommandDispatcher.class, RETURNS_SMART_NULLS);
		actionCmdBuilder = mock(HttpParamsToActionCommand.class,
				RETURNS_SMART_NULLS);
		reqContextHolder = mock(RequestContextHolderWrapper.class, RETURNS_SMART_NULLS);
		
		HttpSession session = mock(HttpSession.class, RETURNS_SMART_NULLS);
		
		when(session.getMaxInactiveInterval()).thenReturn(30);
		when(reqContextHolder.getCurrentSession()).thenReturn(session);
		
		controller.setDispatcher(dispatcher);
		controller.setActionCmdBuilder(actionCmdBuilder);
		controller.setReqContextHolder(reqContextHolder);
	}

	@Test
	public void testMwwsController() throws Exception {
		MswsController result = new MswsController();
		assertNotNull(result);
	}

	/**
	 * Test if it returns ActionResult when there is no error
	 */
	@Test
	public void returnsActionResultIfNoErrorIsThrown() throws Exception {
		when(dispatcher.dispatchReqRespCommand(any(ReqRespCommand.class)))
				.thenReturn(new ActionResult(new FakeType()));

		WebRequest req = new ServletWebRequest(new HttpServletRequestWrapper(
				new MockMultipartHttpServletRequest()));

		AbstractWebserviceResult result = controller.performActionReq(req);
		assertTrue(result instanceof ActionResult);	
	}
	
	/**
	 * Test if it returns UnknownActionException(UAE) in Exception result
	 */
	@Test
	public void returnUAEExceptionResultIfDispatcherThrowException()
			throws Exception {

		when(dispatcher.dispatchReqRespCommand(any(ReqRespCommand.class)))
				.thenThrow(UnknownActionException.standardException("Foo"));

		WebRequest req = new ServletWebRequest(new HttpServletRequestWrapper(
				new MockMultipartHttpServletRequest()));

		AbstractWebserviceResult result = controller.performActionReq(req);
		assertTrue(result instanceof ExceptionResult);
		ExceptionResult er = (ExceptionResult) result;
		assertEquals(1, er.getErrors().size());
		assertEquals("UnknownActionException", er.getErrors().get(0)
				.getErrorCode());
		assertTrue(er.getErrors().get(0).getErrorMessage()
				.contains("Cannot find action with the name"));
		assertTrue(er.getErrors().get(0).getErrorMessage().contains("Foo"));
	}

	/**
	 * Test if it returns ProgrammaticException(PE) in Exception result when
	 * main exception(other than MwwsException) is thrown, and if non-MwwsExceptions are caught correctly
	 */
	@Test
	public void returnNPEExceptionResultIfDispatcherThrowException()
			throws Exception {

		when(dispatcher.dispatchReqRespCommand(any(ReqRespCommand.class)))
				.thenThrow(new NullPointerException("NPE thrown"));

		WebRequest req = new ServletWebRequest(new HttpServletRequestWrapper(
				new MockMultipartHttpServletRequest()));

		AbstractWebserviceResult result = controller.performActionReq(req);
		assertTrue(result instanceof ExceptionResult);
		ExceptionResult er = (ExceptionResult) result;
		assertEquals(1, er.getErrors().size());
		assertEquals(ExceptionResult.ERRORCODE_GENERIC, er.getErrors().get(0)
				.getErrorCode());
		assertEquals(ExceptionResult.ERRORMESSAGE_GENERIC, er.getErrors().get(0)
				.getErrorMessage());
	}
	
	/**
	 * Test if it returns multiple ValidationException(VE) in Exception result
	 */
	@Test
	public void returnVEExceptionResultIfDispatcherThrowException()
			throws Exception {

		String[] messages = { "validation exception message1",
				"validation exception message2" };
		List<String> errorMessages = Arrays.asList(messages);
		MswsException validationException = new ValidationException(
				"ValidationException", errorMessages);

		when(dispatcher.dispatchReqRespCommand(any(ReqRespCommand.class)))
				.thenThrow(validationException);

		WebRequest req = new ServletWebRequest(new HttpServletRequestWrapper(
				new MockMultipartHttpServletRequest()));

		AbstractWebserviceResult result = controller.performActionReq(req);
		assertTrue(result instanceof ExceptionResult);
		ExceptionResult er = (ExceptionResult) result;
		assertEquals(2, er.getErrors().size());
		assertEquals("ValidationException", er.getErrors().get(0)
				.getErrorCode());
		assertEquals("ValidationException", er.getErrors().get(1)
				.getErrorCode());
		assertEquals("validation exception message1", er.getErrors().get(0)
				.getErrorMessage());
		assertEquals("validation exception message2", er.getErrors().get(1)
				.getErrorMessage());
	}
	
	

}
