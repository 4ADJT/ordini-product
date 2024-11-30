package io.ordini.products.infrastructure.config;

import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

@Configuration
@AllArgsConstructor
public class ActuatorConfig {
  private final DataSource dataSource;

  private static final String APP = "Ordini Products";
  private static final String VERSION = "Ordini Products";
  private static final String DESCRIPTION = "Ordini Products Service";

  @Bean
  public HealthIndicator customHealthIndicator() {
    return () -> {
      boolean isHealthy = checkServiceHealth();
      if (isHealthy) {
        return Health.up().withDetail("status", "All systems operational.").build();
      } else {
        return Health.down().withDetail("status", "Service is down.").build();
      }
    };
  }

  @Bean
  public InfoContributor customInfoContributor() {
    return builder -> {
      builder.withDetail("app", Map.of("name", APP, "version", VERSION));
      builder.withDetail("description", DESCRIPTION);
    };
  }

  private boolean checkServiceHealth() {
    try (Connection connection = dataSource.getConnection()) {
      return connection.isValid(2);
    } catch (SQLException e) {
      return false;
    }
  }
}
