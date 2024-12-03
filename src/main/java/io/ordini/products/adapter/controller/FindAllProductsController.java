package io.ordini.products.adapter.controller;

import io.ordini.products.adapter.presenter.ProductPresenter;
import io.ordini.products.application.FindAllProductsUseCase;
import io.ordini.products.domain.model.ProductModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@Tag(name = "Products", description = "Products API")
@RestController
@RequestMapping("/products")
public class FindAllProductsController {

  private final FindAllProductsUseCase findAllProductsUseCase;

  @GetMapping(value = "/all", produces = "application/json")
  @Operation(summary = "Find all products", description = "Find all products.")
  public ResponseEntity<PagedModel<ProductPresenter.ProductResponse>> findAllProducts(
      @PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC, page = 0)
      Pageable pageable) {
    Page<ProductModel> products = findAllProductsUseCase.execute(pageable);

    Page<ProductPresenter.ProductResponse> response = products.map(product ->
        ProductPresenter.ProductResponse.builder()
        .id(product.getId())
        .name(product.getName())
        .description(product.getDescription())
        .price(product.getPrice())
        .stock(product.getStock())
        .currency(product.getCurrency())
        .createdAt(product.getCreatedAt())
        .build());

    return ResponseEntity.ok(new PagedModel<>(response));
  }

}
