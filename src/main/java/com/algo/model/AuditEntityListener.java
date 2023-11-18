package com.algo.model;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;

/**
 * @author : iyeong-gyo
 * @package : com.algo.model
 * @since : 18.11.23
 */
class AuditEntityListener {

  @PrePersist
  public void setCreatedAt(BaseEntity entity) {
    entity.setCreatedAt(LocalDateTime.now());
    entity.setUpdatedAt(LocalDateTime.now());
  }

  @PreUpdate
  public void setUpdatedAt(BaseEntity entity) {
    entity.setUpdatedAt(LocalDateTime.now());
  }
}
