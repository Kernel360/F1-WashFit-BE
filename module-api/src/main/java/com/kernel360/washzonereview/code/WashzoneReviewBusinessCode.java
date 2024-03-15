package com.kernel360.washzonereview.code;

import com.kernel360.code.BusinessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum WashzoneReviewBusinessCode implements BusinessCode {
    SUCCESS_GET_WASHZONE_REVIEWS(HttpStatus.OK.value(), "BWZRV001", "세차장 리뷰 목록 조회 성공"),
    SUCCESS_GET_WASHZONE_REVIEW(HttpStatus.OK.value(), "BWZRV002", "세차장 리뷰 단건 조회 성공"),
    SUCCESS_CREATE_WASHZONE_REVIEW(HttpStatus.OK.value(), "BWZRV003", "세차장 리뷰 등록 성공"),
    SUCCESS_UPDATE_WASHZONE_REVIEW(HttpStatus.OK.value(), "BWZRV004", "세차장 리뷰 수정 성공"),
    SUCCESS_DELETE_WASHZONE_REVIEW(HttpStatus.OK.value(), "BWZRV005", "세차장 리뷰 삭제 성공");

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
