package com.kernel360.review.code;

import com.kernel360.code.BusinessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
@RequiredArgsConstructor
public enum ReviewBusinessCode implements BusinessCode {
    SUCCESS_GET_REVIEWS(HttpStatus.OK.value(), "BRV001", "리뷰 목록 조회 성공"),
    SUCCESS_GET_REVIEW(HttpStatus.OK.value(), "BRV002", "리뷰 단건 조회 성공"),
    SUCCESS_CREATE_REVIEW(HttpStatus.OK.value(), "BRV003", "리뷰 등록 성공"),
    SUCCESS_UPDATE_REVIEW(HttpStatus.OK.value(), "BRV004", "리뷰 수정 성공"),
    SUCCESS_DELETE_REVIEW(HttpStatus.OK.value(), "BRV005", "리뷰 삭제 성공");

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
