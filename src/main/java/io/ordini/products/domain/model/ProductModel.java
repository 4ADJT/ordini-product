package io.ordini.products.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductModel {

  private UUID id;

  private String name;

  private String description;

  private BigDecimal price;

  private Integer stock;

  private String sourceFile;

  private String createdAt;

  private String updatedAt;

}
