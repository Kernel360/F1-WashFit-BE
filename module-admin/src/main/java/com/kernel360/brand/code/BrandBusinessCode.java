package com.kernel360.brand.code;

import com.kernel360.code.BusinessCode;
import org.springframework.http.HttpStatus;

public enum BrandBusinessCode implements BusinessCode {

    SUCCESS_FOUND_BRAND_LIST(HttpStatus.OK.value(),"BMB001","브랜드 목록 조회 성공"),
    SUCCESS_FOUND_EXACT_BRAND(HttpStatus.OK.value(),"BMB002","브랜드 상세 조회 성공"),
    SUCCESS_CREATED_BRAND(HttpStatus.CREATED.value(), "BMB003","브랜드 추가 성공" ),
    SUCCESS_DELETED_BRAND(HttpStatus.OK.value(), "BMB004","브랜드 삭제 성공"),
    SUCCESS_UPDATED_BRAND(HttpStatus.OK.value(), "BMB005","브랜드 업데이트 성공" );


    private final int status;
    private final String code;
    private final String message;

    BrandBusinessCode(int status, String code, String message) {
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
