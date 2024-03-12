package com.kernel360.bbs.code;

import com.kernel360.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum BBSErrorCode implements ErrorCode {

    FAILED_GET_BBS_LIST(HttpStatus.NO_CONTENT.value(), "BMC001", "게시판 목록을 찾을 수 없음."),
    FAILED_GET_BBS_VIEW(HttpStatus.NO_CONTENT.value(), "BMC002", "게시글을 찾을 수 없음.");

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
