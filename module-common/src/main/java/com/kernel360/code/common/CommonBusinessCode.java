package com.kernel360.code.common;

import com.kernel360.code.ErrorCode;
import org.springframework.http.HttpStatus;

public enum CommonBusinessCode implements ErrorCode {
    SAVED(HttpStatus.OK.value(), "B001", "저장이 왼료되었습니다.");

    private final int status;
    private final String code;
    private final String message;

    CommonBusinessCode(int status, String code, String message) {
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
