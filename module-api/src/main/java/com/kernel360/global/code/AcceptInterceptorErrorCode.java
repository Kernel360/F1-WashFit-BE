package com.kernel360.global.code;

import com.kernel360.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum AcceptInterceptorErrorCode implements ErrorCode {

    DOSE_NOT_EXIST_REQUEST_TOKEN(HttpStatus.UNAUTHORIZED.value(), "AIEC001", "요청자의 토큰이 존재하지 않음."),
    FAILED_VALID_REQUEST_TOKEN(HttpStatus.UNAUTHORIZED.value(), "AIEC002", "요청자의 토큰이 유효하지 않음."),
    FAILED_VALID_REQUEST_TOKEN_PERIOD(HttpStatus.BAD_REQUEST.value(), "AIEC003", "토큰 유효시간 조건이 충족되지 않음."),
    FAILED_VALID_REQUEST_TOKEN_HASH(HttpStatus.BAD_REQUEST.value(), "AIEC004", "로그인시 발급한 토큰의 해시값과 불일치함.");

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
