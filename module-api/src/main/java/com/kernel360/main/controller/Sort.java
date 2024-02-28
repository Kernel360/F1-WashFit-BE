package com.kernel360.main.controller;

import com.kernel360.product.dto.ProductDto;
import com.kernel360.product.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public enum Sort {

    VIEW_COUNT_PRODUCT_ORDER("viewCnt-order") {
        @Override
        Page<ProductDto> sort(ProductService productService, Pageable pageable) {

            return productService.getProductListOrderByViewCount(pageable);
        }

        @Override
        public Page<ProductDto> withKeywordSort(ProductService productService, String keyword, Pageable pageable) {

            return productService.getProductWithKeywordAndOrderByViewCount(keyword, pageable);
        }


    },
    VIOLATION_PRODUCT_LIST("violation-products") {
        @Override
        Page<ProductDto> sort(ProductService productService, Pageable pageable) {

            return productService.getViolationProducts(pageable);
        }

        @Override
        public Page<ProductDto> withKeywordSort(ProductService productService, String keyword, Pageable pageable) {

            return productService.getProductWithKeywordAndViolationProducts(keyword, pageable);
        }
    },
    RECOMMENDATION_PRODUCT_ORDER("recommend-order") {
        @Override
        Page<ProductDto> sort(ProductService productService, Pageable pageable) {

            return productService.getFavoriteProducts(pageable);
        }

        @Override
        public Page<ProductDto> withKeywordSort(ProductService productService, String keyword, Pageable pageable) {

            return productService.getProductWithKeywordAndOrderByRecommend(keyword, pageable);
        }
    },
    RECENT_PRODUCT_ORDER("recent-order") {
        @Override
        Page<ProductDto> sort(ProductService productService, Pageable pageable) {

            return productService.getRecentProducts(pageable);
        }

        @Override
        public Page<ProductDto> withKeywordSort(ProductService productService, String keyword, Pageable pageable) {

            return productService.getProductWithKeywordAndRecentOrder(keyword, pageable);
        }
    };

    private final String orderType;

    Sort(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderType() {
        return orderType;
    }

    abstract Page<ProductDto> sort(ProductService productService, Pageable pageable);
    public abstract Page<ProductDto> withKeywordSort(ProductService productService, String keyword, Pageable pageable);
}
