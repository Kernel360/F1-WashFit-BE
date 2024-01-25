package com.kernel360.mypage.controller;

import com.kernel360.common.ControllerTest;
import com.kernel360.product.dto.ProductDto;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class MyPageControllerTest extends ControllerTest {

    private FixtureMonkey fixtureMonkey;

    @BeforeEach
    void 준비() {
        fixtureMonkey = FixtureMonkey.builder()
                .objectIntrospector(new FailoverIntrospector(
                        Arrays.asList(
                                BuilderArbitraryIntrospector.INSTANCE,
                                FieldReflectionArbitraryIntrospector.INSTANCE,
                                ConstructorPropertiesArbitraryIntrospector.INSTANCE,
                                BeanArbitraryIntrospector.INSTANCE
                        )
                ))
                .build();
    }

    @Test
    void 마이페이지_메인요청이왔을때_200요청과_응답이_잘반환되는지() throws Exception {
        //given
        List<ProductDto> productDtoList = fixtureMonkey.giveMeBuilder(ProductDto.class).sampleList(5);

        //when
        when(productService.getProductListOrderByViewCount()).thenReturn(productDtoList);

        //then
        mockMvc.perform(get("/mypage/main"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.Banner").value("http://localhost:8080/bannersample.png"))
                .andExpect(jsonPath("$.Suggest").value("http://localhost:8080/suggestsample.png"))
                .andExpect(jsonPath("$.Product", hasSize(5)));

        verify(productService, times(1)).getProductListOrderByViewCount();
    }


}