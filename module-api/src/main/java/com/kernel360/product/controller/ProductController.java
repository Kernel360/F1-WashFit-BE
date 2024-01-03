package com.kernel360.product.controller;
import com.kernel360.product.dto.ProductDto;
import com.kernel360.product.entity.Product;
import com.kernel360.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/")
    ResponseEntity<List<ProductDto>> findProductList(){
        final List<Product> productList = productService.getProductList();
        final List<ProductDto> productDtoList = productList.stream()
                .map(ProductDto::from)
                .toList();

        return new ResponseEntity<>(productDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<ProductDto> findProductById(@PathVariable("id") Long productId) {
        Product product = productService.getProductById(productId).orElse(null);
        final ProductDto productDto = ProductDto.from(product);

        return product == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
                : ResponseEntity.status(HttpStatus.OK).body(productDto);

    }


}
