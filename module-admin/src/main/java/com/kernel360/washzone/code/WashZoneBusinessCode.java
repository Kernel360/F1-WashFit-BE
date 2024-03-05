package com.kernel360.washzone.code;


import com.kernel360.code.BusinessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum WashZoneBusinessCode implements BusinessCode {

    SUCCESS_REQUEST_WASH_ZONE_INFO(HttpStatus.CREATED.value(), "BWZC001", "Wash Zone 정보조회 성공"),
    SUCCESS_SAVED_WASH_ZONE_INFO(HttpStatus.CREATED.value(), "BWZC002", "Wash Zone 정보등록 성공"),
    SUCCESS_SAVED_BULK_WASH_ZONE_INFO(HttpStatus.CREATED.value(), "BWZC003", "Wash Zone 대량정보등록 성공");


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

