package com.language.service.impl;

import com.language.service.GraalVmLanguageService;
import com.language.service.model.Language;
import org.springframework.stereotype.Service;

@Service
public class PythonLanguageServiceImpl extends GraalVmLanguageService {

    /**
     * {@inheritDoc}
     */
    @Override
    public Language getInterpreterLanguage() {
        return Language.PYTHON;
    }

}
