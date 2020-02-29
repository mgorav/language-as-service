package com.notebook.service.service;

import com.notebook.service.model.ExecutionRequest;
import com.notebook.service.model.NotebookRequest;
import com.notebook.service.model.exception.InvalidInterpreterRequestException;

/**
 * Parse NotebookLanguage request and extract needed information
 *
 */
public interface InterpreterRequestParsingService {
    /**
     * Extract execution request information from NotebookLanguage request
     * @param request
     * @return
     */
    ExecutionRequest parseInterpreterRequest(NotebookRequest request) throws InvalidInterpreterRequestException;
}
