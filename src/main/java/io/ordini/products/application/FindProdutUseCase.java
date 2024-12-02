package io.ordini.products.application;

import io.ordini.products.domain.repository.IProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class FindProdutUseCase {
  private final IProductRepository repository;


}
