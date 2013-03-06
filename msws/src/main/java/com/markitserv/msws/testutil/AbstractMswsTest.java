package com.markitserv.msws.testutil;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import static org.mockito.Mockito.RETURNS_SMART_NULLS;
import org.mockito.Mockito;

import com.markitserv.msws.Type;
import com.markitserv.msws.internal.UuidGenerator;

/** 
 * Exposes some useful variables to the subclasses:
 * actionCommandBuilder - used to create a fake ActionCommand
 * testAction - a simple subclass of Action
 * 
 * commonly used mocks:
 * uuidGeneratorMock - mock of UuidGenerator
 * 
 * @author roy.truelove
 *
 */
public abstract class AbstractMswsTest {

	// common utilities
	protected static TestActionCommandBuilder actionCommandBuilder;

	// commonly used mocks
	protected static UuidGenerator uuidGeneratorMock;

	@BeforeClass
	public static void setupAll() {
		
		System.err.println("Setting up all");

		// Stubs
		uuidGeneratorMock = Mockito.mock(UuidGenerator.class, RETURNS_SMART_NULLS);
		Mockito.when(uuidGeneratorMock.generateUuid()).thenReturn("Stubbed UUID");

		actionCommandBuilder = new TestActionCommandBuilder();
	}
	
	@After
	public void setup() {
		Mockito.reset(uuidGeneratorMock);
	}
}
