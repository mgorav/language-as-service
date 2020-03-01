package com.language.service;

import com.language.service.model.LanguageExecutionRequest;
import com.language.service.model.LanguageRequest;
import com.language.service.model.exception.InvalidLanguageExecutionRequestException;

/**
 * Parse Language request and extract needed information
 *
 */
public interface LanguageRequestParsingService {
    /**
     * Extract execution request information from Language request
     * @param request
     * @return
     */
    LanguageExecutionRequest parseInterpreterRequest(LanguageRequest request) throws InvalidLanguageExecutionRequestException;
}
