package com.kernel360.brand.dto;

import com.kernel360.brand.entity.Brand;

public record BrandDto(Long brandNo, String brandName, String companyName, String description, String nationName) {

    public static BrandDto of(Long brandNo, String brandName, String companyName, String description, String nationName) {
        return new BrandDto(brandNo, brandName, companyName, description, nationName);
    }

    public static BrandDto of(String brandName, String companyName, String description, String nationName) {
        return new BrandDto(null, brandName, companyName, description, nationName);
    }

    public static BrandDto fromEntity(Brand brand) {
        return new BrandDto(brand.getBrandNo(), brand.getBrandName(), brand.getCompanyName(), brand.getDescription(),
                brand.getNationName());
    }

}
