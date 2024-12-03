package io.ordini.products.domain.repository;

import io.ordini.products.domain.model.ProductModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IProductRepository {

  ProductModel save(ProductModel productModel);
  ProductModel findByName(String name);
  Page<ProductModel> findAllByName(String name, Pageable pageable);
  Page<ProductModel> findAll(Pageable pageable);
  ProductModel findById(UUID id);
  ProductModel update(ProductModel productModel, int quantity);
}
