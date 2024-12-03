package io.ordini.products.application;

import io.ordini.products.domain.model.ProductModel;
import io.ordini.products.domain.repository.IProductRepository;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FindProductByNameUseCase {
  private final IProductRepository repository;

  public ProductModel execute(String name) {
    return repository.findByName(name.toUpperCase());
  }

}
