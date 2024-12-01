package io.ordini.products.adapter.gateway.bucket;

import io.ordini.products.domain.exception.BucketException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Slf4j
public class GetBucketFileContent {

  private final S3Client s3Client;

  private static final String BUCKET_NAME = "ordini";
  private static final String PREFIX = "products";

  public String getFileContent(String fileName) {
    String key = PREFIX + "/" + fileName;

    GetObjectRequest getObjectRequest = GetObjectRequest.builder()
        .bucket(BUCKET_NAME)
        .key(key)
        .build();

    try (ResponseInputStream<?> inputStream = s3Client.getObject(getObjectRequest);
         BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

      return reader.lines().collect(Collectors.joining("\n"));

    } catch (Exception e) {
      log.error("Erro ao capturar o arquivo: " + fileName + " error: " + e.getMessage());
      throw new BucketException("Erro ao capturar o arquivo: " + fileName + " error: " + e.getMessage(),
          HttpStatus.BAD_REQUEST);
    }
  }

}
