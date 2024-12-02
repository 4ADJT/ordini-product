package io.ordini.products.domain.model;

import io.ordini.products.domain.exception.ModelValidationException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductModel {

  private UUID id;

  private String name;

  private String description;

  private BigDecimal price;

  private Integer stock;

  private String currency;

  private String sourceFile;

  private String createdAt;

  private String updatedAt;

  public static ProductModel buildAndValidate(ProductModelBuilder builder) {
    ProductModel model = builder.build();
    model.validate();
    return model;
  }

  public void validate() {
    validateName();
    validateDescription();
    validatePrice();
    validateStock();
    validateSourceFile();
    validateCurrency();
  }

  private void validateName() {
    if (name == null || name.trim().isEmpty()) {
      throw new ModelValidationException("O nome não pode ser nulo ou vazio.", HttpStatus.BAD_REQUEST);
    }
  }

  private void validateDescription() {
    if (description == null || description.trim().isEmpty()) {
      throw new ModelValidationException("A descrição não pode ser nula ou vazia.", HttpStatus.BAD_REQUEST);
    }
  }

  private void validatePrice() {
    if (price == null) {
      throw new ModelValidationException("O preço não pode ser nulo.", HttpStatus.BAD_REQUEST);
    }
    if (price.compareTo(BigDecimal.ZERO) < 0) {
      throw new ModelValidationException("O preço não pode ser inferior a zero.", HttpStatus.BAD_REQUEST);
    }
  }

  private void validateStock() {
    if (stock == null) {
      throw new ModelValidationException("O estoque não pode ser nulo.", HttpStatus.BAD_REQUEST);
    }
    if (stock < 0) {
      throw new ModelValidationException("O estoque não pode ser inferior a zero.", HttpStatus.BAD_REQUEST);
    }
  }

  private void validateSourceFile() {
    if (sourceFile == null || sourceFile.trim().isEmpty()) {
      throw new ModelValidationException("O arquivo de origem não pode ser nulo ou vazio.", HttpStatus.BAD_REQUEST);
    }
  }

  private void validateCurrency() {
    if (currency == null || currency.trim().isEmpty()) {
      throw new ModelValidationException("O tipo de moeda deve ser definida.", HttpStatus.BAD_REQUEST);
    }
  }

}
