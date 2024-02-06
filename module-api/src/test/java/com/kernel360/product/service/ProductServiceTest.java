package com.kernel360.product.service;

import com.kernel360.product.dto.ProductDetailDto;
import com.kernel360.product.dto.ProductDto;
import com.kernel360.product.entity.Product;
import com.kernel360.product.repository.ProductRepository;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.*;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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
    private Pageable pageable;
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
        pageable = mock(Pageable.class);
        productService = new ProductService(productRepository);
    }

    @Test
    void 아이디로_제품찾기() {

        Product product = fixtureMonkey.giveMeBuilder(Product.class)
                .set("productNo", 1L)
                .sample();

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        ProductDetailDto foundProduct = productService.getProductById(1L);

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
    void 키워드로_제품_목록_조회() {
        // given
        String keyword = "sample";
        List<Product> productList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Product product = fixtureMonkey.giveMeBuilder(Product.class)
                    .set("productName", "sample" + i)
                    .sample();
            productList.add(product);
        }
        Page<Product> productsPage = new PageImpl<>(productList, pageable, productList.size());

        when(productRepository.findByProductNameContaining(keyword, pageable)).thenReturn(productsPage);

        Page<ProductDto> productDtos = productService.getProductListByKeyword(keyword, pageable);

        // then
        then(productDtos.getContent()).hasSize(5);
        then(productDtos.getContent()).allSatisfy(productDto -> then(productDto.productName()).contains(keyword));
    }
}
