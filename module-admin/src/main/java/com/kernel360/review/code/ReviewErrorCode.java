package com.kernel360.review.code;

import com.kernel360.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum ReviewErrorCode implements ErrorCode {
    INVALID_STAR_RATING_VALUE(HttpStatus.BAD_REQUEST.value(), "ERV001", "유효하지 않은 별점입니다."),
    INVALID_REVIEW_WRITE_REQUEST(HttpStatus.BAD_REQUEST.value(), "ERV002", "리뷰가 중복되거나 유효하지 않습니다."),
    INVALID_REVIEW_DELETE_REQUEST(HttpStatus.BAD_REQUEST.value(), "ERV003", "삭제하려는 리뷰가 없거나 삭제할 수 없습니다.");

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
