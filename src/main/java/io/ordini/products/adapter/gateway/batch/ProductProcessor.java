package io.ordini.products.adapter.gateway.batch;

import io.ordini.products.adapter.mapper.ProductMapper;
import io.ordini.products.domain.model.ProductModel;
import io.ordini.products.infrastructure.persistence.jpa.db.IProductJpaRepository;
import io.ordini.products.infrastructure.persistence.jpa.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
@Slf4j
public class ProductProcessor implements ItemProcessor<ProductModel, ProductEntity> {
  public final ProductMapper productMapper;
  public final IProductJpaRepository productRepository;

  @Override
  public ProductEntity process(ProductModel productModel) throws Exception {

    if(productRepository.findByName(productModel.getName()) != null) {
      log.warn("Product with name {} already exists", productModel.getName());
      return null;
    }

    productModel.validate();

    return productMapper.toEntity(productModel);

  }
}
