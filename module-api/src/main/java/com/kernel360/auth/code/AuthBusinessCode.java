package com.kernel360.auth.code;

import com.kernel360.code.BusinessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum AuthBusinessCode implements BusinessCode {

    SUCCESS_REQUEST_REGENERATED_JWT(HttpStatus.CREATED.value(), "BAC001", "JWT 토큰 재발급 성공");

    private final int status;
    private final String code;
    private final String message;

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
