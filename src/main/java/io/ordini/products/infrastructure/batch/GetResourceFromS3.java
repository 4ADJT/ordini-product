package io.ordini.products.infrastructure.batch;

import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class GetResourceFromS3 {
  private final S3Client s3Client;
  private static final String BUCKET_NAME = "ordini";
  private static final String PREFIX = "products";

  public List<Resource> getResourcesFromS3() {

    ListObjectsV2Request request = ListObjectsV2Request.builder()
        .bucket(BUCKET_NAME)
        .prefix(PREFIX)
        .build();

    return s3Client.listObjectsV2(request)
        .contents()
        .stream()
        .map(S3Object::key)
        .map(key -> {
          return new InputStreamResource(
              s3Client.getObject(builder -> builder.bucket(BUCKET_NAME).key(key))
          ) {
            @Override
            public String getFilename() {
              return key;
            }
          };
        })
        .collect(Collectors.toList());
  }
}
