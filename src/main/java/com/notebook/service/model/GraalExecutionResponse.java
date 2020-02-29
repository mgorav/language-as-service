package com.notebook.service.model;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class GraalExecutionResponse {
    String output;
    String errors;


}
