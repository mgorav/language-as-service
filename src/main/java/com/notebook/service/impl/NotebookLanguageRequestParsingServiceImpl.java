package com.notebook.service.impl;

import com.notebook.service.NotebookLanguageRequestParsingService;
import com.notebook.service.model.NotbookExecutionRequest;
import com.notebook.service.model.NotebookRequest;
import com.notebook.service.model.exception.InvalidNotebookRequestException;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class NotebookLanguageRequestParsingServiceImpl implements NotebookLanguageRequestParsingService {

    private static final String REQUEST_PATTERN = "%(\\w+)\\s+(.*)";
    private static final Pattern pattern = Pattern.compile(REQUEST_PATTERN);

    /**
     * {@inheritDoc}
     */
    @Override
    public NotbookExecutionRequest parseInterpreterRequest(NotebookRequest request) {
        Matcher matcher = pattern.matcher(request.getCode());
        if (matcher.matches()) {
            String language = matcher.group(1);
            String code = matcher.group(2);

            NotbookExecutionRequest notbookExecutionRequest = new NotbookExecutionRequest();
            notbookExecutionRequest.setCode(code);
            notbookExecutionRequest.setLanguage(language);

            return notbookExecutionRequest;
        }

        throw new InvalidNotebookRequestException();
    }
}
