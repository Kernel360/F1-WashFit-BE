package com.kernel360.product.dto;

import com.kernel360.main.controller.Sort;

public record ProductSearchDto(
        String token,
        String keyword,
        Sort sortType

) {
    public static ProductSearchDto of(String token, String keyword, Sort sortBy) {
        return new ProductSearchDto(token, keyword, sortBy);
    }
}
