package com.notebook.service.service;

import com.notebook.service.model.NotebookLanguage;
import com.notebook.service.model.exception.LanguageNotSupportedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Factory for all NotebookLanguage Services
 *
 */
@Service
public class InterpreterServiceFactory {

    // TODO use enum map
    private Map<NotebookLanguage, InterpreterService> interpreterServiceMap = new EnumMap<>(NotebookLanguage.class);

    @Autowired
    public InterpreterServiceFactory(List<InterpreterService> interpreterServices) {
        for (InterpreterService interpreterService: interpreterServices) {
            interpreterServiceMap.put(interpreterService.getInterpreterLanguage(), interpreterService);
        }
    }

    /**
     * get NotebookLanguage Service from language
     * @param language
     * @return
     * @throws LanguageNotSupportedException in case no service service mapped to the given language
     */
    public InterpreterService getInterpreterService(String language) {
        NotebookLanguage notebookLanguage = NotebookLanguage.getSupportedNotebookLanguageFromLanguageName(language);
        if (notebookLanguage == null || !interpreterServiceMap.containsKey(notebookLanguage)) {
            throw new LanguageNotSupportedException();
        }
        return interpreterServiceMap.get(notebookLanguage);
    }
}
