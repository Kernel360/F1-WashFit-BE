package com.kernel360.washzonereview.dto;

import com.kernel360.member.dto.MemberDto;
import com.kernel360.washzone.dto.WashZoneDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link WashzoneReviewResponseDto}
 */
public record WashzoneReviewResponseDto(Long washzoneReviewNo,
                                        WashZoneDto washzone,
                                        MemberDto member,
                                        BigDecimal starRating,
                                        String title,
                                        String contents,
                                        LocalDateTime createdAt,
                                        String createdBy,
                                        LocalDateTime modifiedAt,
                                        String modifiedBy,
                                        List<String> files) {

    public static WashzoneReviewResponseDto of(
            Long washzoneReviewNo,
            WashZoneDto washzoneDto,
            MemberDto memberDto,
            BigDecimal starRating,
            String title,
            String contents,
            LocalDateTime createdAt,
            String createdBy,
            LocalDateTime modifiedAt,
            String modifiedBy,
            List<String> files
    ) {
        return new WashzoneReviewResponseDto(
                washzoneReviewNo,
                washzoneDto,
                memberDto,
                starRating,
                title,
                contents,
                createdAt,
                createdBy,
                modifiedAt,
                modifiedBy,
                files
        );
    }
}