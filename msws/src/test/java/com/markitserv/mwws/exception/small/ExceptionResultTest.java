package com.markitserv.mwws.exception.small;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.markitserv.mwws.ExceptionResult;
import com.markitserv.mwws.ResponseMetadata;
import com.markitserv.mwws.exceptions.MalformedFiltersException;
import com.markitserv.mwws.exceptions.MultipleErrorsException;
import com.markitserv.mwws.exceptions.MwwsException;
import com.markitserv.mwws.exceptions.ProgrammaticException;
import com.markitserv.mwws.exceptions.ValidationException;
import com.markitserv.mwws.testutil.AbstractMswsTest;

/**
 * Tests ExceptionResult. More exception testing is done in
 * MwwsControllerTest.java
 * 
 * @author prasanth.sudarsanan
 * 
 */
public class ExceptionResultTest extends AbstractMswsTest {

	/**
	 * Test if it returns ProgrammaticException
	 */
	@Test
	public void returnProgrammaticException() throws Exception {
		MwwsException exception = new ProgrammaticException();
		ExceptionResult result = new ExceptionResult(exception);
		assertNotNull(result);
		assertEquals(ExceptionResult.ERRORCODE_GENERIC,
				result.getErrors().get(0).getErrorCode());
		assertEquals(ExceptionResult.ERRORMESSAGE_GENERIC, result.getErrors()
				.get(0).getErrorMessage());
	}

	/**
	 * Test if it returns MultipleErrorsException
	 */
	@Test
	public void returnMultipleErrorsException() throws Exception {
		String[] messages = { "validation exception message1",
				"validation exception message2" };
		List<String> errorMessages = Arrays.asList(messages);
		MultipleErrorsException validationException = new ValidationException(
				"ValidationException", errorMessages);
		ExceptionResult result = new ExceptionResult(validationException);
		assertNotNull(result);
		assertEquals(2, result.getErrors().size());
		assertEquals("ValidationException", result.getErrors().get(0)
				.getErrorCode());
		assertEquals("ValidationException", result.getErrors().get(1)
				.getErrorCode());
		assertEquals("validation exception message1", result.getErrors().get(0)
				.getErrorMessage());
		assertEquals("validation exception message2", result.getErrors().get(1)
				.getErrorMessage());
	}

	/**
	 * Test if it returns errors
	 */
	@Test
	public void testErrors() throws Exception {
		ExceptionResult result = new ExceptionResult(new MwwsException());
		result.setMetaData(new ResponseMetadata());
		List<ExceptionResult.MwwsError> errors = result.getErrors();

		assertNotNull(errors);
		assertEquals(1, errors.size());
	}

	/**
	 * Test if it returns other types of MwwsException except
	 * ProgrammaticException,MultipleErrorsException
	 */
	@Test
	public void returnOtherMwwsException() throws Exception {
		MwwsException exception = new MalformedFiltersException("malformed filters exception thrown");
		ExceptionResult result = new ExceptionResult(exception);
		assertNotNull(result);
		assertEquals(1, result.getErrors().size());
		assertEquals("MalformedFiltersException",  result.getErrors().get(0).getErrorCode());
		assertEquals("malformed filters exception thrown",  result.getErrors().get(0).getErrorMessage());
	}
}
