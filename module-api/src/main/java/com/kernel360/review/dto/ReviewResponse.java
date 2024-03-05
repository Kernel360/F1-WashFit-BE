package com.kernel360.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * DTO for {@link ReviewResponse}
 */
@Getter
@NoArgsConstructor
public class ReviewResponse {
    Long reviewNo;
    Long productNo;
    Long memberNo;
    BigDecimal starRating;
    String title;
    String contents;
    LocalDate createdAt;
    String createdBy;
    LocalDate modifiedAt;
    String modifiedBy;
    String fileUrls;

    public static ReviewDto toDto(ReviewResponse response) {
        List<String> fileUrls = new ArrayList<>();

        if (Objects.nonNull(response.getFileUrls())) {
            fileUrls = Arrays.stream(response.getFileUrls().split("\\|")).toList();
        }

        return ReviewDto.of(
                response.getReviewNo(),
                response.getProductNo(),
                response.getMemberNo(),
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