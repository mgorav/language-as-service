package com.language.service.impl;

import com.language.service.LanguageRequestParsingService;
import com.language.service.model.LanguageExecutionRequest;
import com.language.service.model.LanguageRequest;
import com.language.service.model.exception.InvalidLanguageExecutionRequestException;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class LanguageRequestParsingServiceImpl implements LanguageRequestParsingService {

    private static final String REQUEST_PATTERN = "%(\\w+)\\s+(.*)";
    private static final Pattern pattern = Pattern.compile(REQUEST_PATTERN);

    /**
     * {@inheritDoc}
     */
    @Override
    public LanguageExecutionRequest parseInterpreterRequest(LanguageRequest request) {
        Matcher matcher = pattern.matcher(request.getCode());
        if (matcher.matches()) {
            String language = matcher.group(1);
            String code = matcher.group(2);

            LanguageExecutionRequest languageExecutionRequest = new LanguageExecutionRequest();
            languageExecutionRequest.setCode(code);
            languageExecutionRequest.setLanguage(language);

            return languageExecutionRequest;
        }

        throw new InvalidLanguageExecutionRequestException();
    }
}
