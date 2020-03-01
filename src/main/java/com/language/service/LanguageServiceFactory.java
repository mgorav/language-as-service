package com.language.service;

import com.language.service.model.Language;
import com.language.service.model.exception.LanguageExecutionLanguageNotSupportedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Language Factory for all Language Services
 *
 */
@Service
public class LanguageServiceFactory {

    // Food for thought: use enum map
    private Map<Language, LanguageService> interpreterServiceMap = new EnumMap<>(Language.class);

    @Autowired
    public LanguageServiceFactory(List<LanguageService> languageServices) {
        for (LanguageService languageService : languageServices) {
            interpreterServiceMap.put(languageService.getInterpreterLanguage(), languageService);
        }
    }

    /**
     * get Language Service from language
     * @param language
     * @return
     * @throws LanguageExecutionLanguageNotSupportedException in case no service service mapped to the given language
     */
    public LanguageService getInterpreterService(String language) {
        Language notebookLanguage = Language.getSupportedNotebookLanguageFromLanguageName(language);
        if (notebookLanguage == null || !interpreterServiceMap.containsKey(notebookLanguage)) {
            throw new LanguageExecutionLanguageNotSupportedException();
        }
        return interpreterServiceMap.get(notebookLanguage);
    }
}
