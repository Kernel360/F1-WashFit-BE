package com.kernel360.code.jwt;

import com.kernel360.code.ErrorCode;
import org.springframework.http.HttpStatus;

public enum JwtErrorCode implements ErrorCode {
    FAILED_MALFORMED_JWT(HttpStatus.BAD_REQUEST.value(), "EJC001", "유효하지 않은 JWT 문자열 형식입니다."),
    FAILED_SIGNATURE_JWT(HttpStatus.BAD_REQUEST.value(), "EJC001", "JWT 시그니처가 서버에서 계산한 시그니처와 일치하지 않습니다.");
    private final int status;
    private final String code;
    private final String message;

    JwtErrorCode(int status, String code, String message) {
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
