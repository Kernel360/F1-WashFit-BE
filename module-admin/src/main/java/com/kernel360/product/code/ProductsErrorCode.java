package com.kernel360.product.code;

import com.kernel360.code.ErrorCode;
import org.springframework.http.HttpStatus;

public enum ProductsErrorCode implements ErrorCode {
    NOT_FOUND_RECOMMEND_PRODUCT(HttpStatus.BAD_REQUEST.value(), "ERPMB001", "추천제품 데이터가 존재하지 않습니다."),
    NOT_FOUND_PRODUCT(HttpStatus.BAD_REQUEST.value(), "EPMB001", "제품 데이터가 존재하지 않습니다.");

    private final int status;
    private final String code;
    private final String message;
    ProductsErrorCode(int status, String code, String message) {
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
