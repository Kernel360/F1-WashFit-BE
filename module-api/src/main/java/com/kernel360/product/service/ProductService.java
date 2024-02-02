package com.kernel360.product.service;

import com.kernel360.exception.BusinessException;
import com.kernel360.main.code.ProductsErrorCode;
import com.kernel360.main.dto.RecommendProductsDto;
import com.kernel360.product.dto.ProductDetailDto;
import com.kernel360.product.dto.ProductDto;
import com.kernel360.product.entity.Product;
import com.kernel360.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public ProductDetailDto getProductById(Long id) {

        return productRepository.findById(id)
                .map(ProductDetailDto::from)
                .orElseThrow(() -> new BusinessException(ProductsErrorCode.NOT_FOUND_PRODUCT));

    }

    @Transactional(readOnly = true)
    public List<ProductDto> getProductList() {

        return productRepository.findAll()
                .stream()
                .map(ProductDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getProductListByKeyword(String keyword) {
        List<Product> products = productRepository.
                findByProductNameContaining(keyword);

        return products.stream().map(ProductDto::from).toList();
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getProductListOrderByViewCount() {
        List<Product> products = productRepository.findTop5ByOrderByProductNameDesc();
//        List<Product> products = productRepository.findAllByOrderByViewCountDesc();
        //FIXME:: viewCount 값이 존재하지 않아,제품이룸순 데이터를 샘플로 전달한 후 향후 변경

        return products.stream().map(ProductDto::from).toList();
    }

    @Transactional(readOnly = true)
    public List<RecommendProductsDto> getRecommendProductList() {
        List<Product> productList = productRepository.findTop5ByOrderByProductNameDesc();

        return productList.stream().map(RecommendProductsDto::from).toList();
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getViolationProducts() {

        return getRecentProducts();
        //FIXME :: 데이터가 없어서 최근데이터로 대신 리턴
//        return productRepository.findAllBySafetyStatusEquals(SafetyStatus.DANGER)
//                .stream()
//                .map(ProductDto::from)
//                .toList();
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getRecentProducts() {

        return productRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(ProductDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductDetailDto getProductDetailById(Long id) {

        return
        productRepository.findById(id)
                .map(ProductDetailDto::from)
                .orElseThrow(() -> new BusinessException(ProductsErrorCode.NOT_FOUND_PRODUCT));
    }
}
