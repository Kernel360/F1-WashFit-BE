package com.kernel360.review.dto;

import com.kernel360.member.dto.MemberDto;
import com.kernel360.product.dto.ProductDetailDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * DTO for {@link ReviewSearchResult}
 */
@Getter
@NoArgsConstructor
public class ReviewSearchResult {
    // review
    Long reviewNo;
    BigDecimal starRating;
    String title;
    String contents;
    LocalDate createdAt;
    String createdBy;
    LocalDate modifiedAt;
    String modifiedBy;

    // member
    Long memberNo;
    String id;
    int age;
    int gender;

    // product
    Long productNo;
    String productName;
    String companyName;
    String imageSource;
    String upperItem;
    String item;

    // file
    String fileUrls;

    public static ReviewResponseDto toDto(ReviewSearchResult response) {
        List<String> fileUrls = new ArrayList<>();

        if (Objects.nonNull(response.getFileUrls())) {
            fileUrls = Arrays.stream(response.getFileUrls().split("\\|")).toList();
        }

        return ReviewResponseDto.of(
                response.getReviewNo(),
                ProductDetailDto.of(
                        response.getProductNo(),
                        response.getProductName(),
                        response.getImageSource(),
                        response.getCompanyName(),
                        response.getUpperItem(),
                        response.getItem()
                ),
                MemberDto.of(
                        response.getMemberNo(),
                        response.getId(),
                        response.getAge(),
                        response.getGender()
                ),
                response.getStarRating(),
                response.getTitle(),
                response.getContents(),
                response.getCreatedAt(),
                response.getCreatedBy(),
                response.getModifiedAt(),
                response.getModifiedBy(),
                fileUrls
        );
    }
}