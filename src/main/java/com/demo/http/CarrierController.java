package com.demo.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by mballoni on 06/02/17.
 */
@Slf4j
@RestController
public class CarrierController {

  @Async
  @PostMapping(value = "/carrier", consumes = MediaType.APPLICATION_JSON_VALUE)
  public void select(@RequestHeader("responseTo") String target,
      @RequestHeader(value = "select", required = false) Integer selectedIndex) {
    log.debug(" Sending to target {} and index {}", target, selectedIndex);

    try {
      log.info("Pretending to do some work...");
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      log.error("Failed!", e);
    }

    log.info("Execution finished");
  }
}
