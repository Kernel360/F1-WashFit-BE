package com.kernel360.review.dto;

import com.kernel360.review.entity.Review;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link com.kernel360.review.dto.ReviewDto}
 */
public record ReviewDto(Long reviewNo,
                        Long productNo,
                        Long memberNo,
                        BigDecimal starRating,
                        String title,
                        String contents,
                        LocalDate createdAt,
                        String createdBy,
                        LocalDate modifiedAt,
                        String modifiedBy,
                        List<String> files) {

    public static ReviewDto of(
            Long reviewNo,
            Long productNo,
            Long memberNo,
            BigDecimal starRating,
            String title,
            String contents,
            LocalDate createdAt,
            String createdBy,
            LocalDate modifiedAt,
            String modifiedBy,
            List<String> files
    ) {
        return new ReviewDto(
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

    public static ReviewDto from(Review entity, List<String> files) {
        return ReviewDto.of(
                entity.getReviewNo(),
                entity.getProduct().getProductNo(),
                entity.getMember().getMemberNo(),
                entity.getStarRating(),
                entity.getTitle(),
                entity.getContents(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy(),
                files
        );
    }

//    public Review toEntity() {
//        return Review.of(
//                reviewNo,
//                productDto.toEntity(),
//                memberDto.toEntity(),
//                starRating,
//                title,
//                contents
//        );
//    }
}