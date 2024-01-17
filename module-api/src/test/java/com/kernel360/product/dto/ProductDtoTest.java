package com.kernel360.product.dto;

import com.kernel360.product.entity.Product;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ProductDtoTest {
    private FixtureMonkey fixtureMonkey;

    @BeforeEach
    void 준비() {
        fixtureMonkey = FixtureMonkey.builder()
                .objectIntrospector(new FailoverIntrospector(
                        Arrays.asList(
                                FieldReflectionArbitraryIntrospector.INSTANCE,
                                ConstructorPropertiesArbitraryIntrospector.INSTANCE,
                                BeanArbitraryIntrospector.INSTANCE,
                                BuilderArbitraryIntrospector.INSTANCE
                        )
                ))
                .build();
    }

    @Test
    void 엔티티객체를_ProductDto객체로_잘_변환하는지() {
        //given
        Product product = fixtureMonkey.giveMeBuilder(Product.class).sample();
        ProductDto productDto = ProductDto.from(product);

        //then
        assertEquals(product.getProductName(), productDto.productName());
        assertEquals(product.getProductNo(), productDto.productNo());
        assertEquals(product.getViewCount(), productDto.viewCount());
        assertEquals(product.getIsViolation(), productDto.isViolation());
        assertEquals(product.getBarcode(), productDto.barcode());
        assertEquals(product.getDeclareNo(), productDto.declareNo());
        assertEquals(product.getDescription(), productDto.description());

    }
}