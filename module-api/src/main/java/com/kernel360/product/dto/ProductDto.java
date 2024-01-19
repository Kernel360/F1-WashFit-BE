package com.kernel360.product.dto;

import com.kernel360.product.entity.Product;
import com.kernel360.product.entity.SafetyStatus;
import java.time.LocalDate;

/**
 * DTO for {@link com.kernel360.product.entity.Product}
 */
public record ProductDto(Long productNo,
                         String productName,
                         String barcode,
                         String description,
                         String declareNo,
                         SafetyStatus safetyStatus,
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
            SafetyStatus safetyStatus,
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
                safetyStatus,
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
                entity.getImage(),
                entity.getReportNumber(),
                entity.getSafetyStatus(),
                entity.getViewCount(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }
}
