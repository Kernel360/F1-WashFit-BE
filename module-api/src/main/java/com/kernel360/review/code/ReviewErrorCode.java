package com.kernel360.review.code;

import com.kernel360.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum ReviewErrorCode implements ErrorCode {
    INVALID_STAR_RATING_VALUE(HttpStatus.BAD_REQUEST.value(), "ERV001", "유효하지 않은 별점입니다."),
    DUPLICATE_REVIEW_EXISTS(HttpStatus.BAD_REQUEST.value(), "ERV002", "중복된 리뷰가 존재합니다."),
    NOT_FOUND_REVIEW(HttpStatus.BAD_REQUEST.value(), "ERV003", "리뷰가 존재하지 않습니다."),
    NOT_FOUND_PRODUCT_FOR_REVIEW_CREATION(HttpStatus.BAD_REQUEST.value(), "ERV004", "제품이 존재하지 않습니다."),
    NOT_FOUND_MEMBER_FOR_REVIEW_CREATION(HttpStatus.BAD_REQUEST.value(), "ERV005", "회원이 존재하지 않습니다."),
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
