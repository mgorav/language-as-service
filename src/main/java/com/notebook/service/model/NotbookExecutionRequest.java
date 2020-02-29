package com.notebook.service.model;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotbookExecutionRequest {
    String language;
    String code;
    String interactionId;


}
