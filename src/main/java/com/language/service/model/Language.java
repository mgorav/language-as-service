package com.language.service.model;

public enum Language {
	PYTHON("python"),
	JAVA_SCRIPT("js");

	private String name;
	Language(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static Language getSupportedNotebookLanguageFromLanguageName(String language) {
		for (Language notebookLanguage : Language.values()) {
			if (notebookLanguage.name.equalsIgnoreCase(language)) {
				return notebookLanguage;
			}
		}

		return null; // add default ?
	}
}
