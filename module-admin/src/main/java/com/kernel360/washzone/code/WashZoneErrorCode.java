package com.kernel360.washzone.code;


import com.kernel360.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@RequiredArgsConstructor
public enum WashZoneErrorCode implements ErrorCode {

    FAILED_NOT_MAPPING_ORDINAL_TO_NAME(HttpStatus.INTERNAL_SERVER_ERROR.value(), "EWZC001", "요청 데이터가 존재하지 않습니다."),
    DUPLICATED_WAHSZONE_INFO(HttpStatus.BAD_REQUEST.value(), "EWZC002", "중복된 데이터 입니다.");

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

