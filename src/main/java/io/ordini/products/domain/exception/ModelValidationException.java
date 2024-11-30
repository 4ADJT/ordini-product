package io.ordini.products.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ModelValidationException extends IllegalArgumentException {
  private HttpStatus status;

  public ModelValidationException(String message) {
    super(message);
  }

  public ModelValidationException(String defaultMessage, HttpStatus status) {
    super(defaultMessage);
    this.status = status;
  }

}
