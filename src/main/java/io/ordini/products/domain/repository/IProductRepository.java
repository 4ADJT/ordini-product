package io.ordini.products.domain.repository;

import io.ordini.products.domain.model.ProductModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductRepository {

  ProductModel save(ProductModel productModel);
  ProductModel findByName(String name);
  Page<ProductModel> findAllByName(String name, Pageable pageable);

}
