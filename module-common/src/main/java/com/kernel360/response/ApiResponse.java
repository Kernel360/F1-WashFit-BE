package com.kernel360.response;

import com.kernel360.code.BusinessCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

    public static ResponseEntity<ApiResponse> toResponseEntity(BusinessCode code) {
        return new ResponseEntity<>(
                ApiResponse.of(code),
                HttpStatus.valueOf(code.getStatus())
        );
    }
    public static <T> ResponseEntity<ApiResponse> toResponseEntity(BusinessCode code, T value) {
        return new ResponseEntity<>(
                ApiResponse.of(code, value),
                HttpStatus.valueOf(code.getStatus())
        );
    }
}
