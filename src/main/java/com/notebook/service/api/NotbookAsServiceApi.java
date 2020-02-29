package com.notebook.service.api;

import com.notebook.service.model.*;
import com.notebook.service.model.exception.NotebookException;
import com.notebook.service.NotebookLanguageRequestParsingService;
import com.notebook.service.NotebookLanguageService;
import com.notebook.service.NotebookLanguageServiceFactory;
import com.notebook.service.validation.ValidRequest;
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
    private NotebookLanguageRequestParsingService notebookLanguageRequestParsingService;

    @Autowired
    private NotebookLanguageServiceFactory notebookLanguageServiceFactory;

    @PostMapping("/execute")
    public ResponseEntity<NotbookResponse> execute(@ValidRequest @RequestBody NotebookRequest notebookRequest, HttpSession httpSession) throws NotebookException {
        NotbookExecutionRequest request = notebookLanguageRequestParsingService.parseInterpreterRequest(notebookRequest);
        NotebookLanguageService notebookLanguageService = notebookLanguageServiceFactory.getInterpreterService(request.getLanguage());
        String sessionId = notebookRequest.getInteractionId() != null ? notebookRequest.getInteractionId() : httpSession.getId();
        request.setInteractionId(sessionId);
        GraalExecutionResponse graalExecutionResponse = notebookLanguageService.execute(request);
        NotbookResponse notbookResponse = new NotbookResponse();
        notbookResponse.setResponse(graalExecutionResponse.getOutput());
        notbookResponse.setErrors(graalExecutionResponse.getErrors());
        notbookResponse.setInteractionId(sessionId);
        return ResponseEntity.ok(notbookResponse);
    }
}
