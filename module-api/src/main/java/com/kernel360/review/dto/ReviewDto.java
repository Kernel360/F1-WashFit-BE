package com.kernel360.review.dto;

import com.kernel360.member.dto.MemberDto;
import com.kernel360.product.dto.ProductDto;
import com.kernel360.review.entity.Review;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for {@link com.kernel360.review.dto.ReviewDto}
 */
public record ReviewDto(Long reviewNo,
                        ProductDto productDto,
                        MemberDto memberDto,
                        BigDecimal starRating,
                        String title,
                        String contents,
                        LocalDate createdAt,
                        String createdBy,
                        LocalDate modifiedAt,
                        String modifiedBy) {

    public static ReviewDto of(
            Long reviewNo,
            ProductDto productDto,
            MemberDto memberDto,
            BigDecimal starRating,
            String title,
            String contents,
            LocalDate createdAt,
            String createdBy,
            LocalDate modifiedAt,
            String modifiedBy
    ) {
        return new ReviewDto(
                reviewNo,
                productDto,
                memberDto,
                starRating,
                title,
                contents,
                createdAt,
                createdBy,
                modifiedAt,
                modifiedBy
        );
    }

    public static ReviewDto from(Review entity) {
        return ReviewDto.of(
                entity.getReviewNo(),
                ProductDto.from(entity.getProduct()),
                MemberDto.from(entity.getMember()),
                entity.getStarRating(),
                entity.getTitle(),
                entity.getContents(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    public Review toEntity() {
        return Review.of(
                reviewNo,
                productDto.toEntity(),
                memberDto.toEntity(),
                starRating,
                title,
                contents
        );
    }
}