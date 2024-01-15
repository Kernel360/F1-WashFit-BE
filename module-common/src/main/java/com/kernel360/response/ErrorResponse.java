package com.kernel360.response;

import com.kernel360.code.ErrorCode;

public record ErrorResponse(
        int status,
        String code,
        String message) {

    public static ErrorResponse of(ErrorCode code) {
        return new ErrorResponse(
                code.getStatus(),
                code.getCode(),
                code.getMessage()
        );
    }
}
