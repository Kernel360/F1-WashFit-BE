package com.kernel360.auth.code;

import com.kernel360.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {

    FAILED_GENERATED_JWT(HttpStatus.INTERNAL_SERVER_ERROR.value(), "EAC001", "재발급충족미달");

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
