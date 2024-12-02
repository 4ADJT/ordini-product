package io.ordini.products.adapter.presenter;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class ProductPresenter {

  @Builder
  public record ProductRequest(
      String name,
      String description,
      BigDecimal price,
      int stock,
      String currency
  ) {
  }

  @Builder
  public record ProductResponse(
      UUID id,
      String name,
      String description,
      BigDecimal price,
      int stock,
      String currency,
      LocalDateTime createdAt
  ) {}

}
