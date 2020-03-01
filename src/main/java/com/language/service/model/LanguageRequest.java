package com.language.service.model;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class LanguageRequest {
    private String code;
    private String interactionId;


}
