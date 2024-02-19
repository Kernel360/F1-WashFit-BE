package com.kernel360.product.controller;
import com.kernel360.main.code.ProductsBusinessCode;
import com.kernel360.product.dto.ProductDetailDto;
import com.kernel360.product.dto.ProductDto;
import com.kernel360.product.service.ProductService;
import com.kernel360.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    ResponseEntity<ApiResponse<List<ProductDto>>> findProductList(){
        final List<ProductDto> productDtoList = productService.getProductList();

        return ApiResponse.toResponseEntity(ProductsBusinessCode.GET_PRODUCT_DATA_SUCCESS, productDtoList);
    }

    @GetMapping("/product/{id}")
    ResponseEntity<ApiResponse<ProductDetailDto>> findProductById(@PathVariable("id") Long productId) {
        ProductDetailDto product = productService.getProductDetailById(productId);

        return ApiResponse.toResponseEntity(ProductsBusinessCode.GET_PRODUCT_DATA_SUCCESS, product);
    }

    @GetMapping("/products/search")
    ResponseEntity<ApiResponse<Page<ProductDto>>> findProductByKeyword(@RequestParam("keyword") String keyword, Pageable pageable){
        final Page<ProductDto> list = productService.getProductListByKeyword(keyword, pageable);

        return ApiResponse.toResponseEntity(ProductsBusinessCode.GET_PRODUCT_DATA_SUCCESS, list);
    }

}
