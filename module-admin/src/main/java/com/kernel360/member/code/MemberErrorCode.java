package com.kernel360.member.code;

import com.kernel360.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum MemberErrorCode implements ErrorCode {

    FAILED_NOT_MAPPING_ORDINAL_TO_NAME(HttpStatus.INTERNAL_SERVER_ERROR.value(), "EMC001", "");


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
