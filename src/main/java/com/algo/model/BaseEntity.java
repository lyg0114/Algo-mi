package com.algo.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditEntityListener.class)
public class BaseEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_dt", nullable = false, updatable = false)
  private LocalDateTime createdDt;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "updated_dt", nullable = false)
  private LocalDateTime updatedDt;

  @Column(name = "is_delete", nullable = false)
  private boolean isDelete = false;

  public boolean isNew() {
    return this.id == null;
  }
}
