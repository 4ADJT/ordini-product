package io.ordini.products.adapter.gateway.db;

import io.ordini.products.domain.repository.IProcessedFileRepository;

import io.ordini.products.infrastructure.persistence.jpa.db.IProcessedFileJpaRepository;
import io.ordini.products.infrastructure.persistence.jpa.entity.ProcessedFileEntity;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@AllArgsConstructor
public class ProcessedFileRepositoryImpl implements IProcessedFileRepository {
  private final IProcessedFileJpaRepository repository;

  @Override
  public ProcessedFileEntity save(ProcessedFileEntity processedFileEntity) {
    return null;
  }

  @Override
  public ProcessedFileEntity findByName(String name) {
    return null;
  }

  @Override
  public Set<ProcessedFileEntity> findAll() {
    return Set.of();
  }
}
