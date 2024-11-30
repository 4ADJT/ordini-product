package io.ordini.products.infrastructure.persistence.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "processed_files")
public class ProcessedFileEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "file_name", nullable = false, unique = true, length = 255)
  private String fileName;

  @Column(name = "processed_at", nullable = false, updatable = false)
  private LocalDateTime processedAt;

  @PrePersist
  protected void onCreate() {
    processedAt = LocalDateTime.now();
  }
}
