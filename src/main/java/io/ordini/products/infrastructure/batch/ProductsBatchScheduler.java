package io.ordini.products.infrastructure.batch;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class ProductsBatchScheduler {

  private final ProductsBatchConfiguration batchConfiguration;

  public ProductsBatchScheduler(ProductsBatchConfiguration batchConfiguration) {
    this.batchConfiguration = batchConfiguration;
  }

  @Scheduled(cron = "0 0 * * * ?")
  public void scheduleProductsBatch() {
    batchConfiguration.runJobsForAllFiles();
  }
}
