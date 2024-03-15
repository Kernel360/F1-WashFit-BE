package com.kernel360.washzonereview.dto;

import com.kernel360.code.common.ValidationMessage;
import com.kernel360.global.annotation.BadWordFilter;
import com.kernel360.member.entity.Member;
import com.kernel360.washzone.entity.WashZone;
import com.kernel360.washzonereview.entity.WashzoneReview;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link WashzoneReviewRequestDto}
 */
public record WashzoneReviewRequestDto(Long washzoneReviewNo,
                                       Long washzoneNo,
                                       Long memberNo,
                                       BigDecimal starRating,
                                       @BadWordFilter(message = ValidationMessage.INVALID_WORD_PARAMETER) String title,
                                       @BadWordFilter(message = ValidationMessage.INVALID_WORD_PARAMETER) String contents,
                                       LocalDateTime createdAt,
                                       String createdBy,
                                       LocalDateTime modifiedAt,
                                       String modifiedBy,
                                       List<String> files) {

    public static WashzoneReviewRequestDto of(
            Long washzoneReviewNo,
            Long washzoneNo,
            Long memberNo,
            BigDecimal starRating,
            String title,
            String contents,
            LocalDateTime createdAt,
            String createdBy,
            LocalDateTime modifiedAt,
            String modifiedBy,
            List<String> files
    ) {
        return new WashzoneReviewRequestDto(
                washzoneReviewNo,
                washzoneNo,
                memberNo,
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

    public WashzoneReview toEntity() {
        return WashzoneReview.of(
                washzoneReviewNo,
                WashZone.of(washzoneNo),
                Member.of(memberNo),
                starRating,
                title,
                contents,
                true
        );
    }

    public WashzoneReview toEntityForUpdate() {
        return WashzoneReview.of(
                washzoneReviewNo,
                starRating,
                title,
                contents,
                true
        );
    }
}