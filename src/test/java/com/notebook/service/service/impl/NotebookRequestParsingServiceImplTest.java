package com.notebook.service.service.impl;

import com.notebook.service.model.ExecutionRequest;
import com.notebook.service.model.NotebookRequest;
import com.notebook.service.model.exception.InvalidInterpreterRequestException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class NotebookRequestParsingServiceImplTest {

    private InterpreterRequestParsingServiceImpl interpreterRequestParsingService;

    @Before
    public void setUp() throws InvalidInterpreterRequestException {
        interpreterRequestParsingService = Mockito.mock(InterpreterRequestParsingServiceImpl.class);
        Mockito.when(interpreterRequestParsingService.parseInterpreterRequest(Mockito.any(NotebookRequest.class)))
                .thenCallRealMethod();
    }

    @Test
    public void parseInterpreterRequest() throws InvalidInterpreterRequestException {
        NotebookRequest request = new NotebookRequest();
        request.setCode("%js console.log('Hello World');");
        ExecutionRequest executionRequest = interpreterRequestParsingService.parseInterpreterRequest(request);
        assertEquals("js", executionRequest.getLanguage());
        assertEquals("console.log('Hello World');", executionRequest.getCode());
    }

    @Test(expected = InvalidInterpreterRequestException.class)
    public void parseInvalidInterpreterRequest() throws InvalidInterpreterRequestException {
        NotebookRequest request = new NotebookRequest();
        request.setCode(" %js console.log('Hello World');");
        interpreterRequestParsingService.parseInterpreterRequest(request);
    }
}