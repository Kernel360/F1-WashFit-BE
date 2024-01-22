package com.kernel360.main.controller;

import com.kernel360.main.code.BannerResponse;
import com.kernel360.main.code.ProductsResponse;
import com.kernel360.main.dto.BannerDto;
import com.kernel360.main.dto.RecommendProductsDto;
import com.kernel360.main.service.MainService;
import com.kernel360.product.dto.ProductDto;
import com.kernel360.product.service.ProductService;
import com.kernel360.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping
@RequiredArgsConstructor
public class MainContoller {
    private final ProductService productService;

    @GetMapping("/banner")
    ResponseEntity<ApiResponse<BannerDto>> getBanner() {

        return ApiResponse.toResponseEntity(BannerResponse.GET_BANNER_DATA_SUCCESS, MainService.getSampleBanner());
    }

    @GetMapping("/recommend_products")
    ResponseEntity<ApiResponse<List<RecommendProductsDto>>> getRecommendProducts() {
        List<RecommendProductsDto> recommendProductList = productService.getRecommendProductList();

        return ApiResponse.toResponseEntity(ProductsResponse.GET_RECOMMEND_PRODUCT_DATA_SUCCESS, recommendProductList);

    }
    @GetMapping("/products/")
    ResponseEntity<ApiResponse<List<ProductDto>>> getProducts(@RequestParam(name ="sortType", defaultValue = "viewCnt_order") Sort sortType){
        List<ProductDto> productDtos = sortType.sort(productService);

        return ApiResponse.toResponseEntity(ProductsResponse.GET_PRODUCT_DATA_SUCCESS, productDtos);
    }

}
