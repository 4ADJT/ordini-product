package io.ordini.products.adapter.mapper;

import io.ordini.products.domain.model.ProcessedFileModel;
import io.ordini.products.infrastructure.persistence.jpa.entity.ProcessedFileEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProcessedFileMapper {

  ProcessedFileModel toModel(ProcessedFileEntity entity);

  @InheritConfiguration
  ProcessedFileEntity toEntity(ProcessedFileModel model);

}
