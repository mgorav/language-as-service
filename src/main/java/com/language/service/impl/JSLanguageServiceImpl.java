package com.language.service.impl;

import com.language.service.GraalVmLanguageService;
import com.language.service.model.Language;
import org.springframework.stereotype.Service;

@Service
public class JSLanguageServiceImpl extends GraalVmLanguageService {

    /**
     * {@inheritDoc}
     */
    @Override
    public Language getInterpreterLanguage() {
        return Language.JAVA_SCRIPT;
    }
}
