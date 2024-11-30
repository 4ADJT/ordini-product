package io.ordini.products.domain.repository;

import io.ordini.products.infrastructure.persistence.jpa.entity.ProductEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IProductRepository {

  ProductEntity save(ProductEntity productModel);
  ProductEntity update(ProductEntity productModel);
  Page<ProductEntity> findAll(Pageable pageable);
  ProductEntity findById(UUID id);
  ProductEntity findByName(String name);

}
