package com.notebook.service;

import com.notebook.service.model.NotbookExecutionRequest;
import com.notebook.service.model.NotebookRequest;
import com.notebook.service.model.exception.InvalidNotebookRequestException;

/**
 * Parse NotebookLanguage request and extract needed information
 *
 */
public interface NotebookLanguageRequestParsingService {
    /**
     * Extract execution request information from NotebookLanguage request
     * @param request
     * @return
     */
    NotbookExecutionRequest parseInterpreterRequest(NotebookRequest request) throws InvalidNotebookRequestException;
}
