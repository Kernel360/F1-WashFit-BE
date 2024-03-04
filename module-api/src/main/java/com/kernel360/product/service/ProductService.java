package com.kernel360.product.service;

import com.kernel360.exception.BusinessException;
import com.kernel360.likes.repository.LikeRepositoryJpa;
import com.kernel360.main.dto.RecommendProductsDto;
import com.kernel360.product.code.ProductsErrorCode;
import com.kernel360.product.dto.ProductDetailDto;
import com.kernel360.product.dto.ProductDto;
import com.kernel360.product.dto.ProductResponse;
import com.kernel360.product.dto.ProductSearchDto;
import com.kernel360.product.entity.Product;
import com.kernel360.product.entity.SafetyStatus;

import java.util.List;

import com.kernel360.product.repository.ProductRepositoryDsl;
import com.kernel360.product.repository.ProductRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepositoryJpa productRepositoryJpa;
    private final ProductRepositoryDsl productRepositoryDsl;
    private final LikeRepositoryJpa likeRepositoryJpa;

    @Transactional(readOnly = true)
    public List<ProductDto> getProducts() {

        return productRepositoryJpa.findAll()
                .stream()
                .map(ProductDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public Page<ProductDto> getProductsByKeyword(String keyword, Pageable pageable) {
        Page<Product> products = productRepositoryJpa.getProductsByKeyword(keyword, pageable);

        return products.map(ProductDto::from);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> getProductListOrderByViewCount(Pageable pageable) {

        return productRepositoryJpa.findAllByOrderByViewCountDesc(pageable).map(ProductResponse::from);
    }

    @Transactional(readOnly = true)
    public Page<RecommendProductsDto> getRecommendProducts(Pageable pageable) {
        Page<Product> productList = productRepositoryJpa.findTop5ByOrderByProductNameDesc(pageable);

        return productList.map(RecommendProductsDto::from);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> getViolationProducts(Pageable pageable) {

        return productRepositoryJpa.findAllBySafetyStatusEquals(SafetyStatus.DANGER, pageable)
                .map(ProductResponse::from);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> getRecentProducts(Pageable pageable) {

        return productRepositoryJpa.findAllByOrderByCreatedAtDesc(pageable)
                .map(ProductResponse::from);

    }

    @Transactional(readOnly = true)
    public ProductDetailDto getProductById(Long id) {

        return productRepositoryJpa.findById(id)
                .map(ProductDetailDto::from)
                .orElseThrow(() -> new BusinessException(ProductsErrorCode.NOT_FOUND_PRODUCT));
    }

    @Transactional
    public void updateViewCount(Long id) {
        productRepositoryJpa.updateViewCount(id);
    }

    @Transactional(readOnly = true)
    public Page<ProductDetailDto> getProductByOCR(String reportNo, Pageable pageable) {

        return productRepositoryJpa.findProductByReportNumberEquals(reportNo, pageable)
                .map(ProductDetailDto::from);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> getFavoriteProducts(Pageable pageable) {
        Page<Object[]> results = likeRepositoryJpa.findTop20ByProductNoOrderByLikeCountDesc(pageable);

        List<ProductResponse> productDtos = results.getContent().stream()
                .map(result -> {
                    Long productNo = (Long) result[0];
                    Product product = productRepositoryJpa.findById(productNo)
                            .orElseThrow(() -> new BusinessException(ProductsErrorCode.NOT_FOUND_PRODUCT));
                    return ProductResponse.from(product);
                })
                .toList();

        return new PageImpl<>(productDtos, pageable, results.getTotalElements());
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> getProductWithKeywordAndOrderByViewCount(String keyword, Pageable pageable) {

        return productRepositoryJpa.getProductWithKeywordAndOrderByViewCount(keyword, pageable)
                .map(ProductResponse::from);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> getProductWithKeywordAndViolationProducts(String keyword, Pageable pageable) {

        return productRepositoryJpa.findByProductWithKeywordAndSafetyStatus(keyword, SafetyStatus.DANGER, pageable)
                .map(ProductResponse::from);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> getProductWithKeywordAndOrderByRecommend(String keyword, Pageable pageable) {

        return productRepositoryJpa.getProductWithKeywordAndOrderByRecommend(keyword, pageable)
                .map(ProductResponse::from);

    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> getProductWithKeywordAndRecentOrder(String keyword, Pageable pageable) {

        return productRepositoryJpa.getProductWithKeywordAndRecentOrder(keyword, pageable)
                .map(ProductResponse::from);
    }

    public List<RecommendProductsDto> getRecommendProductsWithRandom() {

        return productRepositoryJpa.getRecommendProductsWithRandom().stream().map(RecommendProductsDto::from).toList();
    }

    public Page<ProductResponse> searchWithCondition(ProductSearchDto productSearchDto, Pageable pageable) {

        return productRepositoryDsl.findAllByCondition(productSearchDto, pageable);
    }
}
