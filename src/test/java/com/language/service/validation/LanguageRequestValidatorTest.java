package com.language.service.validation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class LanguageRequestValidatorTest {

    private LanguageRequestValidator languageRequestValidator;

    @Before
    public void setUp() {
        languageRequestValidator = Mockito.mock(LanguageRequestValidator.class);
        Mockito.when(languageRequestValidator.matchesRequestPatternTest(Mockito.anyString())).thenCallRealMethod();
    }

    @Test
    public void validRequestPatternTest() {
        assertTrue(languageRequestValidator.matchesRequestPatternTest("%python "));
        assertTrue(languageRequestValidator.matchesRequestPatternTest("%python print(a + b)"));
        assertTrue(languageRequestValidator.matchesRequestPatternTest("%python  a = 1 + 2   "));
        assertTrue(languageRequestValidator.matchesRequestPatternTest("%java  System.out.println(\"Hello World\"); "));
        assertTrue(languageRequestValidator.matchesRequestPatternTest("%java  System.out.println(\"Hello World\");\\n" +
                "System.err.println(\"Hello Error World\");"));
    }

    @Test
    public void invalidRequestPatternTest() {
        assertFalse(languageRequestValidator.matchesRequestPatternTest("%python"));
        assertFalse(languageRequestValidator.matchesRequestPatternTest("% python print(a + b)"));
        assertFalse(languageRequestValidator.matchesRequestPatternTest(" %python  a = 1 + 2   "));
        assertFalse(languageRequestValidator.matchesRequestPatternTest(" python  a = 1 + 2   "));
        assertFalse(languageRequestValidator.matchesRequestPatternTest("java  System.out.println(\"Hello World\") "));

    }
}