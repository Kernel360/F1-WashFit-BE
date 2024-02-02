package com.kernel360.product.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kernel360.brand.entity.Brand;
import com.kernel360.product.entity.Product;
import java.time.LocalDate;

/**
 * DTO for {@link com.kernel360.product.entity.Product}
 */
public record ProductDetailDto(

        String productName,
        String companyName,
        String productType,
        String upperItem,
        String manufactureType,
        String manufactureMethod,
        String weight,
        String reportNumber,


        String mainSubstance,


        String brand,
        Double grade,
        Long reviewCnt,

        Long productNo,
        Integer viewCount,
        LocalDate createdAt,
        String createdBy,
        LocalDate modifiedAt,
        String modifiedBy
) {

    public static ProductDetailDto of(
            // parameters for all fields except substance details
            String productName,
            String companyName,
            String productType,
            String upperItem,
            String manufactureType,
            String manufactureMethod,
            String weight,
            String reportNumber,
            String mainSubstance,
            String brand,
            Double grade,
            Long reviewCnt,
            Long productNo,
            Integer viewCount,
            LocalDate createdAt,
            String createdBy,
            LocalDate modifiedAt,
            String modifiedBy
    ) {
        return new ProductDetailDto(
                productName,
                companyName,
                productType,
                upperItem,
                manufactureType,
                manufactureMethod,
                weight,
                reportNumber,
                mainSubstance,
                brand,
                grade,
                reviewCnt,
                productNo,
                viewCount,
                createdAt,
                createdBy,
                modifiedAt,
                modifiedBy
        );
    }

    public static ProductDetailDto from(Product entity) {

        String concatenatedSubstances = String.join(" ",
                entity.getMainSubstance(),
                entity.getAllergicSubstance(),
                entity.getOtherSubstance(),
                entity.getPreservative(),
                entity.getSurfactant(),
                entity.getFluorescentWhitening()
        );

        return ProductDetailDto.of(
                entity.getProductName(),
                entity.getCompanyName(),
                entity.getProductType(),
                entity.getUpperItem(),
                entity.getManufactureType(),
                entity.getManufactureMethod(),
                entity.getWeight(),
                entity.getReportNumber(),
                concatenatedSubstances,
                entity.getCompanyName(),
                3.5,
                10L,
//FIXME:: 하드코딩값 변경할것  entity.getGrade(),
//                entity.getReviewCnt(),
                entity.getProductNo(),
                entity.getViewCount(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }
}