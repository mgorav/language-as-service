package com.notebook.service.validation;

import com.notebook.service.model.NotebookRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class InterpreterRequestValidator implements ConstraintValidator<CorrectRequest, NotebookRequest> {

    private static final String REQUEST_PATTERN = "%(\\w+)\\s+(.*)";

    @Override
    public boolean isValid(NotebookRequest request, ConstraintValidatorContext context) {
        if (request == null) return true;
        if (request.getCode() == null || request.getCode().isEmpty()) {
            context.buildConstraintViolationWithTemplate("Request code is required")
                    .addPropertyNode("code")
                    .addConstraintViolation();
            return false;
        }

        if (!matchesRequestPatternTest(request.getCode())) {
            context.buildConstraintViolationWithTemplate("Invalid request code format. " +
                    "Your code should follow the given format " +
                    "%language code")
                    .addPropertyNode("code");
            return false;
        }

        return true;
    }

    boolean matchesRequestPatternTest(String code) {
        if (code == null) return false;
        return code.matches(REQUEST_PATTERN);
    }
}
