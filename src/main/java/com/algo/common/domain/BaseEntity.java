package com.algo.common.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity implements Serializable {

	@CreatedDate
	@JsonIgnore
	@Column(name = "created_dt", nullable = false, columnDefinition = "TIMESTAMP")
	private LocalDateTime createdDt = LocalDateTime.now();

	@LastModifiedBy
	@JsonIgnore
	@Column(name = "updated_dt", columnDefinition = "TIMESTAMP")
	private LocalDateTime updatedDt;

	@Column(name = "is_delete", nullable = false)
	private boolean isDelete = false;
}
