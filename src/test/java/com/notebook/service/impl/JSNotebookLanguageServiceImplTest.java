package com.notebook.service.impl;

import com.notebook.service.NotbookAsServiceApp;
import com.notebook.service.model.NotbookExecutionRequest;
import com.notebook.service.model.GraalExecutionResponse;
import com.notebook.service.model.exception.NoteboolLanguageTimeOutException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = NotbookAsServiceApp.class)
public class JSNotebookLanguageServiceImplTest {

	@Autowired
	private JSNotebookLanguageServiceImpl jsInterpreterService;

	@Test
	public void testSimpleConsoleLog() {
		String helloWorld = "Hello World";
		NotbookExecutionRequest request = new NotbookExecutionRequest();
		request.setLanguage("js");
		request.setCode("console.log('"+ helloWorld + "');");
		request.setSessionId("mySessionId");

		GraalExecutionResponse response = jsInterpreterService.execute(request);
		assertTrue(response.getErrors().isEmpty());
		assertEquals(helloWorld + "\n", response.getOutput());
	}

	@Test
	public void testUndefinedVariableError() {
		NotbookExecutionRequest request = new NotbookExecutionRequest();
		request.setLanguage("js");
		request.setCode("console.log(a)");
		request.setSessionId("mySessionId");

		GraalExecutionResponse response = jsInterpreterService.execute(request);
		assertTrue(response.getOutput().isEmpty());
		assertEquals("ReferenceError: a is not defined", response.getErrors());
	}

	@Test
	public void testDefinedVariable() {
		NotbookExecutionRequest request = new NotbookExecutionRequest();
		request.setLanguage("js");
		request.setCode("var a = 5;");
		request.setSessionId("mySessionId");

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
		NotbookExecutionRequest request = new NotbookExecutionRequest();
		request.setLanguage("js");
		request.setCode("function f() { console.log('" + helloWorld + "') };");
		request.setSessionId("mySessionId");

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
	@Test(expected = NoteboolLanguageTimeOutException.class)
	public void testInfiniteLoop() {
		NotbookExecutionRequest request = new NotbookExecutionRequest();
		request.setLanguage("js");
		request.setCode("while(true);");
		request.setSessionId("mySessionId");

		jsInterpreterService.execute(request);
	}

	@Test
	public void testResetContext() {
		NotbookExecutionRequest request = new NotbookExecutionRequest();
		request.setLanguage("js");
		request.setCode("function f() { while(true) {console.log(5)} };");
		request.setSessionId("mySessionId");

		GraalExecutionResponse response1 = jsInterpreterService.execute(request);
		assertTrue(response1.getOutput().isEmpty());
		assertTrue(response1.getErrors().isEmpty());

		try {
			request.setCode("f();");
			jsInterpreterService.execute(request);
			fail(); // Should throw TIMEOUT Exception
		} catch (NoteboolLanguageTimeOutException e) {}

		request.setCode("f();");
		GraalExecutionResponse response2 = jsInterpreterService.execute(request);
		assertTrue(response2.getOutput().isEmpty());
		assertEquals("ReferenceError: f is not defined", response2.getErrors());
	}

}