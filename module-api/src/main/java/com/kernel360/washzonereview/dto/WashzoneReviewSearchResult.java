package com.kernel360.washzonereview.dto;

import com.kernel360.member.dto.MemberDto;
import com.kernel360.washzone.dto.WashZoneDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * DTO for {@link WashzoneReviewSearchResult}
 */
@Getter
@NoArgsConstructor
public class WashzoneReviewSearchResult {
    // washzone review
    Long washzoneReviewNo;
    BigDecimal starRating;
    String title;
    String contents;
    LocalDateTime createdAt;
    String createdBy;
    LocalDateTime modifiedAt;
    String modifiedBy;

    // member
    Long memberNo;
    String id;
    int age;
    int gender;

    // washzone
    Long washzoneNo;
    String name;
    String address;
    String type;

    // file
    String fileUrls;

    public static WashzoneReviewResponseDto toDto(WashzoneReviewSearchResult response) {
        List<String> fileUrls = new ArrayList<>();

        if (Objects.nonNull(response.getFileUrls())) {
            fileUrls = Arrays.stream(response.getFileUrls().split("\\|")).toList();
        }

        return WashzoneReviewResponseDto.of(
                response.getWashzoneReviewNo(),
                WashZoneDto.of(
                        response.getWashzoneNo(),
                        response.getName(),
                        response.getAddress(),
                        response.getType()
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