package com.kernel360.washzonereview.dto;

public record WashzoneReviewSearchDto(
        Long washzoneNo,
        Long memberNo,
        String sortBy
) {
    public static WashzoneReviewSearchDto of(Long washzoneNo, Long memberNo, String sortBy) {
        return new WashzoneReviewSearchDto(washzoneNo, memberNo, sortBy);
    }

    public static WashzoneReviewSearchDto byWashzoneNo(Long washzoneNo, String sortBy) {
        return WashzoneReviewSearchDto.of(washzoneNo, null, sortBy);
    }

    public static WashzoneReviewSearchDto byMemberNo(Long memberNo, String sortBy) {
        return WashzoneReviewSearchDto.of(null, memberNo, sortBy);
    }
}
