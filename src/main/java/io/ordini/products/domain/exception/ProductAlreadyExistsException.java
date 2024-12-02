package io.ordini.products.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ProductAlreadyExistsException extends RuntimeException {
  private HttpStatus status;

  public ProductAlreadyExistsException(String message) {
    super(message);
  }

  public ProductAlreadyExistsException(String defaultMessage, HttpStatus status) {
    super(defaultMessage);
    this.status = status;
  }
}
