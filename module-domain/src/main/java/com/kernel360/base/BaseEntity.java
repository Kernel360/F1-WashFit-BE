package com.kernel360.base;

import jakarta.persistence.Column;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;

@Getter
public class BaseEntity {
    @Column(name = "created_at", nullable = false)
    @CreatedDate
    private LocalDate createdAt;

    @Column(name = "created_by", nullable = false, length = Integer.MAX_VALUE)
    @CreatedBy
    private String createdBy;

    @Column(name = "modified_at")
    @LastModifiedDate
    private LocalDate modifiedAt;

    @Column(name = "modified_by", length = Integer.MAX_VALUE)
    @LastModifiedBy
    private String modifiedBy;
}
