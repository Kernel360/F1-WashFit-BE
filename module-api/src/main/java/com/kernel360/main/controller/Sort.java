package com.kernel360.main.controller;

import com.kernel360.product.dto.ProductDto;
import com.kernel360.product.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


//
public enum Sort {

    VIEW_COUNT_PRODUCT_ORDER("viewCnt-order") {
        @Override
        Page<ProductDto> sort(ProductService productService, Pageable pageable) {

            return productService.getProductListOrderByViewCount(pageable);
        }
    },
    VIOLATION_PRODUCT_LIST("violation-products") {
        @Override
        Page<ProductDto> sort(ProductService productService, Pageable pageable) {

            return productService.getViolationProducts(pageable);
        }
    },
    RECOMMENDATION_PRODUCT_ORDER("recommend-order") {
        @Override
        Page<ProductDto> sort(ProductService productService, Pageable pageable) {
//Fixme :: 향후 Like Table 구현후, 정렬메소드 변경이 필요합니다.(임시로 violationProduct 리턴으로 구현)

            return productService.getViolationProducts(pageable);
        }
    },
    RECENT_PRODUCT_ORDER("recent-order") {
        @Override
        Page<ProductDto> sort(ProductService productService, Pageable pageable) {

            return productService.getRecentProducts(pageable);
        }
    };

    private String orderType;

    Sort(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderType() {
        return orderType;
    }

    abstract Page<ProductDto> sort(ProductService productService, Pageable pageable);
}
