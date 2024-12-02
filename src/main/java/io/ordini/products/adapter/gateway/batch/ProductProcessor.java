package io.ordini.products.adapter.gateway.batch;

import io.ordini.products.adapter.mapper.ProductMapper;
import io.ordini.products.domain.model.ProductModel;
import io.ordini.products.infrastructure.persistence.jpa.entity.ProductEntity;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ProductProcessor implements ItemProcessor<ProductModel, ProductEntity> {
  public final ProductMapper productMapper;


  @Override
  public ProductEntity process(ProductModel productModel) throws Exception {
    productModel.setSourceFile(productModel.getSourceFile());
    return productMapper.toEntity(productModel);

  }
}
