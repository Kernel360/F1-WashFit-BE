package com.kernel360.commoncode.vo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommonCodeVO {
    private Long codeNo;

    private String codeName;

    private Integer upperNo;

    private String upperName;

    private Integer sortOrder;

    private Boolean isUsed = false;

    private String description;

    private String subDescription;

    private LocalDate createdAt;

    private String createdBy;

    private LocalDate modifiedAt;

    private String modifiedBy;

    //TODO BUILDER -> RETURN NEW PATTERN
    @Builder
    public CommonCodeVO(Integer codeNo, String codeName, Integer upperNo, String upperName, Integer sortOrder, Boolean isUsed, String description, String subDescription, LocalDate createdAt, String createdBy, LocalDate modifiedAt, String modifiedBy) {

    }
}
