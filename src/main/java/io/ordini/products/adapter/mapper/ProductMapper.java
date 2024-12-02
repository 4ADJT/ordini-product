package io.ordini.products.adapter.mapper;

import io.ordini.products.domain.model.ProductModel;
import io.ordini.products.infrastructure.persistence.jpa.entity.ProductEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

  ProductModel toModel(ProductEntity entity);

  @InheritConfiguration
  @Mapping(target = "sourceFile", source = "sourceFile")
  ProductEntity toEntity(ProductModel model);
}
