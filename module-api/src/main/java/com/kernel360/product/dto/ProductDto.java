package com.kernel360.product.dto;

import com.kernel360.product.entity.Product;
import com.kernel360.product.entity.SafetyStatus;

import java.time.LocalDate;

/**
 * DTO for {@link com.kernel360.product.entity.Product}
 */
public record ProductDto(
        Long productNo,
        String productName,
        String barcode,
        String imageSource,
        String reportNumber,
        SafetyStatus safetyStatus,
        Integer viewCount,
        String brand,
        String upperItem,
        LocalDate createdAt,
        String createdBy,
        LocalDate modifiedAt,
        String modifiedBy
) {

    public static ProductDto of(
            Long productNo,
            String productName,
            String barcode,
            String imageSource,
            String reportNumber,
            SafetyStatus safetyStatus,
            String brand,
            String upperItem,
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
                imageSource,
                reportNumber,
                safetyStatus,
                viewCount,
                brand,
                upperItem,
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
                entity.getImageSource(),
                entity.getReportNumber(),
                entity.getSafetyStatus(),
                entity.getCompanyName(),
                entity.getUpperItem(),
                entity.getViewCount(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }
}
