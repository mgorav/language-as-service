package com.notebook.service.model;

public enum NotebookLanguage {
	PYTHON("python"),
	JAVA_SCRIPT("js");

	private String name;
	NotebookLanguage(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static NotebookLanguage getSupportedNotebookLanguageFromLanguageName(String language) {
		for (NotebookLanguage notebookLanguage : NotebookLanguage.values()) {
			if (notebookLanguage.name.equalsIgnoreCase(language)) {
				return notebookLanguage;
			}
		}

		return null; // add default ?
	}
}
