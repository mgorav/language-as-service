package com.notebook.service.configurer;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "global-settings")
public class AppProperties {

	private Long timeOutDuration;

	public Long getTimeOutDuration() {
		return timeOutDuration;
	}

	public void setTimeOutDuration(Long timeOutDuration) {
		this.timeOutDuration = timeOutDuration;
	}
}
