package com.kernel360.main.code;

import com.kernel360.code.BusinessCode;
import org.springframework.http.HttpStatus;

public enum BannerBusinessCode implements BusinessCode {
    GET_BANNER_DATA_SUCCESS(HttpStatus.OK.value(), "BMB001", "배너정보 조회 성공");

    private final int status;
    private final String code;
    private final String message;

    BannerBusinessCode(int status, String code, String message) {
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
