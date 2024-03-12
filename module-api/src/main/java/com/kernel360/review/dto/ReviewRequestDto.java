package com.kernel360.review.dto;

import com.kernel360.member.entity.Member;
import com.kernel360.product.entity.Product;
import com.kernel360.review.entity.Review;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link ReviewRequestDto}
 */
public record ReviewRequestDto(Long reviewNo,
                               Long productNo,
                               Long memberNo,
                               BigDecimal starRating,
                               String title,
                               String contents,
                               LocalDateTime createdAt,
                               String createdBy,
                               LocalDateTime modifiedAt,
                               String modifiedBy,
                               List<String> files) {

    public static ReviewRequestDto of(
            Long reviewNo,
            Long productNo,
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
        return new ReviewRequestDto(
                reviewNo,
                productNo,
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

    public Review toEntity() {
        return Review.of(
                reviewNo,
                Product.of(productNo),
                Member.of(memberNo),
                starRating,
                title,
                contents,
                true
        );
    }

    public Review toEntityForUpdate() {
        return Review.of(
                reviewNo,
                starRating,
                title,
                contents,
                true
        );
    }
}