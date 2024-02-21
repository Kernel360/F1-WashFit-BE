package com.kernel360.likes.code;

import com.kernel360.code.ErrorCode;
import org.springframework.http.HttpStatus;

public enum LikeErrorCode implements ErrorCode {
    NO_EXIST_LIKE_INFO(HttpStatus.BAD_REQUEST.value(), "ELO001", "좋아요 정보가 존재하지 않습니다.");

    private final int status;
    private final String code;
    private final String message;
    LikeErrorCode(int status, String code, String message) {
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
