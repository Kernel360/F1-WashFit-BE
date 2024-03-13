package com.kernel360.washzonereview.code;

import com.kernel360.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum WashzoneReviewErrorCode implements ErrorCode {
    INVALID_STAR_RATING_VALUE(HttpStatus.BAD_REQUEST.value(), "EWZRV001", "유효하지 않은 별점입니다."),
    DUPLICATE_WASHZONE_REVIEW_EXISTS(HttpStatus.BAD_REQUEST.value(), "EWZRV002", "중복된 세차장 리뷰가 존재합니다."),
    NOT_FOUND_WASHZONE_REVIEW(HttpStatus.BAD_REQUEST.value(), "EWZRV003", "세차장 리뷰가 존재하지 않습니다."),
    NOT_FOUND_WASHZONE_FOR_WASHZONE_REVIEW_CREATION(HttpStatus.BAD_REQUEST.value(), "EWZRV004", "세차장이 존재하지 않습니다."),
    NOT_FOUND_MEMBER_FOR_WASHZONE_REVIEW_CREATION(HttpStatus.BAD_REQUEST.value(), "EWZRV005", "회원이 존재하지 않습니다."),
    MISMATCHED_MEMBER_NO_AND_ID(HttpStatus.BAD_REQUEST.value(), "ERV006", "회원 번호와 아이디가 일치하지 않습니다.");

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
