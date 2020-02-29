package com.notebook.service.model;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class NotebookRequest {
    private String code;
    private String interactionId;


}
