package com.kernel360.member.code;

import com.kernel360.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum MemberErrorCode implements ErrorCode {

    FAILED_NOT_MAPPING_ORDINAL_TO_NAME(HttpStatus.INTERNAL_SERVER_ERROR.value(), "EMC001", "DB값과 ENUM의 ORDINAL이 불일치하여 NAME을 찾을 수 없음."),
    FAILED_NOT_MAPPING_ORDINAL_TO_VALUE(HttpStatus.INTERNAL_SERVER_ERROR.value(), "EMC002", "DB값과 ENUM의 VALUE가 불일치하여 VALUE을 찾을 수 없음."),
    FAILED_NOT_MAPPING_ENUM_VALUEOF(HttpStatus.INTERNAL_SERVER_ERROR.value(), "EMC003", "PARAMETER 값과 ENUM의 NAME이 불일치함."),
    FAILED_GENERATE_JOIN_MEMBER_INFO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "EMC004", "회원가입에 필요한 정보 생성 실패"),
    FAILED_GENERATE_LOGIN_REQUEST_INFO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "EMC005", "정보 불일치로 인한 로그인 정보 생성 실패"),
    FAILED_REQUEST_LOGIN(HttpStatus.BAD_REQUEST.value(), "EMC006", "정보 불일치로 인한 로그인 실패");



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
