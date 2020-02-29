package com.notebook.service.service;

import com.notebook.service.configuration.AppProperties;
import com.notebook.service.model.GraalExecutionContext;
import com.notebook.service.model.ExecutionRequest;
import com.notebook.service.model.GraalExecutionResponse;
import com.notebook.service.model.exception.NotebookLanguageNotSupportedException;
import com.notebook.service.model.exception.NoteboolLanguageTimeOutException;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

public abstract class GraalVmNotebookLanguageService implements NotebookLanguageService {

    @Autowired
    private AppProperties appProperties;

    private Map<String, GraalExecutionContext> sessionContexts = new ConcurrentHashMap<>();
    /**
     * {@inheritDoc}
     */
    @Override
    public GraalExecutionResponse execute(ExecutionRequest request) {

        // Check if language supported
        if (unsupportedLanguage()) {
            throw new NotebookLanguageNotSupportedException();
        }

        GraalExecutionContext graalExecutionContext = getContext(request.getSessionId());
        final Context context = graalExecutionContext.getContext();

        Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    context.close(true);
                    graalExecutionContext.getOutputStream().close();
                    graalExecutionContext.getErrorsStream().close();
                    graalExecutionContext.setTimedOut(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, appProperties.getTimeOutDuration());

        try {
            context.eval(getInterpreterLanguage().getName(), request.getCode());
            timer.cancel();
            timer.purge();
            return new GraalExecutionResponse(graalExecutionContext.getOutput(), graalExecutionContext.getErrors());
        } catch(PolyglotException e) {
            timer.cancel();
            timer.purge();
            if (e.isCancelled()) {
                // remove context
                sessionContexts.remove(request.getSessionId());
                throw new NoteboolLanguageTimeOutException();
            }

            // Food for thought: add polyglot exceptions handling ?
            return new GraalExecutionResponse("" , e.getMessage());
        }

    }

    private boolean unsupportedLanguage() {
        try (Context tmpContext = Context.create()) {
            return !tmpContext.getEngine().getLanguages().containsKey(getInterpreterLanguage().getName());
        }
    }

    /**
     * Get Execution Context by sessionId
     * @param sessionId
     * @return
     */
    private GraalExecutionContext getContext(String sessionId) {
        return sessionContexts.computeIfAbsent(sessionId, key -> buildContext());
    }

    private GraalExecutionContext buildContext() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ByteArrayOutputStream errorsStream = new ByteArrayOutputStream();
        Context context = Context.newBuilder(getInterpreterLanguage().getName()).out(outputStream).err(errorsStream)
                .build();

        return new GraalExecutionContext(outputStream, errorsStream, context);
    }
}
