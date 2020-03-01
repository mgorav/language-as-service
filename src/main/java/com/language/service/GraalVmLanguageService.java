package com.language.service;

import com.language.service.configurer.AppProperties;
import com.language.service.model.LanguageExecutionRequest;
import com.language.service.model.GraalExecutionContext;
import com.language.service.model.GraalExecutionResponse;
import com.language.service.model.exception.LanguageExecutionLanguageNotSupportedException;
import com.language.service.model.exception.LanguageExecutionTimeOutException;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

public abstract class GraalVmLanguageService implements LanguageService {

    @Autowired
    private AppProperties appProperties;

    private Map<String, GraalExecutionContext> sessionContexts = new ConcurrentHashMap<>();
    /**
     * {@inheritDoc}
     */
    @Override
    public GraalExecutionResponse execute(LanguageExecutionRequest request) {

        // Check if language supported
        if (unsupportedLanguage()) {
            throw new LanguageExecutionLanguageNotSupportedException();
        }

        GraalExecutionContext graalExecutionContext = getContext(request.getInteractionId());
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
                sessionContexts.remove(request.getInteractionId());
                throw new LanguageExecutionTimeOutException();
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
     * Get Execution Context by interactionId
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
