package com.kernel360.commoncode.command;

import com.kernel360.entity.commoncode.CommonCode;

import java.time.LocalDate;

public record CommonCodeCommand(
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
    public static CommonCodeCommand of(
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
        return new CommonCodeCommand(
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

    /**
     * entity -> command
     **/
    public static CommonCodeCommand from(CommonCode entity) {
        return CommonCodeCommand.of(
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
