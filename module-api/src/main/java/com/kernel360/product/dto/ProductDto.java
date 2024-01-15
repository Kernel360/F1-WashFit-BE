package com.kernel360.product.dto;

import com.kernel360.product.entity.Product;

import java.time.LocalDate;

/**
 * DTO for {@link com.kernel360.product.entity.Product}
 */

public record ProductDto(Long productNo,
                         String productName,
                         String barcode,
                         String description,
                         String declareNo,
                         Boolean isViolation,
                         Integer viewCount,
                         LocalDate createdAt,
                         String createdBy,
                         LocalDate modifiedAt,
                         String modifiedBy
) {

    public static ProductDto of(
            Long productNo,
            String productName,
            String barcode,
            String description,
            String declareNo,
            Boolean isViolation,
            Integer viewCount,
            LocalDate createdAt,
            String createdBy,
            LocalDate modifiedAt,
            String modifiedBy
    ) {
        return new ProductDto(
                productNo,
                productName,
                barcode,
                description,
                declareNo,
                isViolation,
                viewCount,
                createdAt,
                createdBy,
                modifiedAt,
                modifiedBy
        );
    }

    public static ProductDto from(Product entity) {
        return ProductDto.of(
                entity.getProductNo(),
                entity.getProductName(),
                entity.getBarcode(),
                entity.getDescription(),
                entity.getDeclareNo(),
                entity.getIsViolation(),
                entity.getViewCount(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    public static Product toEntity(ProductDto productDto) {
        return Product.of(
                productDto.productName,
                productDto.barcode,
                productDto.description,
                productDto.declareNo,
                productDto.isViolation,
                productDto.viewCount
        );
    }

}
