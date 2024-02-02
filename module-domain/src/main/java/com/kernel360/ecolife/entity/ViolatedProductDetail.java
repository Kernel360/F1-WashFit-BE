package com.kernel360.ecolife.entity;

import com.kernel360.base.BaseEntity;
import com.kernel360.base.BaseRawEntity;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ViolatedProductDetail extends BaseRawEntity {

    @Column(name = "company_address")
    private String companyAddress;

    @Column(name = "violated_cn")
    private String violatedCn;

    @Column(name = "product_manufacture_country")
    private String productManufactureCountry;

    @Column(name = "act_organization")
    private String actOrganization;

    @Column(name = "product_photo_url")
    private String productPhotoUrl;

    @Column(name = "file_download_url")
    private String fileDownloadUrl;

    @Column(name = "action_cn")
    private String actionCn;

    @Column(name = "etc_info")
    private String etcInfo;
}
