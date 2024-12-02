package io.ordini.products.infrastructure.persistence.jpa.db;

import io.ordini.products.infrastructure.persistence.jpa.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IProductJpaRepository extends JpaRepository<ProductEntity, UUID> {
  ProductEntity findByName(String name);
}
