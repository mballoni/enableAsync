package com.demo.util;

import org.slf4j.MDC;
import org.springframework.core.task.TaskExecutor;

import java.util.Map;
import java.util.concurrent.Executor;

/**
 * Created by mballoni on 08/02/17.
 */
public class ContextAwareExecutorDecorator implements Executor, TaskExecutor {
  private final Executor executor;

  public ContextAwareExecutorDecorator(Executor executor) {
    this.executor = executor;
  }

  @Override
  public void execute(Runnable command) {
    Runnable decoratedCommand = decorate(command);
    executor.execute(decoratedCommand);
  }


  private Runnable decorate(Runnable command) {
    final Map<String, String> originalContextCopy = MDC.getCopyOfContextMap();
    Runnable decoratedCommand = () -> {
      final Map<String, String> localContextCopy = MDC.getCopyOfContextMap();

      redefine(originalContextCopy);

      command.run();

      redefine(localContextCopy);
    };

    return decoratedCommand;
  }

  private void redefine(Map<String, String> contextCopy) {
    MDC.clear();
    if (contextCopy != null) {
      MDC.setContextMap(contextCopy);
    }
  }
}