package com.kernel360.ecolife.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "violated_product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ViolatedProduct extends ViolatedProductDetail {
    @EmbeddedId
    @Column
    ViolatedProductId id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "model_name")
    private String modelName;

    @Column(name = "product_arm")
    private String productArm;

    @Column(name = "product_arm_code_name")
    private String productArmCodeName;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "origin_institute")
    private String originInstitute;

    @Column(name = "actioned_date")
    private String actionedDate;

    private ViolatedProduct(ViolatedProductId violatedProductId,
                            String productName,
                            String modelName,
                            String productArm,
                            String productArmCodeName,
                            String companyName,
                            String originInstitute,
                            String actionedDate) {
        this.id = violatedProductId;
        this.productName = productName;
        this.modelName = modelName;
        this.productArm = productArm;
        this.productArmCodeName = productArmCodeName;
        this.companyName = companyName;
        this.originInstitute = originInstitute;
        this.actionedDate = actionedDate;
    }

    private ViolatedProduct(ViolatedProductId violatedProductId,
                            String productName,
                            String modelName,
                            String productArm,
                            String productArmCodeName,
                            String companyName,
                            String originInstitute,
                            String actionedDate,
                            String companyAddress,
                            String violatedCn,
                            String productManufactureCountry,
                            String actOrganization,
                            String productPhotoUrl,
                            String fileDownloadUrl,
                            String actionCn,
                            String etcInfo) {
        super(companyAddress, violatedCn, productManufactureCountry, actOrganization, productPhotoUrl, fileDownloadUrl,
                actionCn, etcInfo);
        this.id = violatedProductId;
        this.productName = productName;
        this.modelName = modelName;
        this.productArm = productArm;
        this.productArmCodeName = productArmCodeName;
        this.companyName = companyName;
        this.originInstitute = originInstitute;
        this.actionedDate = actionedDate;

    }

    public static ViolatedProduct of(ViolatedProductId violatedProductId,
                                     String productName,
                                     String modelName,
                                     String productArm,
                                     String productArmCodeName,
                                     String companyName,
                                     String originInstitute,
                                     String actionedDate) {

        return new ViolatedProduct(
                violatedProductId,
                productName,
                modelName,
                productArm,
                productArmCodeName,
                companyName,
                originInstitute,
                actionedDate
        );
    }

    public static ViolatedProduct of(ViolatedProductId violatedProductId,
                                     String productName,
                                     String modelName,
                                     String productArm,
                                     String productArmCodeName,
                                     String companyName,
                                     String originInstitute,
                                     String actionedDate,
                                     String companyAddress,
                                     String violatedCn,
                                     String productManufactureCountry,
                                     String actOrganization,
                                     String productPhotoUrl,
                                     String fileDownloadUrl,
                                     String actionCn,
                                     String etcInfo) {

        return new ViolatedProduct(violatedProductId, productName, modelName, productArm, productArmCodeName,
                companyName, originInstitute, actionedDate, companyAddress, violatedCn, productManufactureCountry,
                actOrganization,
                productPhotoUrl, fileDownloadUrl, actionCn, etcInfo);

    }

}
