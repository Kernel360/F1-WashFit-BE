package com.kernel360.code.common;

import com.kernel360.code.ErrorCode;
import org.springframework.http.HttpStatus;

public enum CommonErrorCode implements ErrorCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "E001", "Server Error"),
    FAIL_FILE_UPLOAD(HttpStatus.INTERNAL_SERVER_ERROR.value(), "E002", "파일 업로드 실패"),
    INVALID_FILE_EXTENSION(HttpStatus.BAD_REQUEST.value(), "E003", "유효하지 않은 파일 확장자"),
    NOT_FOUND_RESOURCE(HttpStatus.NOT_FOUND.value(), "E004", "요청한 자원이 존재하지 않음"),
    INVALID_REQUEST_HEADERS(HttpStatus.BAD_REQUEST.value(),  "E005", "요청한 헤더가 존재하지 않음"),
    INVALID_ARGUMENT(HttpStatus.BAD_REQUEST.value(),  "E006", "요청 파라미터가 없거나 비어있거나, 요청 파라미터의 이름이 메서드 인수의 이름과 일치하지 않습니다"),
    INVALID_HTTP_REQUEST_METHOD(HttpStatus.BAD_REQUEST.value(),  "E007", "요청 URL 에서 지원하지 않는 HTTP Method 입니다."),
    INVALID_REQUEST_PARAMETER(HttpStatus.BAD_REQUEST.value(),  "E008", "요청한 파라미터가 존재하지 않음"),
    INVALID_WORD_PARAMETER(HttpStatus.BAD_REQUEST.value(),  "E009", "비속어를 포함할 수 없습니다.");

    private final int status;
    private final String code;
    private final String message;

    CommonErrorCode(int status, String code, String message) {
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
