package com.kernel360.commoncode.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "common_code")
public class CommonCode {
    @Id
    @Column(name = "code_no", nullable = false)
    private Integer codeNo;

    @Column(name = "code_name", nullable = false, length = Integer.MAX_VALUE)
    private String codeName;

    @Column(name = "upper_no")
    private Integer upperNo;

    @Column(name = "upper_name", length = Integer.MAX_VALUE)
    private String upperName;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    @Column(name = "is_used", nullable = false)
    private Boolean isUsed = false;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Column(name = "created_by", nullable = false, length = Integer.MAX_VALUE)
    private String createdBy;

    @Column(name = "modified_at")
    private LocalDate modifiedAt;

    @Column(name = "modified_by", length = Integer.MAX_VALUE)
    private String modifiedBy;

    public CommonCode(int codeNo, String codeName, int upperNo, String upperName, int sortOrder, boolean isUsed, String description, String createdAt, String createdBy) {
    }

    public CommonCode() {

    }
}