package com.kernel360.product.service;

import com.kernel360.exception.BusinessException;
import com.kernel360.main.code.ProductsErrorCode;
import com.kernel360.main.dto.RecommendProductsDto;
import com.kernel360.product.dto.ProductDto;
import com.kernel360.product.entity.Product;
import com.kernel360.product.entity.SafetyStatus;
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
    public ProductDto getProductById(Long id) {

        return
                productRepository.findById(id)
                        .map(ProductDto::from)
                        .orElseThrow(() -> new BusinessException(ProductsErrorCode.INVALID_PRODUCT_CODE_NAME));

    }

    @Transactional(readOnly = true)
    public List<ProductDto> getProductList() {

        List<ProductDto> productDtos = productRepository.findAll()
                .stream()
                .map(ProductDto::from)
                .toList();

        if (productDtos.isEmpty()) {
            throw new BusinessException(ProductsErrorCode.INVALID_PRODUCT_CODE_NAME);
        }

        return productDtos;
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getProductListByKeyword(String keyword) {
        List<Product> products = productRepository.findByKeyword(keyword);

        if (products.isEmpty()) {
            throw new BusinessException(ProductsErrorCode.INVALID_PRODUCT_CODE_NAME);
        }

        return products.stream().map(ProductDto::from).toList();
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getProductListOrderByViewCount() {
        List<Product> products = productRepository.findAllByOrderByViewCountDesc();

        if (products.isEmpty()) {
            throw new BusinessException(ProductsErrorCode.INVALID_PRODUCT_CODE_NAME);
        }

        return products.stream().map(ProductDto::from).toList();
    }

    @Transactional(readOnly = true)
    public List<RecommendProductsDto> getRecommendProductList() {
        List<Product> productList = productRepository.findTop5ByOrderByProductNameDesc();

        return productList.stream().map(RecommendProductsDto::from).toList();
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getViolationProducts() {
        List<ProductDto> productDtoList = productRepository.findAllBySafetyStatusEquals(SafetyStatus.DANGER)
                .stream()
                .map(ProductDto::from)
                .toList();

        if (productDtoList.isEmpty()) {
            throw new BusinessException(ProductsErrorCode.INVALID_PRODUCT_CODE_NAME);
        }

        return  productDtoList;
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getRecentProducts() {
        List<ProductDto> dtoList = productRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(ProductDto::from)
                .toList();

        if (dtoList.isEmpty()) {
            throw new BusinessException(ProductsErrorCode.INVALID_PRODUCT_CODE_NAME);
        }

        return  dtoList;
    }
}
