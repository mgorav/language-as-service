package com.notebook.service.controller;

import com.notebook.service.model.*;
import com.notebook.service.model.exception.InterpreterException;
import com.notebook.service.service.InterpreterRequestParsingService;
import com.notebook.service.service.InterpreterService;
import com.notebook.service.service.InterpreterServiceFactory;
import com.notebook.service.validation.CorrectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@Validated
public class NotbookAsServiceApi {

    @Autowired
    private InterpreterRequestParsingService interpreterRequestParsingService;

    @Autowired
    private InterpreterServiceFactory interpreterServiceFactory;

    @PostMapping("/execute")
    public ResponseEntity<NotbookResponse> execute(@CorrectRequest @RequestBody NotebookRequest notebookRequest, HttpSession httpSession) throws InterpreterException {
        ExecutionRequest request = interpreterRequestParsingService.parseInterpreterRequest(notebookRequest);
        InterpreterService interpreterService = interpreterServiceFactory.getInterpreterService(request.getLanguage());
        String sessionId = notebookRequest.getSessionId() != null ? notebookRequest.getSessionId() : httpSession.getId();
        request.setSessionId(sessionId);
        ExecutionResponse executionResponse = interpreterService.execute(request);
        NotbookResponse notbookResponse = new NotbookResponse();
        notbookResponse.setResponse(executionResponse.getOutput());
        notbookResponse.setErrors(executionResponse.getErrors());
        notbookResponse.setSessionId(sessionId);
        return ResponseEntity.ok(notbookResponse);
    }
}
