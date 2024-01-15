package com.kernel360.commoncode.code;

import com.kernel360.code.BusinessCode;

public enum CommonCodeBusinessCode implements BusinessCode {
    GET_COMMON_CODE_SUCCESS(200, "BC001", "공통코드 조회 성공");

    private int status;
    private final String code;
    private final String message;

    CommonCodeBusinessCode(int status, String code, String message) {
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
