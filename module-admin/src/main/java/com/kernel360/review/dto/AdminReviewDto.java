package com.kernel360.review.dto;

import com.kernel360.product.dto.ProductDto;
import com.kernel360.review.entity.Review;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AdminReviewDto(Long reviewNo,
                             ProductDto productDto,
                             BigDecimal starRating,
                             String title,
                             String contents,
                             LocalDateTime createdAt,
                             String createdBy,
                             LocalDateTime modifiedAt,
                             String modifiedBy) {

    public static AdminReviewDto of(
            Long reviewNo,
            ProductDto productDto,
            BigDecimal starRating,
            String title,
            String contents,
            LocalDateTime createdAt,
            String createdBy,
            LocalDateTime modifiedAt,
            String modifiedBy
    ) {
        return new AdminReviewDto(
                reviewNo,
                productDto,
                starRating,
                title,
                contents,
                createdAt,
                createdBy,
                modifiedAt,
                modifiedBy
        );
    }

    public static AdminReviewDto from(Review entity) {
        return AdminReviewDto.of(
                entity.getReviewNo(),
                ProductDto.from(entity.getProduct()),
                entity.getStarRating(),
                entity.getTitle(),
                entity.getContents(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

}