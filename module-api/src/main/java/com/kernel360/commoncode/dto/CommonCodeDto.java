package com.kernel360.commoncode.dto;

import com.kernel360.commoncode.command.CommonCodeCommand;

import java.time.LocalDate;

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

    /** command -> dto **/
    public static CommonCodeDto from(CommonCodeCommand command) {
        return CommonCodeDto.of(
                command.codeNo(),
                command.codeName(),
                command.upperNo(),
                command.upperName(),
                command.sortOrder(),
                command.isUsed(),
                command.description(),
                command.createdAt(),
                command.createdBy(),
                command.modifiedAt(),
                command.modifiedBy()
        );
    }
}