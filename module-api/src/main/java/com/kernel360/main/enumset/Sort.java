package com.kernel360.main.enumset;

import com.kernel360.product.dto.ProductResponse;
import com.kernel360.product.dto.ProductSearchDto;
import com.kernel360.product.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public enum Sort {

    VIEW_COUNT_PRODUCT_ORDER("viewCnt-order") {
        @Override
        public Page<ProductResponse> sort(ProductService productService, Pageable pageable) {

            return productService.getProductListOrderByViewCount(pageable);
        }

        @Override
        public Page<ProductResponse> withKeywordSort(ProductService productService, String keyword, Pageable pageable) {

            return productService.getProductWithKeywordAndOrderByViewCount(keyword, pageable);
        }

    },

    VIOLATION_PRODUCT_LIST("violation-products") {
        @Override
        public Page<ProductResponse> sort(ProductService productService, Pageable pageable) {

            return productService.getViolationProducts(pageable);
        }

        @Override
        public Page<ProductResponse> withKeywordSort(ProductService productService, String keyword, Pageable pageable) {

            return productService.getProductWithKeywordAndViolationProducts(keyword, pageable);
        }
    },

    RECOMMENDATION_PRODUCT_ORDER("recommend-order") {
        @Override
        public Page<ProductResponse> sort(ProductService productService, Pageable pageable) {

            return productService.getFavoriteProducts(pageable);
        }

        @Override
        public Page<ProductResponse> withKeywordSort(ProductService productService, String keyword, Pageable pageable) {

            return productService.getProductWithKeywordAndOrderByRecommend(keyword, pageable);
        }
    },

    RECENT_PRODUCT_ORDER("recent-order") {
        @Override
        public Page<ProductResponse> sort(ProductService productService, Pageable pageable) {

            return productService.getRecentProducts(pageable);
        }

        @Override
        public Page<ProductResponse> withKeywordSort(ProductService productService, String keyword, Pageable pageable) {

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

    public abstract Page<ProductResponse> sort(ProductService productService, Pageable pageable);

    public abstract Page<ProductResponse> withKeywordSort(ProductService productService, String keyword, Pageable pageable);

    public Page<ProductResponse> getProductWithCondition(ProductService productService, ProductSearchDto productSearchDto,
                                                             Pageable pageable) {

        return productService.searchWithCondition(productSearchDto, pageable);
    }
}

