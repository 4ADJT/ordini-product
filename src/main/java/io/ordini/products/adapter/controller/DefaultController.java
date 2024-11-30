package io.ordini.products.adapter.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class DefaultController {

  private record BodyResponse(String status) {
  }

  private final BodyResponse bodyResponse;

  public DefaultController() {
    this.bodyResponse = new BodyResponse("ok");
  }

  @GetMapping
  @Operation(hidden = true)
  public ResponseEntity<BodyResponse> health() {
    return ResponseEntity.status(HttpStatus.OK).body(bodyResponse);
  }
}
