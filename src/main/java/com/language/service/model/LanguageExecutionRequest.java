package com.language.service.model;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class LanguageExecutionRequest {
    String language;
    String code;
    String interactionId;


}
