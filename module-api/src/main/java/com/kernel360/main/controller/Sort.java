package com.kernel360.main.controller;

import com.kernel360.product.dto.ProductDto;
import com.kernel360.product.service.ProductService;

import java.util.List;

public enum Sort {

    VIEW_COUNT_PRODUCT_ORDER("viewCnt_order") {
        @Override
        List<ProductDto> sort(ProductService productService) {

            return productService.getProductListOrderByViewCount();
        }
    },
    SAFE_PRODUCT_LIST("violation_product") {
        @Override
        List<ProductDto> sort(ProductService productService) {

            return productService.getViolationProducts();
        }
    },
    RECOMMENDATION_PRODUCT_ORDER("recommend_order") {
        @Override
        List<ProductDto> sort(ProductService productService) {
//Fixme :: 향후 Like Table 구현후, 정렬메소드 변경이 필요합니다.(임시로 violationProduct 리턴으로 구현)

            return productService.getViolationProducts();
        }
    },
    RECENT_PRODUCT_ORDER("recent_order") {
        @Override
        List<ProductDto> sort(ProductService productService) {

            return productService.getRecentProducts();
        }
    };

    private String orderType;

    Sort(String orderType) {
        this.orderType = orderType;
    }

    abstract List<ProductDto> sort(ProductService productService);
}
