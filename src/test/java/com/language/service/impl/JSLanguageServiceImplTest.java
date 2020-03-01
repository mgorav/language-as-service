package com.language.service.impl;

import com.language.service.LanguageAsServiceApp;
import com.language.service.model.LanguageExecutionRequest;
import com.language.service.model.GraalExecutionResponse;
import com.language.service.model.exception.LanguageExecutionTimeOutException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LanguageAsServiceApp.class)
public class JSLanguageServiceImplTest {

	@Autowired
	private JSLanguageServiceImpl jsInterpreterService;

	@Test
	public void testSimpleConsoleLog() {
		String helloWorld = "Hello World";
		LanguageExecutionRequest request = new LanguageExecutionRequest();
		request.setLanguage("js");
		request.setCode("console.log('"+ helloWorld + "');");
		request.setInteractionId("mySessionId");

		GraalExecutionResponse response = jsInterpreterService.execute(request);
		assertTrue(response.getErrors().isEmpty());
		assertEquals(helloWorld + "\n", response.getOutput());
	}

	@Test
	public void testUndefinedVariableError() {
		LanguageExecutionRequest request = new LanguageExecutionRequest();
		request.setLanguage("js");
		request.setCode("console.log(a)");
		request.setInteractionId("mySessionId");

		GraalExecutionResponse response = jsInterpreterService.execute(request);
		assertTrue(response.getOutput().isEmpty());
		assertEquals("ReferenceError: a is not defined", response.getErrors());
	}

	@Test
	public void testDefinedVariable() {
		LanguageExecutionRequest request = new LanguageExecutionRequest();
		request.setLanguage("js");
		request.setCode("var a = 5;");
		request.setInteractionId("mySessionId");

		GraalExecutionResponse response1 = jsInterpreterService.execute(request);
		assertTrue(response1.getOutput().isEmpty());
		assertTrue(response1.getErrors().isEmpty());

		request.setCode("console.log(a);");
		GraalExecutionResponse response2 = jsInterpreterService.execute(request);
		assertEquals("5\n", response2.getOutput());
		assertTrue(response2.getErrors().isEmpty());
	}

	@Test
	public void testDefinedFunction() {
		String helloWorld = "Hello World";
		LanguageExecutionRequest request = new LanguageExecutionRequest();
		request.setLanguage("js");
		request.setCode("function f() { console.log('" + helloWorld + "') };");
		request.setInteractionId("mySessionId");

		GraalExecutionResponse response1 = jsInterpreterService.execute(request);
		assertTrue(response1.getOutput().isEmpty());
		assertTrue(response1.getErrors().isEmpty());

		request.setCode("f();");
		GraalExecutionResponse response2 = jsInterpreterService.execute(request);
		assertEquals(helloWorld + '\n', response2.getOutput());
		assertTrue(response2.getErrors().isEmpty());
	}

	// Food for thought: fake or force timeout ?
	// Food for thought: test timeout duration ?
	@Test(expected = LanguageExecutionTimeOutException.class)
	public void testInfiniteLoop() {
		LanguageExecutionRequest request = new LanguageExecutionRequest();
		request.setLanguage("js");
		request.setCode("while(true);");
		request.setInteractionId("mySessionId");

		jsInterpreterService.execute(request);
	}

	@Test
	public void testResetContext() {
		LanguageExecutionRequest request = new LanguageExecutionRequest();
		request.setLanguage("js");
		request.setCode("function f() { while(true) {console.log(5)} };");
		request.setInteractionId("mySessionId");

		GraalExecutionResponse response1 = jsInterpreterService.execute(request);
		assertTrue(response1.getOutput().isEmpty());
		assertTrue(response1.getErrors().isEmpty());

		try {
			request.setCode("f();");
			jsInterpreterService.execute(request);
			fail(); // Should throw TIMEOUT Exception
		} catch (LanguageExecutionTimeOutException e) {}

		request.setCode("f();");
		GraalExecutionResponse response2 = jsInterpreterService.execute(request);
		assertTrue(response2.getOutput().isEmpty());
		assertEquals("ReferenceError: f is not defined", response2.getErrors());
	}

}