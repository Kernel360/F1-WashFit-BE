package com.kernel360.code.common;

import com.kernel360.code.ErrorCode;
import org.springframework.http.HttpStatus;

public enum CommonErrorCode implements ErrorCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "E001", "Server Error"),
    FAIL_FILE_UPLOAD(HttpStatus.INTERNAL_SERVER_ERROR.value(), "E002", "파일 업로드 실패"),
    INVALID_FILE_EXTENSION(HttpStatus.BAD_REQUEST.value(), "E003", "유효하지 않은 파일 확장자"),
    NOT_FOUND_RESOURCE(HttpStatus.NOT_FOUND.value(), "E004", "요청한 자원이 존재하지 않음");

    private final int status;
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
