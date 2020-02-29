package com.notebook.service.model;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class NotebookResponse {
    private String response;
    private String errors;
    private String interactionId;


}
