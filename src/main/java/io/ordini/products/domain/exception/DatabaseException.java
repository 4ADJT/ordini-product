package io.ordini.products.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DatabaseException extends RuntimeException {
  private HttpStatus status;

  public DatabaseException(String message) {
    super(message);
  }

  public DatabaseException(String defaultMessage, HttpStatus status) {
    super(defaultMessage);
    this.status = status;
  }
}
