package com.kernel360.brand.code;

import com.kernel360.code.ErrorCode;
import org.springframework.http.HttpStatus;

public enum BrandErrorCode implements ErrorCode {
    FAILED_CANNOT_FOUND_ANY_BRAND(HttpStatus.NOT_FOUND.value(), "EBC001", "브랜드 목록이 비어있음"),
    FAILED_ALREADY_EXISTS_BRAND(HttpStatus.BAD_REQUEST.value(), "EBC002", "생성하려는 브랜드가 이미 존재함"),
    FAILED_CANNOT_FOUND_EXACT_BRAND(HttpStatus.NOT_FOUND.value(), "EBC003", "변경하려는 브랜드가 존재하지 않음");
    private final int status;
    private final String code;
    private final String message;

    BrandErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
