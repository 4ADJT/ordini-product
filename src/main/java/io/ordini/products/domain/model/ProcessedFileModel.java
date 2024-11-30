package io.ordini.products.domain.model;

import io.ordini.products.domain.exception.ModelValidationException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessedFileModel {

  private UUID id;

  private String fileName;

  private LocalDateTime processedAt;

  public static ProcessedFileModel buildAndValidate(ProcessedFileModelBuilder builder) {
    ProcessedFileModel model = builder.build();
    model.validate();
    return model;
  }

  public void validate() {
    validateFileName();
    validateProcessedAt();
  }

  private void validateFileName() {
    if (fileName == null || fileName.trim().isEmpty()) {
      throw new ModelValidationException("O nome do arquivo não pode ser nulo ou vazio.", HttpStatus.BAD_REQUEST);
    }
  }

  private void validateProcessedAt() {
    if (processedAt == null) {
      throw new ModelValidationException("A data de processamento não pode ser nula.", HttpStatus.BAD_REQUEST);
    }
    if (processedAt.isAfter(LocalDateTime.now())) {
      throw new ModelValidationException("A data de processamento não pode ser no futuro.", HttpStatus.BAD_REQUEST);
    }
  }

}
