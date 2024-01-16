package com.kernel360.response;

import com.kernel360.code.BusinessCode;

public record ApiResponse<T>(
        int status,
        String code,
        String message,
        T value) {

    public static <T> ApiResponse<T> of(BusinessCode code, T value) {
        return new ApiResponse<>(
                code.getStatus(),
                code.getCode(),
                code.getMessage(),
                value
        );
    }

    public static <T> ApiResponse<T> of(BusinessCode code) {
        return new ApiResponse<>(
                code.getStatus(),
                code.getCode(),
                code.getMessage(),
                null
        );
    }
}
