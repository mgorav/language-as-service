package com.language.service;

import com.language.service.model.Language;
import com.language.service.model.exception.LanguageExecutionLanguageNotSupportedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Factory for all Language Services
 *
 */
@Service
public class NotebookLanguageServiceFactory {

    // Food for thought: use enum map
    private Map<Language, NotebookLanguageService> interpreterServiceMap = new EnumMap<>(Language.class);

    @Autowired
    public NotebookLanguageServiceFactory(List<NotebookLanguageService> notebookLanguageServices) {
        for (NotebookLanguageService notebookLanguageService : notebookLanguageServices) {
            interpreterServiceMap.put(notebookLanguageService.getInterpreterLanguage(), notebookLanguageService);
        }
    }

    /**
     * get Language Service from language
     * @param language
     * @return
     * @throws LanguageExecutionLanguageNotSupportedException in case no service service mapped to the given language
     */
    public NotebookLanguageService getInterpreterService(String language) {
        Language notebookLanguage = Language.getSupportedNotebookLanguageFromLanguageName(language);
        if (notebookLanguage == null || !interpreterServiceMap.containsKey(notebookLanguage)) {
            throw new LanguageExecutionLanguageNotSupportedException();
        }
        return interpreterServiceMap.get(notebookLanguage);
    }
}
