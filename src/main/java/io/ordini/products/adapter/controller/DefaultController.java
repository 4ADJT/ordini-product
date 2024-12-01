package io.ordini.products.adapter.controller;

import io.ordini.products.adapter.gateway.bucket.GetBucketFileList;
import io.ordini.products.application.files.FileService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class DefaultController {

  private final GetBucketFileList getBucketFileList;
  private FileService fileService;

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
  public ResponseEntity<Map<String, String>> listObjectsInBucket() {


      // Processa os arquivos e obtém o conteúdo no mapa
      Map<String, String> fileContents = fileService.processFilesAndReturnContents();

      // Exibe os arquivos e seus conteúdos
      fileContents.forEach((fileName, content) -> {
        System.out.println("Arquivo: " + fileName);
        System.out.println("Conteúdo:");
        System.out.println(content);
        System.out.println("------------------------");
      });


    return ResponseEntity.status(200).body(fileContents);
  }
}
