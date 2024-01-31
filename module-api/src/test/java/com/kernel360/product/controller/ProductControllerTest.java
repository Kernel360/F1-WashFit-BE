package com.kernel360.product.controller;

import com.kernel360.common.ControllerTest;
import com.kernel360.product.dto.ProductDto;
import com.kernel360.product.entity.Product;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static com.kernel360.common.utils.RestDocumentUtils.getDocumentRequest;
import static com.kernel360.common.utils.RestDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;


class ProductControllerTest extends ControllerTest {
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
    void 제품목록요청_왔을때_200응답과_리스폰스가_잘반환되는지() throws Exception {
        // given
        List<ProductDto> expectedDtos = fixtureMonkey.giveMeBuilder(Product.class)
                .setNotNull("productNo")
                .setNotNull("productName")
                .setNotNull("barcode")
                .setNotNull("reportNumber")
                .setNotNull("safetyStatus")
                .setNotNull("viewCount")
                .setNotNull("createdAt")
                .setNotNull("createdBy")
                .setNotNull("modifiedAt")
                .setNotNull("modifiedBy")
                .sampleList(5).stream()
                .map(ProductDto::from)
                .collect(Collectors.toList());

        when(productService.getProductList()).thenReturn(expectedDtos);

        // then
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.value", hasSize(5)))
                .andDo(document("products-list/get-products-list",
                        responseFields(
                                fieldWithPath("status").description("상태 코드"),
                                fieldWithPath("message").description("응답 메세지"),
                                fieldWithPath("code").description("비지니스 코드"),
                                fieldWithPath("value").description("제품 리스트"),
                                fieldWithPath("value[].productNo").description("제품번호"),
                                fieldWithPath("value[].productName").description("제품명"),
                                fieldWithPath("value[].barcode").description("바코드"),
                                fieldWithPath("value[].imageSource").description("이미지 소스"),
                                fieldWithPath("value[].reportNumber").description("보고 번호"),
                                fieldWithPath("value[].safetyStatus").description("안전 상태"),
                                fieldWithPath("value[].viewCount").description("조회수"),
                                fieldWithPath("value[].createdAt").description("생성 날짜"),
                                fieldWithPath("value[].createdBy").description("생성자"),
                                fieldWithPath("value[].modifiedAt").optional().description("수정된 날짜"),
                                fieldWithPath("value[].modifiedBy").optional().description("수정자")
                        )));

        verify(productService, times(1)).getProductList();
    }


    @Test
    void 제품아이디로_제품조회요청이왔을때_200응답과_리스폰스가_잘반환되는지() throws Exception {
        // given
        Product mockProduct = fixtureMonkey.giveMeBuilder(Product.class)
                .set("productNo", 1L)
                .setNotNull("imageSource")
                .sample();

        ProductDto productDto = ProductDto.from(mockProduct);

        when(productService.getProductById(1L)).thenReturn(productDto);

        // when & then
        mockMvc.perform(get("/product/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.value.productNo", is(1)))
                .andExpect(jsonPath("$.value.productName", is(mockProduct.getProductName())))
                .andExpect(jsonPath("$.value.barcode", is(mockProduct.getBarcode())))
                .andExpect(jsonPath("$.value.imageSource", is(mockProduct.getImage())))
                .andExpect(jsonPath("$.value.reportNumber", is(mockProduct.getReportNumber())))
                .andExpect(jsonPath("$.value.safetyStatus", is(mockProduct.getSafetyStatus().toString())))
                .andExpect(jsonPath("$.value.viewCount", is(mockProduct.getViewCount())))
                .andDo(document(
                        "product-id/get-product-id",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태 코드"),
                                fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답메세지"),
                                fieldWithPath("value").type(JsonFieldType.ARRAY).description("제품 리스트"),
                                fieldWithPath("value[].productNo").type(JsonFieldType.NUMBER).description("제품 ID"),
                                fieldWithPath("value[].productName").type(JsonFieldType.STRING).description("제품명"),
                                fieldWithPath("value[].barcode").type(JsonFieldType.STRING).optional().description("바코드"),
                                fieldWithPath("value[].imageSource").type(JsonFieldType.STRING).optional().description("이미지 소스"),
                                fieldWithPath("value[].reportNumber").type(JsonFieldType.STRING).description("신고 번호"),
                                fieldWithPath("value[].safetyStatus").type(JsonFieldType.STRING).description("안전 상태"),
                                fieldWithPath("value[].viewCount").type(JsonFieldType.NUMBER).description("조회수"),
                                fieldWithPath("value[].createdAt").type(JsonFieldType.STRING).description("생성날짜"),
                                fieldWithPath("value[].createdBy").type(JsonFieldType.STRING).description("생성자"),
                                fieldWithPath("value[].modifiedAt").type(JsonFieldType.STRING).optional().description("수정날짜"),
                                fieldWithPath("value[].modifiedBy").type(JsonFieldType.STRING).optional().description("수정자")
                        )
                ));

