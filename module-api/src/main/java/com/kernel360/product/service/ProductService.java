package com.kernel360.product.service;

import com.kernel360.product.dto.ProductDto;
import com.kernel360.product.entity.Product;
import com.kernel360.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Optional<Product> getProductById(Long id) {

        return productRepository.findById(id);
    }

    public List<Product> getProductList() {

        return productRepository.findAll();
    }

    public List<ProductDto> getProductListByKeyword(String keyword) {
        List<Product> products = productRepository.findByKeyword(keyword);

        return products.stream().map(ProductDto::from).toList();
    }
}
