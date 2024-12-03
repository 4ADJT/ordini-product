package io.ordini.products.application;

import io.ordini.products.domain.model.ProductModel;
import io.ordini.products.domain.repository.IProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class FindProductByIdUseCase {
  private final IProductRepository repository;

  public ProductModel execute(UUID id) {
    return repository.findById(id);
  }

}
