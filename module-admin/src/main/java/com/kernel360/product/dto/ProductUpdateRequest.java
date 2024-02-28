package com.kernel360.product.dto;


import lombok.Builder;

import java.time.LocalDate;

public record ProductUpdateRequest(
        Long productNo,
        String productName,
        String reportNumber,
        String productType,
        String companyName,
        String manufactureNation,
        String safetyStatus,
        LocalDate issuedDate,
        String barcode,
        String imageSource,
        Integer viewCount,
        String safetyInspectionStandard,
        String upperItem,
        String item,
        String propose,
        String weight,
        String usage,
        String usagePrecaution,
        String firstAid,
        String mainSubstance,
        String allergicSubstance,
        String otherSubstance,
        String preservative,
        String surfactant,
        String fluorescentWhitening,
        String manufactureType,
        String manufactureMethod,
        String violationInfo
) {

}

