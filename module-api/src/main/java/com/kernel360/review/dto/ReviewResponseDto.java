package com.kernel360.review.dto;

import com.kernel360.member.dto.MemberDto;
import com.kernel360.product.dto.ProductDetailDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link com.kernel360.review.dto.ReviewResponseDto}
 */
public record ReviewResponseDto(Long reviewNo,
                                ProductDetailDto productDto,
                                MemberDto memberDto,
                                BigDecimal starRating,
                                String title,
                                String contents,
                                LocalDate createdAt,
                                String createdBy,
                                LocalDate modifiedAt,
                                String modifiedBy,
                                List<String> files) {

    public static ReviewResponseDto of(
            Long reviewNo,
            ProductDetailDto productDto,
            MemberDto memberDto,
            BigDecimal starRating,
            String title,
            String contents,
            LocalDate createdAt,
            String createdBy,
            LocalDate modifiedAt,
            String modifiedBy,
            List<String> files
    ) {
        return new ReviewResponseDto(
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
}