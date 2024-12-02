package io.ordini.products.adapter.controller;

import io.ordini.products.application.CreateProductServiceUseCase;
import io.ordini.products.domain.model.ProductModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
@Tag(name = "Products", description = "Products API")
public class CreateProductController {

  private CreateProductServiceUseCase  createProductServiceUseCase;

  @PostMapping("/")
  @Operation(summary = "Create a new product", description = "Create a new product")
  public ResponseEntity<ProductModel> createProduct(ProductModel productModel) {

    return ResponseEntity.ok(createProductServiceUseCase.execute(productModel));
  }

}
