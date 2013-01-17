package com.markitserv.mwws.exception.small;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.support.ManagedList;

import com.markitserv.mwws.ExceptionResult;
import com.markitserv.mwws.ResponseMetadata;
import com.markitserv.mwws.exceptions.MultipleErrorsException;
import com.markitserv.mwws.exceptions.MwwsException;
import com.markitserv.mwws.testutil.AbstractMswsTest;
import com.markitserv.mwws.web.MwwsController;

//TODO Incomplete
/**
 * 
 * @author prasanth.sudarsanan
 *
 */
public class ExceptionResultTest extends AbstractMswsTest {

	// ValidationException
	@Test
	public void testExceptionResult_1() throws Exception {
		MwwsException exception = new MwwsException();
		//mock container and call with a request and get validation exception
		//ValidationException exception = new ValidationException(msg, allMsgs);
		
		ExceptionResult result = new ExceptionResult(exception);
		assertNotNull(result);
	}

	// ProgrammaticException
	@Test
	public void testExceptionResult_ProgrammaticException() throws Exception {
		MwwsException exception = new MwwsException();

		ExceptionResult result = new ExceptionResult(exception);

		assertNotNull(result);
	}

	// other types of MwwsException
	@Test
	public void testExceptionResult_constructor3() throws Exception {
		MwwsException exception = new MultipleErrorsException("",
				new ManagedList());

		ExceptionResult result = new ExceptionResult(exception);
		assertNotNull(result);
	}

	@Test
	public void testErrors() throws Exception {
		ExceptionResult fixture = new ExceptionResult(new MwwsException());
		fixture.setMetaData(new ResponseMetadata());
		List<ExceptionResult.MwwsError> result = fixture.getErrors();

		assertNotNull(result);
		assertEquals(1, result.size());
	}

	@Before
	public void setUp() throws Exception {
		MwwsController controller = new MwwsController();
		
	}

}
