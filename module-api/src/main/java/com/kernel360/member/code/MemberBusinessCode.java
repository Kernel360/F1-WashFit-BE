package com.kernel360.member.code;

import com.kernel360.code.BusinessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum MemberBusinessCode implements BusinessCode {

    SUCCESS_REQUEST_JOIN_MEMBER_CREATED(HttpStatus.CREATED.value(), "BMC001", "회원가입 성공"),
    SUCCESS_REQUEST_LOGIN_MEMBER(HttpStatus.OK.value(), "BMC002", "로그인 성공"),
    SUCCESS_FIND_REQUEST_MEMBER(HttpStatus.OK.value(), "BMC003", "회원 조회 성공"),
    SUCCESS_FIND_CAR_INFO_IN_MEMBER(HttpStatus.OK.value(), "BMC004", "차량정보 조회 성공"),
    SUCCESS_REQUEST_DELETE_MEMBER(HttpStatus.OK.value(), "BMC005", "회원이 탈퇴 되었습니다."),
    SUCCESS_REQUEST_UPDATE_MEMBER(HttpStatus.OK.value(), "BMC006", "회원정보가 변경 되었습니다."),
    SUCCESS_REQUEST_CHANGE_PASSWORD_MEMBER(HttpStatus.OK.value(), "BMC007", "비밀번호가 변경 되었습니다."),
    SUCCESS_VALIDATE_PASSWORD_MEMBER(HttpStatus.OK.value(), "BMC008", "비밀번호가 확인 되었습니다."),
    SUCCESS_REQUEST_UPDATE_WASH_INFO_MEMBER(HttpStatus.OK.value(), "BMC009", "WashInfo 정보가 변경 되었습니다."),
    SUCCESS_REQUEST_UPDATE_CAR_INFO_MEMBER(HttpStatus.OK.value(), "BMC010", "CarInfo 정보가 변경 되었습니다."),
    SUCCESS_REQUEST_FIND_MEMBER_ID(HttpStatus.OK.value(), "BMC011","회원 아이디 찾기 메일이 발송되었습니다."),
    SUCCESS_REQUEST_SEND_RESET_PASSWORD_EMAIL(HttpStatus.OK.value(), "BMC012", "회원 비밀번호 초기화 메일이 발송되었습니다."),
    SUCCESS_REQUEST_RESET_PASSWORD_PAGE(HttpStatus.FOUND.value(), "BMC013", "비밀번호 초기화 토큰이 유효하므로 비밀번호 초기화 페이지로 접근합니다."),
    SUCCESS_REQUEST_RESET_PASSWORD(HttpStatus.OK.value(), "BMC014", "비밀번호가 초기화되었습니다."),
    SUCCESS_REQUEST_LOGIN_MEMBER_KAKAO(HttpStatus.OK.value(), "BMC015", "로그인 성공");

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
