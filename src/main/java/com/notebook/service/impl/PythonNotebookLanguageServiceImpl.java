package com.notebook.service.impl;

import com.notebook.service.GraalVmNotebookLanguageService;
import com.notebook.service.model.NotebookLanguage;
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
