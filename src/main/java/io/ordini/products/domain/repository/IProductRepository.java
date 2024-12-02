package io.ordini.products.domain.repository;

import io.ordini.products.domain.model.ProductModel;

public interface IProductRepository {

  ProductModel save(ProductModel productModel);
  ProductModel findByName(String name);

}
