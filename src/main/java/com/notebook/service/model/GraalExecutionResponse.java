package com.notebook.service.model;

public class GraalExecutionResponse {
    String output;
    String errors;

    public GraalExecutionResponse() {
        // no args constructor
    }

    public GraalExecutionResponse(String output, String errors) {
        this.output = output;
        this.errors = errors;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }
}
