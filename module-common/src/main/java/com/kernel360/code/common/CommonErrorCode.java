package com.kernel360.code.common;

import com.kernel360.code.ErrorCode;

public enum CommonErrorCode implements ErrorCode {
    INTERNAL_SERVER_ERROR(500, "E001", "Server Error");

    private int status;
    private final String code;
    private final String message;

    CommonErrorCode(int status, String code, String message) {
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
