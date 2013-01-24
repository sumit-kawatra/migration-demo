package com.markitserv.mwws.controller.small;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.RETURNS_SMART_NULLS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequestWrapper;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import com.markitserv.mwws.ExceptionResult;
import com.markitserv.mwws.GenericResult;
import com.markitserv.mwws.action.ActionResult;
import com.markitserv.mwws.command.CommandDispatcher;
import com.markitserv.mwws.command.ReqRespCommand;
import com.markitserv.mwws.exceptions.MwwsException;
import com.markitserv.mwws.exceptions.UnknownActionException;
import com.markitserv.mwws.exceptions.ValidationException;
import com.markitserv.mwws.testutil.AbstractMswsTest;
import com.markitserv.mwws.web.HttpParamsToActionCommand;
import com.markitserv.mwws.web.MwwsController;

/**
 * This testcase tests Controller methods and Exception handling
 * 
 * @author prasanth.sudarsanan
 * 
 */
public class MwwsControllerTest extends AbstractMswsTest {

	private MwwsController controller;
	private HttpParamsToActionCommand actionCmdBuilder;
	private CommandDispatcher dispatcher;

	@Before
	public void setupEach() {

		controller = new MwwsController();
		dispatcher = mock(CommandDispatcher.class, RETURNS_SMART_NULLS);
		actionCmdBuilder = mock(HttpParamsToActionCommand.class,
				RETURNS_SMART_NULLS);
		controller.setDispatcher(dispatcher);
		controller.setActionCmdBuilder(actionCmdBuilder);
	}

	@Test
	public void testMwwsController() throws Exception {
		MwwsController result = new MwwsController();
		assertNotNull(result);
	}

	/**
	 * Test if it returns ActionResult when there is no error
	 */
	@Test
	public void returnsActionResultIfNoErrorIsThrown() throws Exception {
		when(dispatcher.dispatchReqRespCommand(any(ReqRespCommand.class)))
				.thenReturn(new ActionResult());

		WebRequest req = new ServletWebRequest(new HttpServletRequestWrapper(
				new MockMultipartHttpServletRequest()));

		GenericResult result = controller.performActionReq(req);
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

		GenericResult result = controller.performActionReq(req);
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

		GenericResult result = controller.performActionReq(req);
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
		MwwsException validationException = new ValidationException(
				"ValidationException", errorMessages);

		when(dispatcher.dispatchReqRespCommand(any(ReqRespCommand.class)))
				.thenThrow(validationException);

		WebRequest req = new ServletWebRequest(new HttpServletRequestWrapper(
				new MockMultipartHttpServletRequest()));

		GenericResult result = controller.performActionReq(req);
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
