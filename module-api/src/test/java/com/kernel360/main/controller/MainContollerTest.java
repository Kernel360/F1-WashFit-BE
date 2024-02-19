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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.Arrays;
import java.util.List;

import static com.kernel360.common.utils.RestDocumentUtils.getDocumentRequest;
import static com.kernel360.common.utils.RestDocumentUtils.getDocumentResponse;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        // given
        List<RecommendProductsDto> recommendProductsDtos = fixtureMonkey.giveMeBuilder(RecommendProductsDto.class)
                .setNotNull("id")
                .setNotNull("imageSource")
                .setNotNull("alt")
                .setNotNull("productName")
                .sampleList(5);

        Pageable pageable = PageRequest.of(0, 5);
        Page<RecommendProductsDto> page = new PageImpl<>(recommendProductsDtos, pageable, recommendProductsDtos.size());
        when(productService.getRecommendProductList(any(Pageable.class))).thenReturn(page);

//         when & then
        mockMvc.perform(get("/recommend-products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.code").value("PMB001"))
                .andExpect(jsonPath("$.message").value("추천제품정보 조회 성공"))
                .andExpect(jsonPath("$.value.content[*].id").exists())
                .andExpect(jsonPath("$.value.content[*].imageSource").exists())
                .andExpect(jsonPath("$.value.content[*].alt").exists())
                .andExpect(jsonPath("$.value.content[*].productName").exists())
                .andDo(document("recommend-products/get-recommend-products",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태 코드"),
                                fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답메세지"),
                                fieldWithPath("value").type(JsonFieldType.OBJECT).description("응답 본문의 루트 객체"),
                                fieldWithPath("value.content[].id").type(JsonFieldType.NUMBER).description("제품 ID"),
                                fieldWithPath("value.content[].imageSource").type(JsonFieldType.STRING).description("이미지 URL"),
                                fieldWithPath("value.content[].alt").type(JsonFieldType.STRING).description("이미지 대체 텍스트"),
                                fieldWithPath("value.content[].productName").type(JsonFieldType.STRING).description("제품명"),
                                fieldWithPath("value.pageable").type(JsonFieldType.OBJECT).description("페이지 정보"),
                                fieldWithPath("value.pageable.pageNumber").type(JsonFieldType.NUMBER).description("페이지 번호"),
                                fieldWithPath("value.pageable.pageSize").type(JsonFieldType.NUMBER).description("페이지 크기"),
                                fieldWithPath("value.pageable.sort").type(JsonFieldType.OBJECT).description("정렬 정보"),
                                fieldWithPath("value.pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("정렬이 비어 있는지 여부"),
                                fieldWithPath("value.pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("정렬이 되었는지 여부"),
                                fieldWithPath("value.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("정렬이 되지 않았는지 여부"),
                                fieldWithPath("value.pageable.offset").type(JsonFieldType.NUMBER).description("페이지 오프셋"),
                                fieldWithPath("value.pageable.paged").type(JsonFieldType.BOOLEAN).description("페이징 여부"),
                                fieldWithPath("value.pageable.unpaged").type(JsonFieldType.BOOLEAN).description("비페이징 여부"),
                                fieldWithPath("value.sort").type(JsonFieldType.OBJECT).description("정렬 정보"),
                                fieldWithPath("value.sort.empty").type(JsonFieldType.BOOLEAN).description("정렬이 비어 있는지 여부"),
                                fieldWithPath("value.sort.sorted").type(JsonFieldType.BOOLEAN).description("정렬이 되었는지 여부"),
                                fieldWithPath("value.sort.unsorted").type(JsonFieldType.BOOLEAN).description("정렬이 되지 않았는지 여부"),
                                fieldWithPath("value.totalPages").type(JsonFieldType.NUMBER).description("총 페이지 수"),
                                fieldWithPath("value.totalElements").type(JsonFieldType.NUMBER).description("총 요소 수"),
                                fieldWithPath("value.last").type(JsonFieldType.BOOLEAN).description("마지막 페이지 여부"),
                                fieldWithPath("value.number").type(JsonFieldType.NUMBER).description("현재 페이지 번호"),
                                fieldWithPath("value.size").type(JsonFieldType.NUMBER).description("페이지 크기"),
                                fieldWithPath("value.numberOfElements").type(JsonFieldType.NUMBER).description("현재 페이지의 요소 수"),
                                fieldWithPath("value.first").type(JsonFieldType.BOOLEAN).description("첫 페이지 여부"),
                                fieldWithPath("value.empty").type(JsonFieldType.BOOLEAN).description("비어 있는 페이지 여부")
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
                .sampleList(20);



        Pageable pageable = PageRequest.of(0, 20);
        Page<ProductDto> productPage = new PageImpl<>(productDtos, pageable, productDtos.size());

        if (sortType == Sort.VIEW_COUNT_PRODUCT_ORDER) {
            when(productService.getProductListOrderByViewCount(any(Pageable.class))).thenReturn(productPage);
        } else if (sortType == Sort.VIOLATION_PRODUCT_LIST) {
            when(productService.getViolationProducts(any(Pageable.class))).thenReturn(productPage);
        } else if (sortType == Sort.RECENT_PRODUCT_ORDER) {
            when(productService.getRecentProducts(any(Pageable.class))).thenReturn(productPage);
        }

        // when & then
        mockMvc.perform(get("/products/rank")
                        .queryParam("sortType", sortType.getOrderType()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.value.content[*].productNo").exists())
                .andExpect(jsonPath("$.value.content[*].productName").exists())
                .andExpect(jsonPath("$.value.content[*].viewCount").exists())
                .andExpect(jsonPath("$.value.content[*].reportNumber").exists())
                .andExpect(jsonPath("$.value.content[*].safetyStatus").exists())
                .andExpect(jsonPath("$.value.content[*].createdAt").exists())
                .andExpect(jsonPath("$.value.content[*].createdBy").exists())
                .andExpect(jsonPath("$.value.pageable.pageNumber").exists())
                .andExpect(jsonPath("$.value.pageable.pageSize").exists())
                .andExpect(jsonPath("$.value.pageable.sort.empty").exists())
                .andExpect(jsonPath("$.value.totalPages").exists())
                .andExpect(jsonPath("$.value.totalElements").exists())
                .andExpect(jsonPath("$.value.number").exists())
                .andExpect(jsonPath("$.value.size").exists())
                .andExpect(jsonPath("$.value.numberOfElements").exists())
                .andExpect(jsonPath("$.value.empty").exists())
                .andExpect(jsonPath("$.value.last").exists())
                .andExpect(jsonPath("$.value.first").exists())
                .andExpect(jsonPath("$.value.sort.empty").exists())
                .andDo(document("products/get-products-view-count-order",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        queryParameters(
                                parameterWithName("sortType").description("정렬 기준, 가능한 값: VIEW_COUNT_PRODUCT_ORDER, VIOLATION_PRODUCT_LIST, RECENT_PRODUCT_ORDER")
                        ),
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("응답 상태 코드"),
                                fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                subsectionWithPath("value").description("응답 데이터의 루트 객체"),
                                fieldWithPath("value.content[].productNo").type(JsonFieldType.NUMBER).description("제품 번호"),
                                fieldWithPath("value.content[].productName").type(JsonFieldType.STRING).description("제품명"),
                                fieldWithPath("value.content[].barcode").type(JsonFieldType.STRING).optional().description("바코드").ignored(),
                                fieldWithPath("value.content[].imageSource").type(JsonFieldType.STRING).optional().description("이미지 소스").ignored(),
                                fieldWithPath("value.content[].reportNumber").type(JsonFieldType.STRING).description("보고서 번호"),
                                fieldWithPath("value.content[].safetyStatus").type(JsonFieldType.STRING).description("안전 상태"),
                                fieldWithPath("value.content[].viewCount").type(JsonFieldType.NUMBER).description("조회수"),
                                fieldWithPath("value.content[].brand").type(JsonFieldType.STRING).description("브랜드").optional(),
                                fieldWithPath("value.content[].upperItem").type(JsonFieldType.STRING).description("상위 항목").optional(),
                                fieldWithPath("value.content[].createdAt").type(JsonFieldType.STRING).description("생성 날짜"),
                                fieldWithPath("value.content[].createdBy").type(JsonFieldType.STRING).description("생성자"),
                                fieldWithPath("value.content[].modifiedAt").type(JsonFieldType.STRING).optional().description("수정 날짜").ignored(),
                                fieldWithPath("value.content[].modifiedBy").type(JsonFieldType.STRING).optional().description("수정자").ignored(),
                                fieldWithPath("value.pageable.pageNumber").type(JsonFieldType.NUMBER).description("페이지 번호"),
                                fieldWithPath("value.pageable.pageSize").type(JsonFieldType.NUMBER).description("페이지 크기"),
                                fieldWithPath("value.pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("정렬이 비어 있는지 여부"),
                                fieldWithPath("value.pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("정렬이 되었는지 여부"),
                                fieldWithPath("value.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("정렬이 되지 않았는지 여부"),
                                fieldWithPath("value.pageable.offset").type(JsonFieldType.NUMBER).description("오프셋"),
                                fieldWithPath("value.pageable.paged").type(JsonFieldType.BOOLEAN).description("페이징 여부"),
                                fieldWithPath("value.pageable.unpaged").type(JsonFieldType.BOOLEAN).description("비페이징 여부"),
                                fieldWithPath("value.last").type(JsonFieldType.BOOLEAN).description("마지막 페이지 여부"),
                                fieldWithPath("value.totalPages").type(JsonFieldType.NUMBER).description("총 페이지 수"),
                                fieldWithPath("value.totalElements").type(JsonFieldType.NUMBER).description("총 요소 수"),
                                fieldWithPath("value.number").type(JsonFieldType.NUMBER).description("현재 페이지 번호"),
                                fieldWithPath("value.first").type(JsonFieldType.BOOLEAN).description("첫 페이지 여부"),
                                fieldWithPath("value.size").type(JsonFieldType.NUMBER).description("페이지 크기"),
                                fieldWithPath("value.numberOfElements").type(JsonFieldType.NUMBER).description("현재 페이지의 요소 수"),
                                fieldWithPath("value.empty").type(JsonFieldType.BOOLEAN).description("비어 있는 페이지 여부"),
                                fieldWithPath("value.sort.empty").type(JsonFieldType.BOOLEAN).description("정렬이 비어 있는지 여부"),
                                fieldWithPath("value.sort.sorted").type(JsonFieldType.BOOLEAN).description("정렬이 되었는지 여부"),
                                fieldWithPath("value.sort.unsorted").type(JsonFieldType.BOOLEAN).description("정렬이 되지 않았는지 여부")
                        )
                ));


        if (sortType == Sort.VIEW_COUNT_PRODUCT_ORDER) {
            verify(productService, times(1)).getProductListOrderByViewCount(pageable);
        } else if (sortType == Sort.VIOLATION_PRODUCT_LIST) {
            verify(productService, times(1)).getViolationProducts(pageable);
        } else if (sortType == Sort.RECENT_PRODUCT_ORDER) {
            verify(productService, times(1)).getRecentProducts(pageable);
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