package io.ordini.products.adapter.controller;

import io.ordini.products.adapter.presenter.ProductPresenter;
import io.ordini.products.application.FindProductByIdUseCase;
import io.ordini.products.application.UpdateProductUseCase;
import io.ordini.products.domain.model.ProductModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
@Tag(name = "Products", description = "Products API")
public class UpdateProductController {

  private UpdateProductUseCase updateProductUseCase;
  private FindProductByIdUseCase findProductByIdUseCase;

  @PutMapping("/{id}/{quantity}")
  @Operation(summary = "Update product quantity", description = "Update product quantity.")
  public ResponseEntity<ProductPresenter.ProductResponse> updateProductQuantity(
      @PathVariable UUID id,
      @PathVariable int quantity
      ) {

    ProductModel searchProduct = findProductByIdUseCase.execute(id);

    if(searchProduct == null) {
      throw new RuntimeException("Product not found");
    }

    if(quantity < 0) {
      throw new RuntimeException("Quantity must be greater than 0");
    }

    searchProduct.setStock(quantity);

    ProductModel returned = updateProductUseCase.execute(searchProduct, quantity);

    ProductPresenter.ProductResponse response = ProductPresenter.ProductResponse.builder()
        .id(returned.getId())
        .name(returned.getName())
        .price(returned.getPrice())
        .stock(returned.getStock())
        .build();


    return ResponseEntity.status(200).body(response);
  }


}
