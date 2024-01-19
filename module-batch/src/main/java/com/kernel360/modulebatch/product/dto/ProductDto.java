package com.kernel360.modulebatch.product.dto;

import com.kernel360.brand.entity.Brand;
import com.kernel360.product.entity.Product;

public record ProductDto(String productName, String barcode, String description,
                         Boolean isViolation, Integer viewCount,
                         String companyName, String reportNumber,
                         String productType, String safetyInspectionStandard,
                         String upperItem, String item,
                         String propose, String weight, String usage, String usagePrecaution, String firstAid,
                         String mainSubstance, String allergicSubstance, String otherSubstance, String preservative,
                         String Surfactant, String fluorescentWhitening, String manufactureType,
                         String manufactureMethod, String manufactureCountry,
                         Brand brand) {

    public static ProductDto of(String productName, String barcode, String description,
                                Boolean isViolation, Integer viewCount,
                                String companyName, String reportNumber,
                                String productType, String safetyInspectionStandard,
                                String upperItem, String item,
                                String propose, String weight, String usage, String usagePrecaution, String firstAid,
                                String mainSubstance, String allergicSubstance, String otherSubstance,
                                String preservative,
                                String Surfactant, String fluorescentWhitening, String manufactureType,
                                String manufactureMethod, String manufactureCountry,
                                Brand brand) {
        return new ProductDto(
                productName, barcode, description,
                isViolation, viewCount,
                companyName, reportNumber,
                productType, safetyInspectionStandard,
                upperItem, item,
                propose, weight, usage, usagePrecaution, firstAid,
                mainSubstance, allergicSubstance, otherSubstance, preservative,
                Surfactant, fluorescentWhitening, manufactureType,
                manufactureMethod, manufactureCountry,
                brand
        );
    }

    public static Product toEntity(ProductDto productDto) {
        return Product.of(productDto.productName, productDto.barcode(), productDto.description(),
                productDto.reportNumber(), productDto.isViolation, productDto.viewCount, productDto.companyName(),
                productDto.productType(), productDto.safetyInspectionStandard, productDto.upperItem(),
                productDto.item(),
                productDto.propose(), productDto.weight(), productDto.usage(), productDto.usagePrecaution(),
                productDto.firstAid(), productDto.mainSubstance(), productDto.allergicSubstance(),
                productDto.otherSubstance(), productDto.preservative(), productDto.Surfactant(),
                productDto.fluorescentWhitening(), productDto.manufactureType(), productDto.manufactureMethod(),
                productDto.manufactureCountry(), productDto.brand());
    }
}
