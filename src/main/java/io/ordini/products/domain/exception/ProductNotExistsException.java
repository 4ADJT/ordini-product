package io.ordini.products.domain.exception;

import org.springframework.http.HttpStatus;

public class ProductNotExistsException extends RuntimeException {
  private HttpStatus status;

  public ProductNotExistsException(String message) {
    super(message);
  }

  public ProductNotExistsException(String defaultMessage, HttpStatus status) {
    super(defaultMessage);
    this.status = status;
  }
}
