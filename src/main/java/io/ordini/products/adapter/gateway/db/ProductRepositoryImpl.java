package io.ordini.products.adapter.gateway.db;

import io.ordini.products.adapter.mapper.ProductMapper;
import io.ordini.products.domain.exception.DatabaseException;
import io.ordini.products.domain.model.ProductModel;
import io.ordini.products.domain.repository.IProductRepository;
import io.ordini.products.infrastructure.persistence.jpa.db.IProductJpaRepository;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ProductRepositoryImpl implements IProductRepository {
  private final IProductJpaRepository repository;
  private final ProductMapper mapper;

  @Override
  public ProductModel save(ProductModel productModel) {
    try {
      var product = repository.save(mapper.toEntity(productModel));
      return mapper.toModel(product);
    } catch (Exception e) {
      throw new DatabaseException("Erro ao salvar o produto.", HttpStatus.BAD_REQUEST);
    }
  }

  @Override
  public ProductModel findByName(String name) {
    try {
      var product = repository.findByName(name);
      return mapper.toModel(product);
    } catch (Exception e) {
      throw new DatabaseException("Erro ao buscar o produto.", HttpStatus.BAD_REQUEST);
    }
  }
}
