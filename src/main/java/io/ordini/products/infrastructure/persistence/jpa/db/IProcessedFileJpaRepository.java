package io.ordini.products.infrastructure.persistence.jpa.db;

import io.ordini.products.infrastructure.persistence.jpa.entity.ProcessedFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IProcessedFileJpaRepository extends JpaRepository<ProcessedFileEntity, UUID> {
  ProcessedFileEntity findByFileName(String fileName);
}
