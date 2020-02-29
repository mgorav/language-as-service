package com.notebook.service.configurer;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "global-settings")
@Data
@Getter
@Setter
@NoArgsConstructor
public class AppProperties {

	private Long timeOutDuration;


}
