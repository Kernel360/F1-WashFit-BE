package com.kernel360.member.code;

import com.kernel360.code.BusinessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum MemberBusinessCode implements BusinessCode {

    SUCCESS_REQUEST_JOIN_MEMBER_CREATED(HttpStatus.CREATED.value(), "BMEC001", "회원가입 성공"),
    SUCCESS_REQUEST_LOGIN_MEMBER(HttpStatus.OK.value(), "BMEC002", "로그인 성공");


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
