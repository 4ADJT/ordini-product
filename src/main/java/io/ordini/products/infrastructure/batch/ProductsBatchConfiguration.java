package io.ordini.products.infrastructure.batch;

import io.awspring.cloud.s3.S3Template;
import io.ordini.products.adapter.gateway.batch.ProductProcessor;
import io.ordini.products.adapter.mapper.ProductMapper;
import io.ordini.products.domain.model.ProductModel;
import io.ordini.products.infrastructure.persistence.jpa.entity.ProductEntity;
import jakarta.persistence.EntityManagerFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Configuration
@AllArgsConstructor
@Slf4j
public class ProductsBatchConfiguration {
  public final ProductMapper productMapper;

  private final S3Client s3Client;
  private final S3Template s3Template;
  private final JobLauncher jobLauncher;

  private static final String BUCKET_NAME = "ordini";
  private static final String PREFIX = "products";

  @Bean
  public Job productsJob(JobRepository jobRepository, Step productsStep) {
    return new JobBuilder("processProducts", jobRepository)
        .incrementer(new RunIdIncrementer())
        .start(productsStep)
        .build();
  }

  @Bean
  public Step productsStep(JobRepository jobRepository,
                           PlatformTransactionManager transactionManager,
                           ItemReader<ProductModel> productsReader,
                           ItemProcessor<ProductModel, ProductEntity> productsProcessor,
                           ItemWriter<ProductEntity> productsWriter) {
    return new StepBuilder("productsStep", jobRepository)
        .<ProductModel, ProductEntity>chunk(3, transactionManager)
        .reader(productsReader)
        .processor(productsProcessor)
        .writer(productsWriter)
        .build();
  }

  @Bean
  @StepScope
  public FlatFileItemReader<ProductModel> productsReader(@Value("#{jobParameters['fileName']}") String fileName) {
    if (fileName == null || fileName.isBlank()) {
      throw new IllegalArgumentException("O parâmetro 'fileName' é obrigatório para o leitor de produtos.");
    }

    Resource s3Resource = s3Template.download(BUCKET_NAME, fileName);

    if (!s3Resource.exists() || !s3Resource.isReadable()) {
      throw new IllegalArgumentException("O recurso do S3 não está acessível ou não existe: " + fileName);
    }

    return new FlatFileItemReaderBuilder<ProductModel>()
        .name("productsReader")
        .resource(s3Resource)
        .linesToSkip(1)
        .delimited()
        .delimiter(";")
        .names("name", "description", "price", "stock", "currency")
        .targetType(ProductModel.class)
        .build();
  }

  @Bean
  public ItemWriter<ProductEntity> productsWriter(EntityManagerFactory entityManagerFactory) {
    return new JpaItemWriterBuilder<ProductEntity>()
        .entityManagerFactory(entityManagerFactory)
        .build();
  }

  @Bean
  @StepScope
  public ItemProcessor<ProductModel, ProductEntity> productsProcessor(
      @Value("#{jobParameters['fileName']}") String fileName) {
    return new ProductProcessor(productMapper) {
      @Override
      public ProductEntity process(ProductModel productModel) throws Exception {
        productModel.setSourceFile(fileName.replace(PREFIX + "/", ""));
        return super.process(productModel);
      }
    };
  }

  public Map<String, Resource> getFileMappingsFromS3() {
    ListObjectsV2Request request = ListObjectsV2Request.builder()
        .bucket(BUCKET_NAME)
        .prefix(PREFIX)
        .build();

    List<S3Object> s3Objects = s3Client.listObjectsV2(request).contents();

    AtomicInteger counter = new AtomicInteger(1);

    return s3Objects.stream()
        .collect(Collectors.toMap(
            obj -> "file" + counter.getAndIncrement(),
            obj -> new InputStreamResource(
                s3Client.getObject(builder -> builder.bucket(BUCKET_NAME).key(obj.key()))
            ) {
              @Override
              public String getFilename() {
                return obj.key();
              }
            }
        ));
  }

  public void runJobsForAllFiles() {
    Map<String, Resource> fileMappings = getFileMappingsFromS3();

    fileMappings.forEach((fileKey, resource) -> {
      try {
        JobParameters jobParameters = new JobParametersBuilder()
            .addString("fileName", Objects.requireNonNull(resource.getFilename()))
            .addString("jobId", UUID.randomUUID().toString())
            .toJobParameters();

        jobLauncher.run(productsJob(null, null), jobParameters);
        log.info("Job executado com sucesso para o arquivo: {}", resource.getFilename());
      } catch (Exception e) {
        log.error("Erro ao executar o job para o arquivo: {}, {}", resource.getFilename(), e.getMessage());
      }
    });
  }
}
