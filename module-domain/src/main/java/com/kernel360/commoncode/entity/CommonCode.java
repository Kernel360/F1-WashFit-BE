package com.kernel360.commoncode.entity;

import com.kernel360.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "common_code")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommonCode extends BaseEntity {
    @Id
    @Column(name = "code_no", nullable = false)
    private Long codeNo;

    @Column(name = "code_name", nullable = false, length = Integer.MAX_VALUE)
    private String codeName;

    @Column(name = "upper_no")
    private Long upperNo;

    @Column(name = "upper_name", length = Integer.MAX_VALUE)
    private String upperName;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    @Column(name = "is_used", nullable = false)
    private Boolean isUsed = false;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Builder
    public CommonCode (Integer codeNo, String codeName, Integer upperNo, String upperName, Integer sortOrder, Boolean isUsed, String description){

    }
}