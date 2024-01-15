package com.kernel360.commoncode.code;

import com.kernel360.code.ErrorCode;
import org.springframework.http.HttpStatus;

public enum CommonCodeErrorCode implements ErrorCode {
    INVALID_COMMON_CODE_NAME(HttpStatus.BAD_REQUEST.value(), "EC001", "존재하지 않는 공통코드");

    private final int status;
    private final String code;
    private final String message;

    CommonCodeErrorCode(int status, String code, String message) {
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
