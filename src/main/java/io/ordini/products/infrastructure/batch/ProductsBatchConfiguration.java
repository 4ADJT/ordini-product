package io.ordini.products.infrastructure.batch;

import io.awspring.cloud.s3.S3Template;
import io.ordini.products.adapter.gateway.batch.ProductProcessor;
import io.ordini.products.adapter.mapper.ProductMapper;
import io.ordini.products.domain.model.ProductModel;
import io.ordini.products.infrastructure.persistence.jpa.entity.ProductEntity;
import jakarta.persistence.EntityManagerFactory;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;

import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;

import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@AllArgsConstructor
public class ProductsBatchConfiguration {
  public final ProductMapper productMapper;

  private final S3Template s3Template;

  private static final String BUCKET_NAME = "ordini";
  private static final String PREFIX = "products";

  @Bean
  public Job productsJob(JobRepository jobRepository,
                         Step productsStep
  ) {
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
                           ItemWriter<ProductEntity> productsWriter
  ) {
    return new StepBuilder("productsStep", jobRepository)
        .<ProductModel, ProductEntity>chunk(3, transactionManager)
        .reader(productsReader)
        .processor(productsProcessor)
        .writer(productsWriter)
        .build();
  }

  @Bean
  public ItemReader<ProductModel> productsReader() {

    Resource s3Resource = s3Template.download("ordini", "products/List1.csv");
    if (!s3Resource.exists() || !s3Resource.isReadable()) {
      throw new IllegalArgumentException("O recurso do S3 não está acessível ou não existe: /products/List1.csv");
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
  public ItemProcessor<ProductModel, ProductEntity> productsProcessor() {
    return new ProductProcessor(productMapper);
  }

}
