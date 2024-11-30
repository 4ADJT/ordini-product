package io.ordini.products.domain.repository;

import io.ordini.products.infrastructure.persistence.jpa.entity.ProcessedFileEntity;

import java.util.Set;

public interface IProcessedFileRepository {

  ProcessedFileEntity save(ProcessedFileEntity processedFileEntity);
  ProcessedFileEntity findByName(String name);
  Set<ProcessedFileEntity> findAll();

}
