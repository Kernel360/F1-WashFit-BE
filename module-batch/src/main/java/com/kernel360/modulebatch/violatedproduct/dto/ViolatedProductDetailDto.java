package com.kernel360.modulebatch.violatedproduct.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.kernel360.ecolife.entity.ViolatedProduct;
import com.kernel360.ecolife.entity.ViolatedProductId;

@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "row")
public record ViolatedProductDetailDto(
        @JacksonXmlProperty(localName = "prdt_mstr_no") String productMasterNo,
        @JacksonXmlProperty(localName = "prdt_nm") String productName,
        @JacksonXmlProperty(localName = "modl_nm") String modelName,
        @JacksonXmlProperty(localName = "prdtarm") String productArm,
        @JacksonXmlProperty(localName = "prdtarm_cd") String productArmCode,
        @JacksonXmlProperty(localName = "prdtarm_cd_nm") String productArmCodeName,
        @JacksonXmlProperty(localName = "mnfctur_nm") String companyName,
        @JacksonXmlProperty(localName = "mnfctur_addr") String companyAddress,
        @JacksonXmlProperty(localName = "violt_cn") String violatedCn,
        @JacksonXmlProperty(localName = "origin_instt") String originInstitute,
        @JacksonXmlProperty(localName = "prdtn_mnfctur") String productManufactureCountry,
        @JacksonXmlProperty(localName = "act_org") String actOrganization,
        @JacksonXmlProperty(localName = "action_de") String actionDate,
        @JacksonXmlProperty(localName = "prdt_photo_url") String productPhotoUrl,
        @JacksonXmlProperty(localName = "file_download_url") String fileDownloadUrl,
        @JacksonXmlProperty(localName = "action_cn") String actionCn,
        @JacksonXmlProperty(localName = "etc_info") String etcInfo

) {

    public static ViolatedProduct toEntity(ViolatedProductDetailDto detailDto) {

        return ViolatedProduct.of(new ViolatedProductId(detailDto.productMasterNo, detailDto.productArmCode()),
                detailDto.productName, detailDto.modelName, detailDto.productArm, detailDto.productArmCodeName,
                detailDto.companyName, detailDto.originInstitute, detailDto.actionDate, detailDto.companyAddress,
                detailDto.violatedCn, detailDto.productManufactureCountry, detailDto.actOrganization,
                detailDto.productPhotoUrl,
                detailDto.fileDownloadUrl, detailDto.actionCn, detailDto.etcInfo);
    }
}
