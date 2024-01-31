package com.kernel360.commoncode.dto;

import com.kernel360.entity.commoncode.CommonCode;

import java.time.LocalDate;

/**
 * DTO for {@link CommonCode}
 */
public record CommonCodeDto(Long codeNo,
                            String codeName,
                            Integer upperNo,
                            String upperName,
                            Integer sortOrder,

                            Boolean isUsed,
                            String description,
                            LocalDate createdAt,
                            String createdBy,

                            LocalDate modifiedAt,
                            String modifiedBy) {
    /**
     * @param codeNo, codeName
     * **/

    public static CommonCodeDto of(
            Long codeNo,
            String codeName,
            Integer upperNo,
            String upperName,
            Integer sortOrder,
            Boolean isUsed,
            String description,
            LocalDate createdAt,
            String createdBy,
            LocalDate modifiedAt,
            String modifiedBy
    ) {
        return new CommonCodeDto(
                codeNo,
                codeName,
                upperNo,
                upperName,
                sortOrder,
                isUsed,
                description,
                createdAt,
                createdBy,
                modifiedAt,
                modifiedBy
        );
    }

    /** entity -> dto **/
    public static CommonCodeDto from(CommonCode entity) {
        return CommonCodeDto.of(
                entity.getCodeNo(),
                entity.getCodeName(),
                entity.getUpperNo(),
                entity.getUpperName(),
                entity.getSortOrder(),
                entity.getIsUsed(),
                entity.getDescription(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }
}