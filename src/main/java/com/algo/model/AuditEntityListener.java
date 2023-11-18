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
    entity.setCreatedDt(LocalDateTime.now());
    entity.setUpdatedDt(LocalDateTime.now());
  }

  @PreUpdate
  public void setUpdatedAt(BaseEntity entity) {
    entity.setUpdatedDt(LocalDateTime.now());
  }
}
