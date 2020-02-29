package com.notebook.service.service.impl;

import com.notebook.service.model.NotebookLanguage;
import com.notebook.service.service.GraalVmInterpreterService;
import org.springframework.stereotype.Service;

@Service
public class JSInterpreterServiceImpl extends GraalVmInterpreterService {

    /**
     * {@inheritDoc}
     */
    @Override
    public NotebookLanguage getInterpreterLanguage() {
        return NotebookLanguage.JAVA_SCRIPT;
    }
}
