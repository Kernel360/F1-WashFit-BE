package com.kernel360.likes.code;

import com.kernel360.code.BusinessCode;
import org.springframework.http.HttpStatus;

public enum LikeBusinessCode implements BusinessCode {
    LIKE_ON_SUCCESS(HttpStatus.OK.value(), "BLO001", "좋아요 표시 성공"),
    LIKE_OFF_SUCCESS(HttpStatus.OK.value(), "BLO002", "좋아요 취소 성공"),
    LIKE_LIST_SEARCH_SUCCESS(HttpStatus.OK.value(), "BLO003", "즐겨찾기 목록조회 성공");

    private final int status;
    private final String code;
    private final String message;

    LikeBusinessCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

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
