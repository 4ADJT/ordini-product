package io.ordini.products.application;

import io.ordini.products.domain.model.ProductModel;
import io.ordini.products.domain.repository.IProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateProductUseCase {
  private final IProductRepository repository;

  public ProductModel execute(ProductModel product, int quantity) {
    if(quantity >= 0) product.setStock(quantity);
    return repository.update(product, quantity);
  }

}
