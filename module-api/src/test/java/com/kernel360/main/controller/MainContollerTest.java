package com.kernel360.main.controller;

import com.kernel360.common.ControllerTest;
import com.kernel360.main.dto.BannerDto;
import com.kernel360.main.dto.RecommendProductsDto;
import com.kernel360.product.dto.ProductDto;
import com.kernel360.product.entity.SafetyStatus;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.Arrays;
import java.util.List;

import static com.kernel360.common.utils.RestDocumentUtils.getDocumentRequest;
import static com.kernel360.common.utils.RestDocumentUtils.getDocumentResponse;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
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
        when(mainService.getBanner()).thenReturn(bannerDto);

        //when & then
        mockMvc.perform(get("/banner"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.value.id").value(1L))
                .andExpect(jsonPath("$.value.imageSource").value("classpath:static/bannerSample.png"))
                .andExpect(jsonPath("$.value.alt").value("Banner Image"))
                .andDo(document(
                        "banner/get-banner",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(beneathPath("value").withSubsectionId("value"),
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("배너 ID"),
                                fieldWithPath("imageSource").type(JsonFieldType.STRING).description("배너 이미지 경로"),
                                fieldWithPath("alt").type(JsonFieldType.STRING).description("배너 대체 텍스트")
                        )
                ));

        verify(mainService, times(1)).getBanner();
    }


    @Test
    void 메인페이지_추천상품을_호출할때_200응답과_데이터가_잘보내지는지() throws Exception {
        //given
        List<RecommendProductsDto> recommendProductsDtos = fixtureMonkey.giveMeBuilder(RecommendProductsDto.class)
                .setNotNull("id").setNotNull("imageSource").setNotNull("alt").setNotNull("productName")
                .sampleList(5);
        when(productService.getRecommendProductList()).thenReturn(recommendProductsDtos);

//        when & then
        mockMvc.perform(get("/recommend-products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.code").value("PMB001"))
                .andExpect(jsonPath("$.message").value("추천제품정보 조회 성공"))
                .andExpect(jsonPath("$.value", hasSize(5)))
                .andExpect(jsonPath("$.value[0].id").value(notNullValue()))
                .andExpect(jsonPath("$.value[0].imageSource").value(notNullValue()))
                .andExpect(jsonPath("$.value[0].alt").value(notNullValue()))
                .andExpect(jsonPath("$.value[0].productName").value(notNullValue()))
                .andDo(document(
                        "recommend-products/get-recommend-products",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태 코드"),
                                fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답메세지"),
                                fieldWithPath("value").type(JsonFieldType.ARRAY).description("제품 리스트"),
                                fieldWithPath("value[].id").type(JsonFieldType.NUMBER).description("제품 ID"),
                                fieldWithPath("value[].imageSource").type(JsonFieldType.STRING).description("이미지 URL"),
                                fieldWithPath("value[].alt").type(JsonFieldType.STRING).description("이미지 대체 텍스트"),
                                fieldWithPath("value[].productName").type(JsonFieldType.STRING).description("제품명")
                        )
                ));
    }

    @ParameterizedTest
    @EnumSource(value = Sort.class, names = {"VIEW_COUNT_PRODUCT_ORDER", "VIOLATION_PRODUCT_LIST", "RECENT_PRODUCT_ORDER"})
    void 메인페이지_조회순으로_제품리스트_요청시_200응답과_데이터가_잘_반환되는지(Sort sortType) throws Exception {
        // given
        List<ProductDto> productDtos = fixtureMonkey.giveMeBuilder(ProductDto.class)
                .setNotNull("productNo")
                .setNotNull("productName")
                .setNotNull("viewCount")
                .setNotNull("reportNumber")
                .set("safetyStatus", SafetyStatus.SAFE)
                .setNotNull("createdAt")
                .setNotNull("createdBy")
                .sampleList(5);


        if (sortType == Sort.VIEW_COUNT_PRODUCT_ORDER) {
            when(productService.getProductListOrderByViewCount()).thenReturn(productDtos);
        } else if (sortType == Sort.VIOLATION_PRODUCT_LIST) {
            when(productService.getViolationProducts()).thenReturn(productDtos);
        } else if (sortType == Sort.RECENT_PRODUCT_ORDER) {
            when(productService.getRecentProducts()).thenReturn(productDtos);
        }

        // when & then
        mockMvc.perform(get("/products/rank")
                        .queryParam("sortType", sortType.getOrderType()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.value", hasSize(5)))
                .andExpect(jsonPath("$.value[0].productNo").isNotEmpty())
                .andExpect(jsonPath("$.value[0].productName").isNotEmpty())
                .andExpect(jsonPath("$.value[0].viewCount").isNotEmpty())
                .andDo(document(
                        "products/get-products-view-count-order",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        queryParameters(
                                parameterWithName("sortType").description("정렬기준")
                        ),
                        responseFields(
                                fieldWithPath("status").description("상태 코드"),
                                fieldWithPath("code").description("응답 코드"),
                                fieldWithPath("message").description("응답메세지"),
                                fieldWithPath("value").description("제품 리스트"),
                                fieldWithPath("value[].productNo").description("제품 ID"),
                                fieldWithPath("value[].productName").description("제품명"),
                                fieldWithPath("value[].barcode").optional().description("바코드"),
                                fieldWithPath("value[].imageSource").optional().description("이미지 소스"),
                                fieldWithPath("value[].reportNumber").description("신고 번호"),
                                fieldWithPath("value[].safetyStatus").description("안전 상태"),
                                fieldWithPath("value[].viewCount").description("조회수"),
                                fieldWithPath("value[].createdAt").description("생성날짜"),
                                fieldWithPath("value[].createdBy").description("생성자"),
                                fieldWithPath("value[].modifiedAt").optional().description("수정날짜"),
                                fieldWithPath("value[].modifiedBy").optional().description("수정자"),
                                fieldWithPath("value[].brand").optional().description("브랜드"),
                                fieldWithPath("value[].upperItem").optional().description("상위 항목")
                        )
                ));

        if (sortType == Sort.VIEW_COUNT_PRODUCT_ORDER) {
            verify(productService, times(1)).getProductListOrderByViewCount();
        } else if (sortType == Sort.VIOLATION_PRODUCT_LIST) {
            verify(productService, times(1)).getViolationProducts();
        } else if (sortType == Sort.RECENT_PRODUCT_ORDER) {
            verify(productService, times(1)).getRecentProducts();
        }

    }


//FIXME:: 좋아요 기능구현후, 데이터를 바탕으로 추천기능구현후, 테스트코드 변경예정
//    @Test
//    void 메인페이지_추천제품_정렬_옵션으로_요청시_200응답과_데이터가_잘_반환되는지() throws Exception {
//        // given
//        List<RecommendProductsDto> recommendProducts = fixtureMonkey.giveMeBuilder(RecommendProductsDto.class)
//                .sampleList(5);
//        when(productService.getRecommendProductList()).thenReturn(recommendProducts);
//
//        // when & then
//        mockMvc.perform(get("/products/rank")
//                        .queryParam("sortType", "recommend-order"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.status").value(200));
////                .andExpect(jsonPath("$.value", hasSize(5)));
//        ;
//
//        verify(productService, times(1)).getRecommendProductList();
//
//    }
}