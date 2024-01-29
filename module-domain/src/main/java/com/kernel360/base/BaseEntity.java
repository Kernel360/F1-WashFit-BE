package com.kernel360.base;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    private static final int MAX_STRING_LENGTH = 255;

    @Column(name = "created_at", nullable = false)
    @CreatedDate
    private LocalDate createdAt;

    @Column(name = "created_by", nullable = false, length = MAX_STRING_LENGTH, updatable = false)
    @CreatedBy
    private String createdBy;

    @Column(name = "modified_at")
    @LastModifiedDate
    private LocalDate modifiedAt;

    @Column(name = "modified_by", length = MAX_STRING_LENGTH )
    @LastModifiedBy
    private String modifiedBy;
}
