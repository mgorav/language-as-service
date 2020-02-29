package com.notebook.service.service.impl;

import com.notebook.service.model.ExecutionRequest;
import com.notebook.service.model.NotebookRequest;
import com.notebook.service.model.exception.InvalidInterpreterRequestException;
import com.notebook.service.service.InterpreterRequestParsingService;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class InterpreterRequestParsingServiceImpl implements InterpreterRequestParsingService {

    private static final String REQUEST_PATTERN = "%(\\w+)\\s+(.*)";
    private static final Pattern pattern = Pattern.compile(REQUEST_PATTERN);

    /**
     * {@inheritDoc}
     */
    @Override
    public ExecutionRequest parseInterpreterRequest(NotebookRequest request) {
        Matcher matcher = pattern.matcher(request.getCode());
        if (matcher.matches()) {
            String language = matcher.group(1);
            String code = matcher.group(2);

            ExecutionRequest executionRequest = new ExecutionRequest();
            executionRequest.setCode(code);
            executionRequest.setLanguage(language);

            return executionRequest;
        }

        throw new InvalidInterpreterRequestException();
    }
}
