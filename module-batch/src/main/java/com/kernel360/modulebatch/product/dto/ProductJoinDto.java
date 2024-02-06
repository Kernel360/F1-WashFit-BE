package com.kernel360.modulebatch.product.dto;

public record ProductJoinDto(
        String productName,
        String companyName,
        String actionedDate,
        String violatedCn,
        String actionCn,
        String etcInfo
) {

}
