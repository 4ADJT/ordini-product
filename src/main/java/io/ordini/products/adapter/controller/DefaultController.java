package io.ordini.products.adapter.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class DefaultController {

  private final S3Client s3Client;
  private static final String BUCKET_NAME = "ordini";

  public DefaultController(S3Client s3Client) {
    this.s3Client = s3Client;
  }

  private record BodyResponse(String status) {
  }

  private final BodyResponse bodyResponse = new BodyResponse("ok");

  @GetMapping
  @Operation(hidden = true)
  public ResponseEntity<BodyResponse> health() {
    return ResponseEntity.status(HttpStatus.OK).body(bodyResponse);
  }

  @GetMapping("/bucket/objects")
  @Operation(summary = "List all objects in the bucket")
  public ResponseEntity<List<String>> listObjectsInBucket() {
    ListObjectsV2Request request = ListObjectsV2Request.builder()
        .bucket(BUCKET_NAME)
        .prefix("products")
        .build();

    List<String> objectKeys = s3Client.listObjectsV2(request)
        .contents()
        .stream()
        .map(S3Object -> S3Object.key().replace("products/", ""))
        .collect(Collectors.toList());

    return ResponseEntity.ok(objectKeys);
  }
}
