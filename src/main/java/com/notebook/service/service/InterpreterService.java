package com.notebook.service.service;

import com.notebook.service.model.ExecutionRequest;
import com.notebook.service.model.ExecutionResponse;
import com.notebook.service.model.NotebookLanguage;
import com.notebook.service.model.exception.InterpreterException;

/**
 * Interpreters Service Interface
 *
 */
public interface InterpreterService {

    /**
     * Get NotebookLanguage from Service
     * @return
     */
    NotebookLanguage getInterpreterLanguage();

    /**
     * Interpret users requests
     * @param request user request with code to be interpreted
     * @return
     * @throws InterpreterException
     */
    ExecutionResponse execute(ExecutionRequest request) throws InterpreterException;
}
