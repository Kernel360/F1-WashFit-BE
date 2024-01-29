package com.kernel360.main.code;

import com.kernel360.code.ErrorCode;
import org.springframework.http.HttpStatus;

public enum ConverterErrorCode implements ErrorCode {

    NOT_FOUND_CONVERTER(HttpStatus.BAD_REQUEST.value(), "CMB001", "적절한 조회타입이 존재하지 않습니다.");

    private final int status;
    private final String code;
    private final String message;

    ConverterErrorCode(int status, String code, String message) {
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
