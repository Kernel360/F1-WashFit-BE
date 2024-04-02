package com.kernel360.bbs.dto;

import com.kernel360.bbs.entity.BBS;
import com.kernel360.file.entity.File;
import com.kernel360.member.dto.MemberDto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link com.kernel360.bbs.entity.BBS}
 */
public record BBSDto(

        Long bbsNo,
        Long upperNo,
        String type,
        String title,
        String contents,
        Boolean isVisible,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy,
        Long viewCount,
        MemberDto memberDto,
        List<File> files
    ) {

    public static BBSDto of(
            Long bbsNo,
            Long upperNo,
            String type,
            String title,
            String contents,
            Boolean isVisible,
            LocalDateTime createdAt,
            String createdBy,
            LocalDateTime modifiedAt,
            String modifiedBy,
            Long viewCount,
            MemberDto memberDto,
            List<File> files
    ){
        return new BBSDto(
            bbsNo,
            upperNo,
            type,
            title,
            contents,
            isVisible,
            createdAt,
            createdBy,
            modifiedAt,
            modifiedBy,
            viewCount,
            memberDto,
                files
        );
    }

    public static BBSDto from(BBS entity, List<File> byReferenceTypeAndReferenceNo){
        return new BBSDto(
                entity.getBbsNo(),
                entity.getUpperNo(),
                entity.getType(),
                entity.getTitle(),
                entity.getContents(),
                entity.getIsVisible(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy(),
                entity.getViewCount(),
                MemberDto.from(entity.getMember()),
                byReferenceTypeAndReferenceNo
        );
    }

}