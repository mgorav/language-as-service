package com.language.service.model;

import lombok.*;
import org.graalvm.polyglot.Context;

import java.io.ByteArrayOutputStream;

@Data
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class GraalExecutionContext {

    private ByteArrayOutputStream outputStream;
    private ByteArrayOutputStream errorsStream;
    private Context context;
    private boolean timedOut = false;

    public GraalExecutionContext(ByteArrayOutputStream outputStream, ByteArrayOutputStream errorsStream, Context context) {
        this.outputStream = outputStream;
        this.errorsStream = errorsStream;
        this.context = context;
    }

    public String getOutput() {
        String out = this.outputStream.toString();
        this.outputStream.reset();
        return out;
    }


    public String getErrors() {
        String out = this.errorsStream.toString();
        this.errorsStream.reset();
        return out;
    }

}
