package com.kernel360.commoncode.dto;

import com.kernel360.commoncode.entity.CommonCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.kernel360.commoncode.entity.CommonCode}
 */
public record CommonCodeDto(Long codeNo,
                            String codeName,
                            Long upperNo,
                            String upperName,
                            Integer sortOrder,

                            Boolean isUsed,
                            String description,
                            LocalDateTime createdAt,
                            String createdBy,

                            LocalDateTime modifiedAt,
                            String modifiedBy) {
    /**
     * @param codeNo, codeName
     * **/

    public static CommonCodeDto of(
            Long codeNo,
            String codeName,
            Long upperNo,
            String upperName,
            Integer sortOrder,
            Boolean isUsed,
            String description,
            LocalDateTime createdAt,
            String createdBy,
            LocalDateTime modifiedAt,
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