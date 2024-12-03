package io.ordini.products.adapter.controller;

import io.ordini.products.adapter.presenter.ProductPresenter;
import io.ordini.products.application.FindProductByNameUseCase;
import io.ordini.products.domain.model.ProductModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
@Tag(name = "Products", description = "Products API")
public class FindProductByNameController {

  private final FindProductByNameUseCase findProductByNameUseCase;

  @GetMapping(value = "/find-name/{name}", produces = "application/json")
  @Operation(summary = "Find product by name", description = "Find product by name.")
  public ResponseEntity<ProductPresenter.ProductResponse> findProductByName(@PathVariable String name) {
    ProductModel product = findProductByNameUseCase.execute(name);

    ProductPresenter.ProductResponse response = ProductPresenter.ProductResponse.builder()
        .id(product.getId())
        .name(product.getName())
        .description(product.getDescription())
        .price(product.getPrice())
        .stock(product.getStock())
        .currency(product.getCurrency())
        .createdAt(product.getCreatedAt())
        .build();

    return ResponseEntity.ok().body(response);
  }


}
