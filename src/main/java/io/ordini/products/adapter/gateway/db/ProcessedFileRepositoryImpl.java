package io.ordini.products.adapter.gateway.db;

import io.ordini.products.adapter.mapper.ProcessedFileMapper;
import io.ordini.products.domain.exception.DatabaseException;
import io.ordini.products.domain.model.ProcessedFileModel;
import io.ordini.products.domain.repository.IProcessedFileRepository;

import io.ordini.products.infrastructure.persistence.jpa.db.IProcessedFileJpaRepository;
import io.ordini.products.infrastructure.persistence.jpa.entity.ProcessedFileEntity;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
@AllArgsConstructor
public class ProcessedFileRepositoryImpl implements IProcessedFileRepository {
  private final IProcessedFileJpaRepository repository;
  private final ProcessedFileMapper mapper;

  @Override
  public ProcessedFileModel save(ProcessedFileModel processedFileModel) {
    try {
      var processedFile = repository.save(mapper.toEntity(processedFileModel));
      return mapper.toModel(processedFile);
    } catch (Exception e) {
      throw new DatabaseException("Erro ao salvar o arquivo processado.", HttpStatus.BAD_REQUEST);
    }
  }

  @Override
  public ProcessedFileModel findByName(String name) {
    try {
      var processedFile = repository.findByFileName(name);
      return mapper.toModel(processedFile);
    } catch (Exception e) {
      throw new DatabaseException("Erro ao buscar o arquivo processado.", HttpStatus.BAD_REQUEST);
    }
  }

  @Override
  public Set<ProcessedFileModel> findAll() {
    Set<ProcessedFileEntity> filesSet = new HashSet<>();

    try {
      List<ProcessedFileEntity> processedFiles = repository.findAll();
      Stream<ProcessedFileEntity> entityStream;
      entityStream = processedFiles.stream().peek(
          file -> {
            if (file != null) {
              filesSet.add(file);
            }
          });
      return entityStream.map(mapper::toModel).collect(Collectors.toSet());
    } catch (Exception e) {
      throw new DatabaseException("Erro ao coletar os arquivos processados.", HttpStatus.BAD_REQUEST);
    }
  }
}
