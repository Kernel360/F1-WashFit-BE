package com.kernel360.review.dto;

public record ReviewSearchDto(
        Long productNo,
        Long memberNo,
        String sortBy
) {
    public static ReviewSearchDto of(Long productNo, Long memberNo, String sortBy) {
        return new ReviewSearchDto(productNo, memberNo, sortBy);
    }

    public static ReviewSearchDto byProductNo(Long productNo, String sortBy) {
        return ReviewSearchDto.of(productNo, null, sortBy);
    }

    // TODO: 추후 mypage 리뷰 관리에서 사용 예정
    public static ReviewSearchDto byMemberNo(Long memberNo, String sortBy) {
        return ReviewSearchDto.of(null, memberNo, sortBy);
    }
}
