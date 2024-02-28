package com.kernel360.main.controller;

import com.kernel360.main.code.BannerBusinessCode;
import com.kernel360.main.dto.BannerDto;
import com.kernel360.main.dto.RecommendProductsDto;
import com.kernel360.main.service.MainService;
import com.kernel360.product.code.ProductsBusinessCode;
import com.kernel360.product.dto.ProductDto;
import com.kernel360.product.service.ProductService;
import com.kernel360.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping
@RequiredArgsConstructor
public class MainController {
    private final ProductService productService;
    private final MainService mainService;

    @GetMapping("/banner")
    ResponseEntity<ApiResponse<BannerDto>> getBanner() {

        return ApiResponse.toResponseEntity(BannerBusinessCode.GET_BANNER_DATA_SUCCESS, mainService.getBanner());
    }

    @GetMapping("/recommend-products")
    ResponseEntity<ApiResponse<Page<RecommendProductsDto>>> getRecommendProducts(Pageable pageable) {
        Page<RecommendProductsDto> recommendProducts = productService.getRecommendProducts(pageable);

        return ApiResponse.toResponseEntity(ProductsBusinessCode.GET_RECOMMEND_PRODUCT_DATA_SUCCESS, recommendProducts);

    }

    @GetMapping("/products/rank")
    ResponseEntity<ApiResponse<Page<ProductDto>>> getProducts(
            @RequestParam(name = "sortType", defaultValue = "viewCnt-order") Sort sortType, Pageable pageable) {
        Page<ProductDto> productDtos = sortType.sort(productService, pageable);

        return ApiResponse.toResponseEntity(ProductsBusinessCode.GET_PRODUCT_DATA_SUCCESS, productDtos);
    }



}
