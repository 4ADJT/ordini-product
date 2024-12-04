package io.ordini.products.application;

import io.ordini.products.domain.exception.ProductNotExistsException;
import io.ordini.products.domain.model.ProductModel;
import io.ordini.products.domain.repository.IProductRepository;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FindProductByNameUseCase {
  private final IProductRepository repository;

  public Page<ProductModel> execute(String name, Pageable p) {
    try {
      return repository.findAllByName(name.toUpperCase(), p);
    } catch (Exception e) {
      throw new ProductNotExistsException("Error finding product by name", HttpStatus.BAD_REQUEST);
    }

  }

}
