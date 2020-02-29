package com.notebook.service;

import com.notebook.service.model.NotbookExecutionRequest;
import com.notebook.service.model.GraalExecutionResponse;
import com.notebook.service.model.NotebookLanguage;
import com.notebook.service.model.exception.NotebookException;

/**
 * Interpreters Service Interface
 *
 */
public interface NotebookLanguageService {

    /**
     * Get NotebookLanguage from Service
     * @return
     */
    NotebookLanguage getInterpreterLanguage();

    /**
     * Interpret users requests
     * @param request user request with code to be interpreted
     * @return
     * @throws NotebookException
     */
    GraalExecutionResponse execute(NotbookExecutionRequest request) throws NotebookException;
}
