package io.ordini.products.infrastructure.persistence.jpa.db;

import io.ordini.products.infrastructure.persistence.jpa.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface IProductJpaRepository extends JpaRepository<ProductEntity, UUID> {
  @Query("SELECT p FROM ProductEntity p WHERE UPPER(p.name) LIKE %?1%")
  ProductEntity findByName(String name);

  @Query("SELECT p FROM ProductEntity p WHERE UPPER(p.name) LIKE %?1%")
  Page<ProductEntity> findAllByName(String name, Pageable pageable);
}
