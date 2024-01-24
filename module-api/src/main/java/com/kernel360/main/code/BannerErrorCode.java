package com.kernel360.main.code;

import com.kernel360.code.ErrorCode;
import org.springframework.http.HttpStatus;

public enum BannerErrorCode implements ErrorCode {
    INVALID_BANNER_CODE_NAME(HttpStatus.BAD_REQUEST.value(), "EMB001", "배너 데이터가 존재하지 않습니다.");

    private final int status;
    private final String code;
    private final String message;
    BannerErrorCode(int status, String code, String message) {
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
