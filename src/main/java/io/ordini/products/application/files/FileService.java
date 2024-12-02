package io.ordini.products.application.files;

import io.ordini.products.adapter.gateway.bucket.GetBucketFileContent;
import io.ordini.products.adapter.gateway.bucket.GetBucketFileList;
import io.ordini.products.domain.exception.BucketException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class FileService {

  private GetBucketFileList getBucketFileList;
  private GetBucketFileContent getBucketFileContent;

  public Map<String, Map<String, Object>> getFilesAndContent() {
    Set<String> fileList = getBucketFileList.getBucketFileList();

    return fileList.stream().collect(Collectors.toMap(
        fileName -> fileName,
        fileName -> {
          try {
            String fileContent = getBucketFileContent.getFileContent(fileName);
            String[] lines = fileContent.split("\n");

            if (lines.length == 0) {
              log.warn("O arquivo '{}' está vazio ou possui formato inválido.", fileName);
              return Map.of("header", "", "data", "");
            }

            String header = lines[0];
            String data = String.join("\n", Arrays.copyOfRange(lines, 1, lines.length));

            return Map.of("header", header, "data", data);

          } catch (RuntimeException e) {
            log.error("Erro ao processar o arquivo '{}': {}", fileName, e.getMessage());
            throw new BucketException("Erro ao processar o arquivo '" + fileName + "': " + e.getMessage(),
                HttpStatus.BAD_REQUEST);
          }
        }
    ));
  }
}
