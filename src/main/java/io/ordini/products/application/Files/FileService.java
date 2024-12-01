package io.ordini.products.application.Files;

import io.ordini.products.adapter.gateway.bucket.GetBucketFileList;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@AllArgsConstructor
public class FileService {

  private GetBucketFileList getBucketFileList;

  public Map<String, String> processFilesAndReturnContents() {
    // Obtém a lista de arquivos do bucket
    Set<String> fileList = getBucketFileList.getBucketFileList();
    Map<String, String> fileContents = new HashMap<>();

    fileList.forEach(fileName -> {
      try {
        // Captura o conteúdo do arquivo
        String fileContent = getBucketFileList.getFileContent(fileName);
        // Adiciona ao mapa
        fileContents.put(fileName, fileContent);
      } catch (RuntimeException e) {
        // Exibe erros, mas continua processando os outros arquivos
        System.err.println("Erro ao processar o arquivo '" + fileName + "': " + e.getMessage());
      }
    });

    return fileContents;
  }

}
