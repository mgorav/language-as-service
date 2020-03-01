package com.language.service.impl;

import com.language.service.model.LanguageExecutionRequest;
import com.language.service.model.LanguageRequest;
import com.language.service.model.exception.InvalidLanguageExecutionRequestException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class LanguageRequestParsingServiceImplTest {

    private LanguageRequestParsingServiceImpl interpreterRequestParsingService;

    @Before
    public void setUp() throws InvalidLanguageExecutionRequestException {
        interpreterRequestParsingService = Mockito.mock(LanguageRequestParsingServiceImpl.class);
        Mockito.when(interpreterRequestParsingService.parseInterpreterRequest(Mockito.any(LanguageRequest.class)))
                .thenCallRealMethod();
    }

    @Test
    public void parseInterpreterRequest() throws InvalidLanguageExecutionRequestException {
        LanguageRequest request = new LanguageRequest();
        request.setCode("%js console.log('Hello World');");
        LanguageExecutionRequest languageExecutionRequest = interpreterRequestParsingService.parseInterpreterRequest(request);
        assertEquals("js", languageExecutionRequest.getLanguage());
        assertEquals("console.log('Hello World');", languageExecutionRequest.getCode());
    }

    @Test(expected = InvalidLanguageExecutionRequestException.class)
    public void parseInvalidInterpreterRequest() throws InvalidLanguageExecutionRequestException {
        LanguageRequest request = new LanguageRequest();
        request.setCode(" %js console.log('Hello World');");
        interpreterRequestParsingService.parseInterpreterRequest(request);
    }
}