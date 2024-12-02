package io.ordini.products.domain.repository;

import io.ordini.products.domain.model.ProcessedFileModel;

import java.util.Set;

public interface IProcessedFileRepository {

  ProcessedFileModel save(ProcessedFileModel processedFileModel);
  ProcessedFileModel findByName(String name);
  Set<ProcessedFileModel> findAll();

}
