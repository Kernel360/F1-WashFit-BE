package com.kernel360.product.controller;
import com.kernel360.product.dto.ProductDto;
import com.kernel360.product.entity.Product;
import com.kernel360.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    ResponseEntity<List<ProductDto>> findProductList(){
        final List<Product> productList = productService.getProductList();
        final List<ProductDto> productDtoList = productList.stream()
                .map(ProductDto::from)
                .toList();

        return productDtoList.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : ResponseEntity.status(HttpStatus.OK).body(productDtoList);
    }

    @GetMapping("/product/{id}")
    ResponseEntity<ProductDto> findProductById(@PathVariable("id") Long productId) {

        return productService.getProductById(productId)
                .map(ProductDto::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/products")
    ResponseEntity<List<ProductDto>> findProductByKeyword(@RequestParam("keyword") String keyword){
        final List<ProductDto> list = productService.getProductListByKeyword(keyword);

        return list.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : ResponseEntity.status(HttpStatus.OK).body(list);
    }

}
