package io.ordini.products.adapter.controller;

import io.ordini.products.adapter.presenter.ProductPresenter;
import io.ordini.products.application.FindProductByNameUseCase;
import io.ordini.products.domain.model.ProductModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
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
  public ResponseEntity<PagedModel<ProductPresenter.ProductResponse>> findProductByName(@PathVariable String name,
                                                                                        Pageable pageable) {
    Page<ProductModel> product = findProductByNameUseCase.execute(name, pageable);

    Page<ProductPresenter.ProductResponse> products = product.map(productModel ->
        ProductPresenter.ProductResponse.builder()
            .id(productModel.getId())
            .name(productModel.getName())
            .description(productModel.getDescription())
            .price(productModel.getPrice())
            .stock(productModel.getStock())
            .currency(productModel.getCurrency())
            .createdAt(productModel.getCreatedAt())
            .build());

    return ResponseEntity.status(200).body(new PagedModel<>(products));

  }


}
