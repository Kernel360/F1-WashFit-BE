package com.kernel360.washzonereview.code;

import com.kernel360.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum WashzoneReviewErrorCode implements ErrorCode {
    INVALID_STAR_RATING_VALUE(HttpStatus.BAD_REQUEST.value(), "EWZRV001", "유효하지 않은 별점입니다."),
    INVALID_WASHZONE_REVIEW_WRITE_REQUEST(HttpStatus.BAD_REQUEST.value(), "EWZRV002", "세차장 리뷰가 중복되거나 유효하지 않습니다."),
    NOT_FOUND_WASHZONE_REVIEW(HttpStatus.BAD_REQUEST.value(), "EWZRV003", "세차장 리뷰가 존재하지 않습니다.");

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
