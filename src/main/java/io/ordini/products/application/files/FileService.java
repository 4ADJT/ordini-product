package io.ordini.products.application.files;

import io.ordini.products.adapter.gateway.bucket.GetBucketFileContent;
import io.ordini.products.adapter.gateway.bucket.GetBucketFileList;
import io.ordini.products.domain.exception.BucketException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class FileService {

  private GetBucketFileList getBucketFileList;
  private GetBucketFileContent getBucketFileContent;

  /**
   * Processa os arquivos, separa o cabeçalho e os dados, e retorna o resultado em um mapa.
   */
  public Map<String, Map<String, Object>> processFilesAndContent() {
    Set<String> fileList = getBucketFileList.getBucketFileList();

    return fileList.stream().collect(Collectors.toMap(
        fileName -> fileName,
        fileName -> {
          try {
            String fileContent = getBucketFileContent.getFileContent(fileName);
            String[] lines = fileContent.split("\n");

            if (lines.length == 0) {
              log.warn("O arquivo '" + fileName + "' está vazio ou possui formato inválido.");
              return Map.of("header", "", "data", "");
            }

            String header = lines[0]; // Primeira linha como cabeçalho
            String data = String.join("\n", java.util.Arrays.copyOfRange(lines, 1, lines.length)); // Restante como dados

            return Map.of("header", header, "data", data);

          } catch (RuntimeException e) {
            log.error("Erro ao processar o arquivo '" + fileName + "': " + e.getMessage());
            throw new BucketException("Erro ao processar o arquivo '" + fileName + "': " + e.getMessage(),
                HttpStatus.BAD_REQUEST);
          }
        }
    ));
  }
}
