package com.kernel360.member.code;

import com.kernel360.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum MemberErrorCode implements ErrorCode {

    FAILED_NOT_MAPPING_ORDINAL_TO_NAME(HttpStatus.INTERNAL_SERVER_ERROR.value(), "EMC001", "DB값과 ENUM의 ORDINAL이 불일치하여 NAME을 찾을 수 없음."),
    FAILED_NOT_MAPPING_ORDINAL_TO_VALUE(HttpStatus.INTERNAL_SERVER_ERROR.value(), "EMC002", "DB값과 ENUM의 VALUE가 불일치하여 VALUE을 찾을 수 없음."),
    FAILED_NOT_MAPPING_ENUM_VALUE_OF(HttpStatus.INTERNAL_SERVER_ERROR.value(), "EMC003", "PARAMETER 값과 ENUM의 NAME이 불일치함."),
    FAILED_GENERATE_JOIN_MEMBER_INFO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "EMC004", "회원가입에 필요한 정보 생성 실패"),
    FAILED_GENERATE_LOGIN_REQUEST_INFO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "EMC005", "정보 불일치로 인한 로그인 정보 생성 실패"),
    FAILED_REQUEST_LOGIN(HttpStatus.BAD_REQUEST.value(), "EMC006", "정보 불일치로 인한 로그인 실패"),
    FAILED_FIND_MEMBER_INFO(HttpStatus.BAD_REQUEST.value(), "EMC007", "요청 회원정보가 존재하지 않습니다."),
    EXPIRED_TOKEN(HttpStatus.NOT_FOUND.value(), "EMC008", "유효하지 않은 토큰입니다"),
    FAILED_REQUEST_LOGIN_FOR_KAKAO(HttpStatus.BAD_REQUEST.value(), "EMC009", "카카오 로그인 정보를 찾을 수 없습니다."),
    FAILED_FIND_MEMBER_CAR_INFO(HttpStatus.BAD_REQUEST.value(), "EMC010", "요청 회원의 차량정보가 존재하지 않습니다."),
    FAILED_FIND_MEMBER_WASH_INFO(HttpStatus.BAD_REQUEST.value(), "EMC011", "요청 회원의 세차정보가 존재하지 않습니다."),
    WRONG_PASSWORD_REQUEST(HttpStatus.BAD_REQUEST.value(), "EMC012", "잘못된 비밀번호 입니다."),
    FAILED_DUPLICATED_JOIN_MEMBER_INFO(HttpStatus.BAD_REQUEST.value(), "EMC013", "동일한 아이디로 가입한 회원이 이미 존재합니다.");

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
