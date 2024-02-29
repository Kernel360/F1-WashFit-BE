package com.kernel360.brand.controller;

import com.kernel360.brand.code.BrandBusinessCode;
import com.kernel360.brand.dto.BrandDto;
import com.kernel360.brand.service.BrandServiceImpl;
import com.kernel360.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/admin/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandServiceImpl brandService;

    @GetMapping
    public ResponseEntity<ApiResponse<Object>> getBrandList() {

        return ApiResponse.toResponseEntity(BrandBusinessCode.SUCCESS_FOUND_BRAND_LIST, brandService.findAllBrand());
    }

    @GetMapping("/brand")
    public ResponseEntity<ApiResponse<Object>> findBrandById(@RequestBody BrandDto brandDto) {

        return ApiResponse.toResponseEntity(BrandBusinessCode.SUCCESS_FOUND_EXACT_BRAND,
                brandService.findBrandByBrandId(brandDto.brandNo()));
    }

    @GetMapping("/brandName")
    public ResponseEntity<ApiResponse<BrandDto>> findBrandByBrandName(@RequestBody BrandDto brandDto) {

        return ApiResponse.toResponseEntity(BrandBusinessCode.SUCCESS_FOUND_EXACT_BRAND,
                brandService.findBrandByBrandName(brandDto.brandName()));
    }


    @PostMapping("/brand")
    public ResponseEntity<ApiResponse<Object>> createBrand(@RequestBody BrandDto brandDto) {
        brandService.createBrand(brandDto);

        return ApiResponse.toResponseEntity(BrandBusinessCode.SUCCESS_CREATED_BRAND);
    }

    @DeleteMapping("/brand")
    public ResponseEntity<ApiResponse<BrandDto>> deleteBrand(@RequestBody BrandDto brandDto,
                                                             @RequestHeader("Authorization") String token) {
        brandService.deleteBrand(brandDto.brandNo());

        return ApiResponse.toResponseEntity(BrandBusinessCode.SUCCESS_DELETED_BRAND);
    }

    @PutMapping("/brand")
    public ResponseEntity<ApiResponse<BrandDto>> updateAll(@RequestBody BrandDto brandDto) {
        brandService.updateBrand(brandDto);

        return ApiResponse.toResponseEntity(BrandBusinessCode.SUCCESS_UPDATED_BRAND);
    }

    @PatchMapping("/description")
    public ResponseEntity<ApiResponse<BrandDto>> updateBrandDescription(@RequestBody BrandDto brandDto) {
        brandService.updateBrandDescription(brandDto.brandNo(), brandDto.description());

        return ApiResponse.toResponseEntity(BrandBusinessCode.SUCCESS_UPDATED_BRAND);
    }

    @PatchMapping("/brandName")
    public ResponseEntity<ApiResponse<BrandDto>> updateBrandName(@RequestBody BrandDto brandDto) {
        brandService.updateBrandName(brandDto.brandNo(), brandDto.brandName());

        return ApiResponse.toResponseEntity(BrandBusinessCode.SUCCESS_UPDATED_BRAND);
    }

    @PatchMapping("/companyName")
    public ResponseEntity<ApiResponse<BrandDto>> updateBrandCompanyName(@RequestBody BrandDto brandDto) {
        brandService.updateBrandCompanyName(brandDto.brandNo(), brandDto.companyName());

        return ApiResponse.toResponseEntity(BrandBusinessCode.SUCCESS_UPDATED_BRAND);
    }

    @PatchMapping("/nationName")
    public ResponseEntity<ApiResponse<BrandDto>> updateBrandNationName(@RequestBody BrandDto brandDto) {
        brandService.updateBrandNationName(brandDto.brandNo(), brandDto.nationName());

        return ApiResponse.toResponseEntity(BrandBusinessCode.SUCCESS_UPDATED_BRAND);
    }
}
