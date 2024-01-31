package com.kernel360.product.service;

import com.kernel360.product.dto.ProductDto;
import com.kernel360.product.entity.Product;
import com.kernel360.product.repository.ProductRepository;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.*;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductServiceTest {
    private FixtureMonkey fixtureMonkey;
    private ProductRepository productRepository;
    private ProductService productService;
    @BeforeEach
    void 테스트준비() {
        fixtureMonkey = FixtureMonkey.builder()
                .objectIntrospector(new FailoverIntrospector(
                        Arrays.asList(
                                FieldReflectionArbitraryIntrospector.INSTANCE,
                                ConstructorPropertiesArbitraryIntrospector.INSTANCE,
                                BeanArbitraryIntrospector.INSTANCE,
                                BuilderArbitraryIntrospector.INSTANCE
                        )
                ))
                .plugin(new JakartaValidationPlugin())
                .build();

        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository);
    }

    @Test
    void 아이디로_제품찾기() {

        Product product = fixtureMonkey.giveMeBuilder(Product.class)
                .set("productNo", 1L)
                .sample();

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        ProductDto foundProduct = productService.getProductById(1L);

        //then
        then(foundProduct.productNo()).isEqualTo(product.getProductNo());
    }

    @Test
    void 전체_제품_목록_조회(){
        //given & when
        List<ProductDto> products = fixtureMonkey.giveMe(ProductDto.class, 3);
        when(productService.getProductList()).thenReturn(products);

        //then
        then(products).isEqualTo(products);
    }

    @Test
    void 키워드로_제품_목록_조회(){
        //given
        List<Product> products = new ArrayList<>();
        String keyword = "sample";

        for (int i = 0; i < 5; i++) {
            Product product = fixtureMonkey.giveMeBuilder(Product.class)
                    .set("productName", keyword + i) // Append a unique number to each productName
                    .sample();
            products.add(product);
        }
        //when
        when(productRepository.findByProductNameContaining(keyword)).thenReturn(products);
        List<ProductDto> productListByKeyword = productService.getProductListByKeyword(keyword);
        //then
        then(productListByKeyword).hasSize(5);
        then(productListByKeyword).allSatisfy(productDto -> then(productDto.productName()).contains(keyword));
    }
}
