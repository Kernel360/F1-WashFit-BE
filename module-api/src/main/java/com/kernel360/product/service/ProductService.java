package com.kernel360.product.service;

import com.kernel360.exception.BusinessException;
import com.kernel360.likes.repository.LikeRepository;
import com.kernel360.main.dto.RecommendProductsDto;
import com.kernel360.product.code.ProductsErrorCode;
import com.kernel360.product.dto.ProductDetailDto;
import com.kernel360.product.dto.ProductDto;
import com.kernel360.product.entity.Product;
import com.kernel360.product.entity.SafetyStatus;
import com.kernel360.product.repository.ProductRepository;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final LikeRepository likeRepository;

    @Transactional(readOnly = true)
    public List<ProductDto> getProducts() {

        return productRepository.findAll()
                .stream()
                .map(ProductDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public Page<ProductDto> getProductsByKeyword(String keyword, Pageable pageable) {
        Page<Product> products = productRepository.getProductsByKeyword(keyword, pageable);

        return products.map(ProductDto::from);
    }

    @Transactional(readOnly = true)
    public Page<ProductDto> getProductListOrderByViewCount(Pageable pageable) {

        return productRepository.findAllByOrderByViewCountDesc(pageable).map(ProductDto::from);
    }

    @Transactional(readOnly = true)
    public Page<RecommendProductsDto> getRecommendProducts(Pageable pageable) {
        Page<Product> productList = productRepository.findTop5ByOrderByProductNameDesc(pageable);

        return productList.map(RecommendProductsDto::from);
    }

    @Transactional(readOnly = true)
    public Page<ProductDto> getViolationProducts(Pageable pageable) {

        return productRepository.findAllBySafetyStatusEquals(SafetyStatus.DANGER, pageable)
                .map(ProductDto::from);
    }

    @Transactional(readOnly = true)
    public Page<ProductDto> getRecentProducts(Pageable pageable) {

        return productRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(ProductDto::from);

    }

    @Transactional(readOnly = true)
    public ProductDetailDto getProductById(Long id) {

        return productRepository.findById(id)
                .map(ProductDetailDto::from)
                .orElseThrow(() -> new BusinessException(ProductsErrorCode.NOT_FOUND_PRODUCT));
    }

    @Transactional
    public void updateViewCount(Long id) {
        productRepository.updateViewCount(id);
    }

    @Transactional(readOnly = true)
    public Page<ProductDetailDto> getProductByOCR(String reportNo, Pageable pageable) {

        return productRepository.findProductByReportNumberEquals(reportNo, pageable)
                .map(ProductDetailDto::from);
    }

    @Transactional(readOnly = true)
    public Page<ProductDto> getFavoriteProducts(Pageable pageable) {
        Page<Object[]> results = likeRepository.findTop20ByProductNoOrderByLikeCountDesc(pageable);

        List<ProductDto> productDtos = results.getContent().stream()
                .map(result -> {
                    Long productNo = (Long) result[0];
                    Product product = productRepository.findById(productNo)
                            .orElseThrow(() -> new BusinessException(ProductsErrorCode.NOT_FOUND_PRODUCT));
                    return ProductDto.from(product);
                })
                .toList();

        return new PageImpl<>(productDtos, pageable, results.getTotalElements());
    }

    @Transactional(readOnly = true)
    public Page<ProductDto> getProductWithKeywordAndOrderByViewCount(String keyword, Pageable pageable) {

        return productRepository.getProductWithKeywordAndOrderByViewCount(keyword, pageable)
                .map(ProductDto::from);
    }

    @Transactional(readOnly = true)
    public Page<ProductDto> getProductWithKeywordAndViolationProducts(String keyword, Pageable pageable) {

        return productRepository.findByProductWithKeywordAndSafetyStatus(keyword, SafetyStatus.DANGER, pageable)
                .map(ProductDto::from);
    }

    @Transactional(readOnly = true)
    public Page<ProductDto> getProductWithKeywordAndOrderByRecommend(String keyword, Pageable pageable) {

        return productRepository.getProductWithKeywordAndOrderByRecommend(keyword, pageable)
                .map(ProductDto::from);

    }

    @Transactional(readOnly = true)
    public Page<ProductDto> getProductWithKeywordAndRecentOrder(String keyword, Pageable pageable) {

        return productRepository.getProductWithKeywordAndRecentOrder(keyword, pageable)
                .map(ProductDto::from);
    }
}
