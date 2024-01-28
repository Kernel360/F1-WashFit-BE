package com.kernel360.main.controller;

import com.kernel360.common.ControllerTest;
import com.kernel360.main.dto.BannerDto;
import com.kernel360.main.dto.RecommendProductsDto;
import com.kernel360.product.dto.ProductDto;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.Arrays;
import java.util.List;

import static com.kernel360.common.utils.RestDocumentUtils.getDocumentRequest;
import static com.kernel360.common.utils.RestDocumentUtils.getDocumentResponse;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class MainContollerTest extends ControllerTest {
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
    void 메인페이지_배너요청이_왔을때_200_응답이_잘반환되는지() throws Exception {
        //given
        BannerDto bannerDto = BannerDto.of(1L, "classpath:static/bannerSample.png", "Banner Image");
        when(mainService.getSampleBanner()).thenReturn(bannerDto);

        //when & then
        mockMvc.perform(get("/banner"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.value.id").value(1L))
                .andExpect(jsonPath("$.value.image").value("classpath:static/bannerSample.png"))
                .andExpect(jsonPath("$.value.alt").value("Banner Image"))
                .andDo(document(
                        "banner/get-banner",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(beneathPath("value").withSubsectionId("value"),
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("배너 ID"),
                                fieldWithPath("image").type(JsonFieldType.STRING).description("배너 이미지 경로"),
                                fieldWithPath("alt").type(JsonFieldType.STRING).description("배너 대체 텍스트")
                        )
                ));

        verify(mainService, times(1)).getSampleBanner();
    }


    @Test
    void 메인페이지_추천상품을_호출할때_200응답과_데이터가_잘보내지는지() throws Exception {
        //given
        List<RecommendProductsDto> recommendProductsDtos = fixtureMonkey.giveMeBuilder(RecommendProductsDto.class)
                .setNotNull("id").setNotNull("image").setNotNull("alt").setNotNull("productName")
                .sampleList(5);
        when(productService.getRecommendProductList()).thenReturn(recommendProductsDtos);

        //when & then
        mockMvc.perform(get("/recommend-products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.code").value("PMB001"))
                .andExpect(jsonPath("$.message").value("추천제품정보 조회 성공"))
                .andExpect(jsonPath("$.value", hasSize(5)))
                .andExpect(jsonPath("$.value[0].id").isNotEmpty())
                .andExpect(jsonPath("$.value[0].image").isNotEmpty())
                .andExpect(jsonPath("$.value[0].alt").isNotEmpty())
                .andExpect(jsonPath("$.value[0].productName").isNotEmpty())
                .andDo(document(
                        "recommend-products/get-recommend-products",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태 코드"),
                                fieldWithPath("code").type(JsonFieldType.STRING).description("비지니스 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"),
                                subsectionWithPath("value").description("추천 제품 리스트"),
                                fieldWithPath("value[].id").type(JsonFieldType.NUMBER).description("제품 ID"),
                                fieldWithPath("value[].image").type(JsonFieldType.STRING).description("이미지 URL"),
                                fieldWithPath("value[].alt").type(JsonFieldType.STRING).description("이미지 설명"),
                                fieldWithPath("value[].productName").type(JsonFieldType.STRING).description("제품명")
                        )
                ));

        verify(productService, times(1)).getRecommendProductList();
    }

    @Test
    void 메인페이지_조회순으로_제품리스트_요청시_200응답과_데이터가_잘_반환되는지() throws Exception {
        // given
        List<ProductDto> productDtos = fixtureMonkey.giveMeBuilder(ProductDto.class)
                .setNotNull("productNo")
                .setNotNull("productName")
                .setNotNull("viewCount")
                .sampleList(5);
        when(productService.getProductListOrderByViewCount()).thenReturn(productDtos);

        // when & then
        mockMvc.perform(get("/products/rank")
                        .queryParam("sortType", "viewCnt-order"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.value", hasSize(5)))
                .andExpect(jsonPath("$.value[0].id").isNotEmpty())
                .andExpect(jsonPath("$.value[0].name").isNotEmpty())
                .andDo(document(
                        "products/get-products-view-count-order",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태 코드"),
                                fieldWithPath("value").type(JsonFieldType.ARRAY).description("제품 리스트"),
                                fieldWithPath("value[].id").type(JsonFieldType.NUMBER).description("제품 아이디"),
                                fieldWithPath("value[].name").type(JsonFieldType.STRING).description("제품명")
                        )
                ));

        verify(productService, times(1)).getProductListOrderByViewCount();
    }

}