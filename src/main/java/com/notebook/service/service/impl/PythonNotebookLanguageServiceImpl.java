package com.notebook.service.service.impl;

import com.notebook.service.model.NotebookLanguage;
import com.notebook.service.service.GraalVmNotebookLanguageService;
import org.springframework.stereotype.Service;

@Service
public class PythonNotebookLanguageServiceImpl extends GraalVmNotebookLanguageService {

    /**
     * {@inheritDoc}
     */
    @Override
    public NotebookLanguage getInterpreterLanguage() {
        return NotebookLanguage.PYTHON;
    }

}
