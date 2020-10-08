package org.demo.store.service.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.function.Supplier;

@Configuration
public class CoreContext {

  @Bean
  public Clock clock() {
    return Clock.systemUTC();
  }

  @Bean
  public Supplier<LocalDateTime> dateTimeSupplier(Clock clock) {
    return () -> LocalDateTime.now(clock);
  }
}
