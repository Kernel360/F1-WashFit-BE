package com.kernel360.base;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDate;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseRawEntity {

    @Column(name = "created_at", nullable = false)
    @CreatedDate
    private LocalDate createdAt;

    @Column(name = "created_by", nullable = false)
    @CreatedBy
    private String createdBy = "admin";

    @Column(name = "modified_at")
    @LastModifiedDate
    private LocalDate modifiedAt;

    @Column(name = "modified_by")
    @LastModifiedBy
    private String modifiedBy;
}
