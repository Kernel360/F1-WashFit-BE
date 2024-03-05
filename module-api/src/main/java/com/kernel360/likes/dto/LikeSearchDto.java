package com.kernel360.likes.dto;

import com.kernel360.main.controller.Sort;

public record LikeSearchDto(
        String token,
        String keyword,
        Sort sortType
) {
    public static LikeSearchDto of(String token, String keyword, Sort sortBy) {
        return new LikeSearchDto(token, keyword, sortBy);
    }
}