        verify(productService).getProductById(1L);
    }

    @Test
    void 검색키워드로_제품조회요청시_200응답과_데이터가_잘_반환되는지() throws Exception {
        // given
        List<ProductDto> products = new ArrayList<>();
        String keyword = "productName";

        for (int i = 0; i < 5; i++) {
            ProductDto productDto = fixtureMonkey.giveMeBuilder(ProductDto.class)
                    .set("productName", keyword + i)
                    .setNotNull("productNo")
                    .setNull("barcode")
                    .setNull("imageSource")
                    .setNotNull("reportNumber")
                    .setNotNull("safetyStatus")
                    .setNotNull("viewCount")
                    .setNotNull("createAt")
                    .setNotNull("createBy")
                    .setNotNull("modifiedAt")
                    .setNotNull("modifiedBy")
                    .sample();
            products.add(productDto);
        }

        when(productService.getProductListByKeyword(keyword)).thenReturn(products);

        // when & then
        mockMvc.perform(get("/products/search").param("keyword", keyword))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.value", hasSize(5)))
                .andExpect(jsonPath("$.value[0].productNo").isNotEmpty())
                .andExpect(jsonPath("$.value[0].productName").isNotEmpty())
                .andExpect(jsonPath("$.value[0].barcode").isEmpty())
                .andExpect(jsonPath("$.value[0].imageSource").isEmpty())
                .andExpect(jsonPath("$.value[0].reportNumber").isNotEmpty())
                .andExpect(jsonPath("$.value[0].safetyStatus").isNotEmpty())
                .andExpect(jsonPath("$.value[0].viewCount").isNotEmpty())
                .andExpect(jsonPath("$.value[0].createdAt").isNotEmpty())
                .andExpect(jsonPath("$.value[0].createdBy").isNotEmpty())
                .andExpect(jsonPath("$.value[0].modifiedAt").isNotEmpty())
                .andExpect(jsonPath("$.value[0].modifiedBy").isNotEmpty())
                .andDo(document("products-search/get-products-by-keyword",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태 코드"),
                                fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답메세지"),
                                fieldWithPath("value").type(JsonFieldType.ARRAY).description("제품 리스트"),
                                fieldWithPath("value[].productNo").type(JsonFieldType.NUMBER).description("제품 ID"),
                                fieldWithPath("value[].productName").type(JsonFieldType.STRING).description("제품명"),
                                fieldWithPath("value[].barcode").type(JsonFieldType.STRING).optional().description("바코드"),
                                fieldWithPath("value[].imageSource").type(JsonFieldType.STRING).optional().description("이미지 소스"),
                                fieldWithPath("value[].reportNumber").type(JsonFieldType.STRING).description("신고 번호"),
                                fieldWithPath("value[].safetyStatus").type(JsonFieldType.STRING).description("안전 상태"),
                                fieldWithPath("value[].viewCount").type(JsonFieldType.NUMBER).description("조회수"),
                                fieldWithPath("value[].createdAt").type(JsonFieldType.STRING).description("생성날짜"),
                                fieldWithPath("value[].createdBy").type(JsonFieldType.STRING).description("생성자"),
                                fieldWithPath("value[].modifiedAt").type(JsonFieldType.STRING).optional().description("수정날짜"),
                                fieldWithPath("value[].modifiedBy").type(JsonFieldType.STRING).optional().description("수정자")
                        )));


        verify(productService, times(1)).getProductListByKeyword(keyword);

    }
}