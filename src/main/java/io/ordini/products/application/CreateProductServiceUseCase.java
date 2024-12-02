package io.ordini.products.application;

import io.ordini.products.domain.exception.ProductAlreadyExistsException;
import io.ordini.products.domain.model.ProductModel;
import io.ordini.products.domain.repository.IProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class CreateProductServiceUseCase {
    private final IProductRepository repository;

    public ProductModel execute(ProductModel productModel) {

        if(repository.findByName(productModel.getName()) != null) {
            log.error("Product already exists");
            throw new ProductAlreadyExistsException("Product already exists",
                HttpStatus.BAD_REQUEST);
        }

        return repository.save(productModel);
    }

}
