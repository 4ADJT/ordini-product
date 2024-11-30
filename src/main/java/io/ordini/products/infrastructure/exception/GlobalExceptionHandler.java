package io.ordini.products.infrastructure.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({ResourceNotFoundException.class, NoResourceFoundException.class, NoHandlerFoundException.class})
  public ResponseEntity<Object> handleNotFoundExceptions(Exception ex, WebRequest request) {
    HttpStatus status = HttpStatus.NOT_FOUND;
    Map<String, Object> body = new HashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("message", ex instanceof NoHandlerFoundException ?
        "No handler found for this request." : "Resource not found.");
    body.put("details", ex.getMessage());
    body.put("path", ((ServletWebRequest) request).getRequest().getRequestURI());
    body.put("status", status.value());

    log.warn("Not found exception: {}", ex.getMessage());
    return new ResponseEntity<>(body, status);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    Map<String, Object> body = new HashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("message", "Validation error.");
    body.put("path", ((ServletWebRequest) request).getRequest().getRequestURI());
    body.put("status", status.value());

    Map<String, String> fieldErrors = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(error ->
        fieldErrors.put(error.getField(), error.getDefaultMessage()));

    body.put("errors", fieldErrors);

    log.warn("Validation error: {}", ex.getMessage());
    return new ResponseEntity<>(body, status);
  }

  @ExceptionHandler({RuntimeException.class, Exception.class})
  public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    try {
      Method getStatusMethod = ex.getClass().getMethod("getStatus");
      if (HttpStatus.class.isAssignableFrom(getStatusMethod.getReturnType())) {
        status = (HttpStatus) getStatusMethod.invoke(ex);
      }
    } catch (NoSuchMethodException | SecurityException e) {
      log.debug("Status method not found or inaccessible: {}", e.getMessage());
    } catch (Exception e) {
      log.error("Error while extracting status: {}", e.getMessage(), e);
    }

    Map<String, Object> body = new HashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("message", ex instanceof RuntimeException ? ex.getMessage() : "Unexpected error.");
    body.put("details", ex.getMessage());
    body.put("path", ((ServletWebRequest) request).getRequest().getRequestURI());
    body.put("status", status.value());

    log.error("Exception occurred: {}", ex.getMessage(), ex);
    return new ResponseEntity<>(body, status);
  }
}
