package io.ordini.products.infrastructure.batch;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@AllArgsConstructor
public class ProductsBatchScheduler {

  private final ProductsBatchConfiguration batchConfiguration;

  @Scheduled(cron = "0 0 * * * ?")
  public void scheduleProductsBatch() {
    batchConfiguration.runJobsForAllFiles();
  }
}
