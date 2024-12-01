package io.ordini.products.application.files;

import io.ordini.products.adapter.gateway.bucket.GetBucketFileContent;
import io.ordini.products.adapter.gateway.bucket.GetBucketFileList;
import io.ordini.products.domain.exception.BucketException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class FileService {

  private GetBucketFileList getBucketFileList;
  private GetBucketFileContent getBucketFileContent;

  public Map<String, String> processFilesAndReturnContents() {

    Set<String> fileList = getBucketFileList.getBucketFileList();
    Map<String, String> fileContents = new HashMap<>();

    fileList.forEach(fileName -> {
      try {

        String fileContent = getBucketFileContent.getFileContent(fileName);

        fileContents.put(fileName, fileContent);
      } catch (RuntimeException e) {
        log.error("Erro ao processar o arquivo '" + fileName + "': " + e.getMessage());
        throw new BucketException("Erro ao processar o arquivo '" + fileName + "': " + e.getMessage(),
            HttpStatus.BAD_REQUEST);
      }
    });

    return fileContents;
  }

}
