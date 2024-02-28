package com.kernel360.modulebatch.violatedproduct.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.kernel360.ecolife.entity.ViolatedProduct;
import com.kernel360.ecolife.entity.ViolatedProductId;

@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "row")
public record ViolatedProductDto(
        @JacksonXmlProperty(localName = "prdtarm")
        String productArm,

        @JacksonXmlProperty(localName = "action_de")
        String actionedDate,

        @JacksonXmlProperty(localName = "prdt_nm")
        String productName,

        @JacksonXmlProperty(localName = "mnfctur_nm")
        String companyName,

        @JacksonXmlProperty(localName = "prdtarm_cd")
        String productArmCode,

        @JacksonXmlProperty(localName = "prdtarm_cd_nm")
        String productArmCodeName,

        @JacksonXmlProperty(localName = "origin_instt")
        String originInstitute,

        @JacksonXmlProperty(localName = "prdt_mstr_no")
        String productMasterNo,

        @JacksonXmlProperty(localName = "modl_nm")
        String modelName
) {

    public static ViolatedProduct toEntity(ViolatedProductDto dto) {
        return ViolatedProduct.of(new ViolatedProductId(dto.productMasterNo, dto.productArmCode),
                dto.productName, dto.modelName, dto.productArm, dto.productArmCodeName, dto.companyName,
                dto.originInstitute, dto.actionedDate);
    }

    public static ViolatedProductDto of(String productArm, String actionedDate, String productName, String companyName,
                                        String productArmCode, String productArmCodeName, String originInstitute,
                                        String productMasterNo, String modelName) {

        return new ViolatedProductDto(productArm, actionedDate, productName, companyName, productArmCode,
                productArmCodeName, originInstitute, productMasterNo, modelName);
    }
}
