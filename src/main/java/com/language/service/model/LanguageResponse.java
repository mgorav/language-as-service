package com.language.service.model;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class LanguageResponse {
    private String response;
    private String errors;
    private String interactionId;


}
