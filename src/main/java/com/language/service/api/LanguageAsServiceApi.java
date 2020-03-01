package com.language.service.api;

import com.language.service.LanguageRequestParsingService;
import com.language.service.NotebookLanguageService;
import com.language.service.NotebookLanguageServiceFactory;
import com.language.service.model.GraalExecutionResponse;
import com.language.service.model.LanguageExecutionRequest;
import com.language.service.model.LanguageRequest;
import com.language.service.model.LanguageResponse;
import com.language.service.validation.ValidRequest;
import com.language.service.model.exception.LanguageExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@Validated
public class LanguageAsServiceApi {

    @Autowired
    private LanguageRequestParsingService languageRequestParsingService;

    @Autowired
    private NotebookLanguageServiceFactory notebookLanguageServiceFactory;

    @PostMapping("/execute")
    public ResponseEntity<LanguageResponse> execute(@ValidRequest @RequestBody LanguageRequest languageRequest, HttpSession httpSession) throws LanguageExecutionException {
        LanguageExecutionRequest request = languageRequestParsingService.parseInterpreterRequest(languageRequest);
        NotebookLanguageService notebookLanguageService = notebookLanguageServiceFactory.getInterpreterService(request.getLanguage());
        String sessionId = languageRequest.getInteractionId() != null ? languageRequest.getInteractionId() : httpSession.getId();
        request.setInteractionId(sessionId);
        GraalExecutionResponse graalExecutionResponse = notebookLanguageService.execute(request);
        LanguageResponse languageResponse = new LanguageResponse();
        languageResponse.setResponse(graalExecutionResponse.getOutput());
        languageResponse.setErrors(graalExecutionResponse.getErrors());
        languageResponse.setInteractionId(sessionId);
        return ResponseEntity.ok(languageResponse);
    }
}
