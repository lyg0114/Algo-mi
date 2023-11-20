package com.algo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity implements Serializable {

  @CreatedDate
  @JsonIgnore
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_dt", nullable = false, updatable = false)
  private LocalDateTime createdDt;

  @LastModifiedBy
  @JsonIgnore
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "updated_dt", nullable = false)
  private LocalDateTime updatedDt;

  @Column(name = "is_delete", nullable = false)
  private boolean isDelete = false;
}
