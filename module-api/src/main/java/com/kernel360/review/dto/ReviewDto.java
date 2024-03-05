package com.kernel360.review.dto;

import com.kernel360.file.entity.File;
import com.kernel360.member.dto.MemberDto;
import com.kernel360.product.dto.ProductDto;
import com.kernel360.review.entity.Review;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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
                        String modifiedBy,
                        List<File> files) {

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
            String modifiedBy,
            List<File> files
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
                modifiedBy,
                files
        );
    }

    public static ReviewDto from(Review entity, List<File> files) {
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
                entity.getModifiedBy(),
                files
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