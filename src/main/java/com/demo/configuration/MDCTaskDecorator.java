package com.demo.configuration;

import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

import java.util.Map;

public class MDCTaskDecorator implements TaskDecorator {

  @Override
  public Runnable decorate(Runnable runnable) {
    final Map<String, String> originalContextCopy = MDC.getCopyOfContextMap();
    Runnable decoratedCommand = () -> {
      final Map<String, String> localContextCopy = MDC.getCopyOfContextMap();

      redefine(originalContextCopy);

      runnable.run();

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