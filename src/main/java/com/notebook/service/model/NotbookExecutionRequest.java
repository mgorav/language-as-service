package com.notebook.service.model;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class NotbookExecutionRequest {
    String language;
    String code;
    String interactionId;


}
