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
// 중복
@EntityListeners(AuditingEntityListener.class)
public class BaseRawEntity {
    // FIXME :: createdAt 자동 생성시, update 로직 진행중에 null or transient 문제가 발생. 임시방편으로 직접 지정하였으나 이후 수정 필요
    @Column(name = "created_at", nullable = false)
    @CreatedDate
    private LocalDate createdAt = LocalDate.now();

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
