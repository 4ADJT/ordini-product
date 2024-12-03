package io.ordini.products.adapter.controller;

//import io.ordini.products.application.files.FileService;
//import io.ordini.products.domain.repository.IProcessedFileRepository;
//import io.ordini.products.infrastructure.batch.ProductsBatchConfiguration;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import java.util.Map;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class DefaultController {

//  private FileService fileService;
//  private IProcessedFileRepository processedFileRepository;
//  private ProductsBatchConfiguration productsBatchConfiguration;

  private record BodyResponse(String status) {
  }

  @GetMapping
  @Operation(hidden = true)
  public ResponseEntity<BodyResponse> health() {
    return ResponseEntity.status(HttpStatus.OK).body(new BodyResponse("ok"));
  }

//  @GetMapping("/bucket/objects")
//  @Operation(summary = "List all objects in the bucket")
//  public ResponseEntity<Map<String, Map<String, Object>> > listObjectsInBucket() {
//
//    Map<String, Map<String, Object>> fileContents = fileService.getFilesAndContent();
//    productsBatchConfiguration.runJobsForAllFiles();
//
//    return ResponseEntity.status(200).body(fileContents);
//  }
}
