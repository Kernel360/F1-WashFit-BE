package com.kernel360.member.code;

import com.kernel360.code.BusinessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum MemberBusinessCode implements BusinessCode {

    SUCCESS_REQUEST_ALL_MEMBER_LIST(HttpStatus.OK.value(), "BMC001", "전체회원목록 조회 성공");

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
