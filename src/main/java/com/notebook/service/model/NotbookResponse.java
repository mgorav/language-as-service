package com.notebook.service.model;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotbookResponse {
    private String response;
    private String errors;
    private String interactionId;


}
