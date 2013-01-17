package com.markitserv.mwws.exception.small;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.markitserv.mwws.GenericResult;
import com.markitserv.mwws.ResponseMetadata;
//TODO incomplete
/**
 *
 * @author prasanth.sudarsanan
 *
 */
public class GenericResultTest {

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testGenericResult() throws Exception {
		GenericResult result = new GenericResult();
		assertNotNull(result);

	}

	@Test
	public void testGetMetaData() throws Exception {
		GenericResult genericResult = new GenericResult();
		genericResult.setMetaData(new ResponseMetadata());

		ResponseMetadata result = genericResult.getMetaData();

		assertNotNull(result);
		assertEquals(null, result.getRequestId());
	}
}
