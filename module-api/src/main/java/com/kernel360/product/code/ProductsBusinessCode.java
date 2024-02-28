package com.kernel360.product.code;
import com.kernel360.code.BusinessCode;
import org.springframework.http.HttpStatus;

public enum ProductsBusinessCode implements BusinessCode {
    GET_RECOMMEND_PRODUCT_DATA_SUCCESS(HttpStatus.OK.value(), "PMB001", "추천제품정보 조회 성공"),
    GET_PRODUCT_DATA_SUCCESS(HttpStatus.OK.value(), "PMB002", "제품정보 조회 성공");

    private final int status;
    private final String code;
    private final String message;

    ProductsBusinessCode(int status, String code, String message) {
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
