package io.ordini.products.application;

import io.ordini.products.domain.model.ProductModel;
import io.ordini.products.domain.repository.IProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FindAllProductsUseCase {

    private final IProductRepository productRepository;

    public Page<ProductModel> execute(Pageable pageable) {

        return productRepository.findAll(pageable);

    }

}
