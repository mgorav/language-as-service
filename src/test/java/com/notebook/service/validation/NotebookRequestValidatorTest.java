package com.notebook.service.validation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class NotebookRequestValidatorTest {

    private NotebookLanguageRequestValidator notebookLanguageRequestValidator;

    @Before
    public void setUp() {
        notebookLanguageRequestValidator = Mockito.mock(NotebookLanguageRequestValidator.class);
        Mockito.when(notebookLanguageRequestValidator.matchesRequestPatternTest(Mockito.anyString())).thenCallRealMethod();
    }

    @Test
    public void validRequestPatternTest() {
        assertTrue(notebookLanguageRequestValidator.matchesRequestPatternTest("%python "));
        assertTrue(notebookLanguageRequestValidator.matchesRequestPatternTest("%python print(a + b)"));
        assertTrue(notebookLanguageRequestValidator.matchesRequestPatternTest("%python  a = 1 + 2   "));
        assertTrue(notebookLanguageRequestValidator.matchesRequestPatternTest("%java  System.out.println(\"Hello World\"); "));
        assertTrue(notebookLanguageRequestValidator.matchesRequestPatternTest("%java  System.out.println(\"Hello World\");\\n" +
                "System.err.println(\"Hello Error World\");"));
    }

    @Test
    public void invalidRequestPatternTest() {
        assertFalse(notebookLanguageRequestValidator.matchesRequestPatternTest("%python"));
        assertFalse(notebookLanguageRequestValidator.matchesRequestPatternTest("% python print(a + b)"));
        assertFalse(notebookLanguageRequestValidator.matchesRequestPatternTest(" %python  a = 1 + 2   "));
        assertFalse(notebookLanguageRequestValidator.matchesRequestPatternTest(" python  a = 1 + 2   "));
        assertFalse(notebookLanguageRequestValidator.matchesRequestPatternTest("java  System.out.println(\"Hello World\") "));

    }
}