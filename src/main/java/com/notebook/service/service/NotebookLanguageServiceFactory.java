package com.notebook.service.service;

import com.notebook.service.model.NotebookLanguage;
import com.notebook.service.model.exception.NotebookLanguageNotSupportedException;
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
public class NotebookLanguageServiceFactory {

    // Food for thought: use enum map
    private Map<NotebookLanguage, NotebookLanguageService> interpreterServiceMap = new EnumMap<>(NotebookLanguage.class);

    @Autowired
    public NotebookLanguageServiceFactory(List<NotebookLanguageService> notebookLanguageServices) {
        for (NotebookLanguageService notebookLanguageService : notebookLanguageServices) {
            interpreterServiceMap.put(notebookLanguageService.getInterpreterLanguage(), notebookLanguageService);
        }
    }

    /**
     * get NotebookLanguage Service from language
     * @param language
     * @return
     * @throws NotebookLanguageNotSupportedException in case no service service mapped to the given language
     */
    public NotebookLanguageService getInterpreterService(String language) {
        NotebookLanguage notebookLanguage = NotebookLanguage.getSupportedNotebookLanguageFromLanguageName(language);
        if (notebookLanguage == null || !interpreterServiceMap.containsKey(notebookLanguage)) {
            throw new NotebookLanguageNotSupportedException();
        }
        return interpreterServiceMap.get(notebookLanguage);
    }
}
