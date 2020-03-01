package com.language.service;

import com.language.service.model.LanguageExecutionRequest;
import com.language.service.model.GraalExecutionResponse;
import com.language.service.model.Language;
import com.language.service.model.exception.LanguageExecutionException;

/**
 * Language Interpreters Service Interface
 *
 */
public interface LanguageService {

    /**
     * Get Language from Service
     * @return
     */
    Language getInterpreterLanguage();

    /**
     * Interpret users requests
     * @param request user request with code to be interpreted
     * @return
     * @throws LanguageExecutionException
     */
    GraalExecutionResponse execute(LanguageExecutionRequest request) throws LanguageExecutionException;
}
