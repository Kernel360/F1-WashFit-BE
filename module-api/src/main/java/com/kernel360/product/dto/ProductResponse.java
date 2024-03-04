package com.kernel360.product.dto;

import com.kernel360.product.entity.Product;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductResponse {
    private Long productNo;
    private String productName;
    private String companyName;
    private String item;
    private boolean isLiked;
    private Long likeCnt;

    @QueryProjection
    public ProductResponse(Long productNo, String productName, String companyName, String item, boolean isLiked, Long likeCnt) {
        this.productNo = productNo;
        this.productName = productName;
        this.companyName = companyName;
        this.item = item;
        this.isLiked = isLiked;
        this.likeCnt = likeCnt;
    }
    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getProductNo(),
                product.getProductName(),
                product.getCompanyName(),
                product.getItem(),
                false,
                0L
        );
    }
}