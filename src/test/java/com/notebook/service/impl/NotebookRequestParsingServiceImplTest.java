package com.notebook.service.impl;

import com.notebook.service.model.NotbookExecutionRequest;
import com.notebook.service.model.NotebookRequest;
import com.notebook.service.model.exception.InvalidNotebookRequestException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class NotebookRequestParsingServiceImplTest {

    private NotebookLanguageRequestParsingServiceImpl interpreterRequestParsingService;

    @Before
    public void setUp() throws InvalidNotebookRequestException {
        interpreterRequestParsingService = Mockito.mock(NotebookLanguageRequestParsingServiceImpl.class);
        Mockito.when(interpreterRequestParsingService.parseInterpreterRequest(Mockito.any(NotebookRequest.class)))
                .thenCallRealMethod();
    }

    @Test
    public void parseInterpreterRequest() throws InvalidNotebookRequestException {
        NotebookRequest request = new NotebookRequest();
        request.setCode("%js console.log('Hello World');");
        NotbookExecutionRequest notbookExecutionRequest = interpreterRequestParsingService.parseInterpreterRequest(request);
        assertEquals("js", notbookExecutionRequest.getLanguage());
        assertEquals("console.log('Hello World');", notbookExecutionRequest.getCode());
    }

    @Test(expected = InvalidNotebookRequestException.class)
    public void parseInvalidInterpreterRequest() throws InvalidNotebookRequestException {
        NotebookRequest request = new NotebookRequest();
        request.setCode(" %js console.log('Hello World');");
        interpreterRequestParsingService.parseInterpreterRequest(request);
    }
}