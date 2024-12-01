package io.ordini.products.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BucketException extends RuntimeException {
  private HttpStatus status;

  public BucketException(String message) {
    super(message);
  }

  public BucketException(String defaultMessage, HttpStatus status) {
    super(defaultMessage);
    this.status = status;
  }
}
