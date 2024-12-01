package io.ordini.products.adapter.gateway.bucket;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import software.amazon.awssdk.services.s3.S3Client;

import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class GetBucketFileList {
  private final S3Client s3Client;

  private static final String BUCKET_NAME = "ordini";
  private static final String PREFIX = "products";

  public Set<String> getBucketFileList() {
    ListObjectsV2Request request = ListObjectsV2Request.builder()
        .bucket(BUCKET_NAME)
        .prefix(PREFIX)
        .build();

    return s3Client.listObjectsV2(request)
        .contents()
        .stream()
        .map(bucket -> bucket.key().replace("products/", ""))
        .collect(Collectors.toSet());
  }

}
