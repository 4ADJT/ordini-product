package io.ordini.products.adapter.gateway.db;

import io.ordini.products.domain.repository.IProductRepository;
import io.ordini.products.infrastructure.persistence.jpa.db.IProductJpaRepository;
import io.ordini.products.infrastructure.persistence.jpa.entity.ProductEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@AllArgsConstructor
public class ProductRepositoryImpl implements IProductRepository {
  private final IProductJpaRepository repository;


  @Override
  public ProductEntity save(ProductEntity productModel) {
    return null;
  }

  @Override
  public ProductEntity update(ProductEntity productModel) {
    return null;
  }

  @Override
  public Page<ProductEntity> findAll(Pageable pageable) {
    return null;
  }

  @Override
  public ProductEntity findById(UUID id) {
    return null;
  }

  @Override
  public ProductEntity findByName(String name) {
    return null;
  }
}
