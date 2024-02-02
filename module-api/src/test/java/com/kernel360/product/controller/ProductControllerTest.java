package com.kernel360.product.controller;

import com.kernel360.common.ControllerTest;
import com.kernel360.product.dto.ProductDetailDto;
import com.kernel360.product.dto.ProductDto;
import com.kernel360.product.entity.Product;
import com.kernel360.product.entity.SafetyStatus;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static com.kernel360.common.utils.RestDocumentUtils.getDocumentRequest;
import static com.kernel360.common.utils.RestDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.hamcrest.Matchers.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;


@AutoConfigureWebMvc
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
                .setNotNull("imageSource")
                .setNotNull("reportNumber")
                .setNotNull("safetyStatus")
                .setNotNull("viewCount")
                .setNotNull("brand")
                .setNotNull("upperItem")
                .setNotNull("createdAt")
                .setNotNull("createdBy")
                .setNotNull("modifiedAt")
                .setNotNull("modifiedBy")
                .sampleList(5).stream()
                .map(ProductDto::from)
                .toList();

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
                                fieldWithPath("value[].productNo").description("제품번호").optional(),
                                fieldWithPath("value[].productName").description("제품명").optional(),
                                fieldWithPath("value[].barcode").description("바코드").optional(),
                                fieldWithPath("value[].imageSource").description("이미지 소스").optional(),
                                fieldWithPath("value[].reportNumber").description("보고 번호").optional(),
                                fieldWithPath("value[].safetyStatus").description("안전 상태").optional(),
                                fieldWithPath("value[].viewCount").description("조회수").optional(),
                                fieldWithPath("value[].brand").description("브랜드").optional(),
                                fieldWithPath("value[].upperItem").description("상위 분류").optional(),
                                fieldWithPath("value[].createdAt").description("생성 날짜").optional(),
                                fieldWithPath("value[].createdBy").description("생성자").optional(),
                                fieldWithPath("value[].modifiedAt").description("수정된 날짜").optional(),
                                fieldWithPath("value[].modifiedBy").description("수정자").optional()
                        )));

        verify(productService, times(1)).getProductList();
    }


    @Test
    void 제품아이디로_제품조회요청이왔을때_200응답과_리스폰스가_잘반환되는지() throws Exception {
        // given
        Product mockProduct = fixtureMonkey.giveMeBuilder(Product.class)
                .set("productNo", 1L) // Assuming you want to explicitly set some values
                .setNotNull("productName")
                .setNotNull("companyName")
                .setNotNull("productType")
                .setNotNull("upperItem")
                .setNotNull("manufactureType")
                .setNotNull("manufactureMethod")
                .setNotNull("weight")
                .setNotNull("reportNumber")
                .setNotNull("mainSubstance")
                .setNotNull("brand")
                .set("grade", 3.5)
                .set("reviewCnt", 10L)
                .setNotNull("viewCount")
                .setNotNull("createdAt")
                .setNotNull("createdBy")
                .setNotNull("modifiedAt")
                .setNotNull("modifiedBy")
                .sample();

        ProductDetailDto productDetailDto = ProductDetailDto.from(mockProduct);

        when(productService.getProductDetailById(1L)).thenReturn(productDetailDto);

        // when & then
        mockMvc.perform(get("/product/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.value.productNo", notNullValue()))
                .andExpect(jsonPath("$.value.productName", notNullValue()))
                .andExpect(jsonPath("$.value.companyName", notNullValue()))
                .andExpect(jsonPath("$.value.productType", notNullValue()))
                .andExpect(jsonPath("$.value.upperItem", notNullValue()))
                .andExpect(jsonPath("$.value.manufactureType", notNullValue()))
                .andExpect(jsonPath("$.value.manufactureMethod", notNullValue()))
                .andExpect(jsonPath("$.value.weight", notNullValue()))
                .andExpect(jsonPath("$.value.reportNumber", notNullValue()))
                .andExpect(jsonPath("$.value.mainSubstance", notNullValue()))
                .andExpect(jsonPath("$.value.brand", notNullValue()))
                .andExpect(jsonPath("$.value.grade", notNullValue()))
                .andExpect(jsonPath("$.value.reviewCnt", notNullValue()))
                .andExpect(jsonPath("$.value.viewCount", notNullValue()))
                .andExpect(jsonPath("$.value.createdAt", notNullValue()))
                .andExpect(jsonPath("$.value.createdBy", notNullValue()))
                .andExpect(jsonPath("$.value.modifiedAt", notNullValue()))
                .andExpect(jsonPath("$.value.modifiedBy", notNullValue()))
                .andDo(document("product-id/get-product-id",
                        pathParameters(
                                parameterWithName("id").description("제품 ID")
                        ),
                        responseFields(
                                fieldWithPath("status").description("상태 코드"),
                                fieldWithPath("message").description("응답 메세지"),
                                fieldWithPath("code").description("비지니스 코드"),
                                fieldWithPath("value").description("제품 상세 정보"),
                                fieldWithPath("value.productNo").description("제품번호"),
                                fieldWithPath("value.productName").description("제품명"),
                                fieldWithPath("value.companyName").description("제조사"),
                                fieldWithPath("value.productType").description("제품 유형"),
                                fieldWithPath("value.upperItem").description("상위 분류"),
                                fieldWithPath("value.manufactureType").description("제조 타입"),
                                fieldWithPath("value.manufactureMethod").description("제조 방법"),
                                fieldWithPath("value.weight").description("무게"),
                                fieldWithPath("value.reportNumber").description("보고 번호"),
                                fieldWithPath("value.mainSubstance").description("주요 성분"),
                                fieldWithPath("value.brand").description("브랜드"),
                                fieldWithPath("value.grade").description("평점"),
                                fieldWithPath("value.reviewCnt").description("리뷰 수"),
                                fieldWithPath("value.viewCount").description("조회수"),
                                fieldWithPath("value.createdAt").description("생성 날짜"),
                                fieldWithPath("value.createdBy").description("생성자"),
                                fieldWithPath("value.modifiedAt").description("수정 날짜"),
                                fieldWithPath("value.modifiedBy").description("수정자")
                        )));
        verify(productService).getProductDetailById(1L);
    }


    @RepeatedTest(100)
    void 검색키워드로_제품조회요청시_200응답과_데이터가_잘_반환되는지() throws Exception {
        // given
        List<ProductDto> products = new ArrayList<>();
        String keyword = "더 클래스";

        for (int i = 0; i < 3; i++) {
            ProductDto productDto = fixtureMonkey.giveMeBuilder(ProductDto.class)
                    .set("productName", keyword + " " + (i + 1))
                    .setNotNull("productNo")
                    .setNull("barcode")
                    .setNull("imageSource")
                    .setNotNull("reportNumber")
                    .set("safetyStatus", SafetyStatus.CONCERN)
                    .set("viewCount", 0)
                    .setNotNull("brand")
                    .setNotNull("upperItem")
                    .setNotNull("createdAt")
                    .setNotNull("createdBy")
                    .setNotNull("modifiedAt")
                    .setNotNull("modifiedBy")
                    .sample();
            products.add(productDto);
        }

        when(productService.getProductListByKeyword(keyword)).thenReturn(products);

//         when & then
        mockMvc.perform(get("/products/search").param("keyword", keyword))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.code").value("PMB002"))
                .andExpect(jsonPath("$.message").value("제품정보 조회 성공"))
                .andExpect(jsonPath("$.value", hasSize(3)))
                .andExpect(jsonPath("$.value[*].productNo", notNullValue()))
                .andExpect(jsonPath("$.value[*].productName", notNullValue()))
                .andExpect(jsonPath("$.value[*].reportNumber", notNullValue()))
                .andExpect(jsonPath("$.value[*].safetyStatus", notNullValue()))
                .andExpect(jsonPath("$.value[*].viewCount", notNullValue()))
                .andDo(document("products-search/get-products-by-search",
                        queryParameters(
                              parameterWithName("keyword").description("키워드")
                      ),
                        responseFields(
                                fieldWithPath("status").description("상태 코드"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("code").description("비즈니스 코드"),
                                fieldWithPath("value").description("제품 목록"),
                                fieldWithPath("value[].productNo").description("제품 번호"),
                                fieldWithPath("value[].productName").description("제품 이름"),
                                fieldWithPath("value[].barcode").description("바코드").optional(),
                                fieldWithPath("value[].imageSource").description("이미지 소스").optional(),
                                fieldWithPath("value[].reportNumber").description("보고서 번호"),
                                fieldWithPath("value[].safetyStatus").description("안전 상태"),
                                fieldWithPath("value[].viewCount").description("조회수"),
                                fieldWithPath("value[].brand").description("브랜드"),
                                fieldWithPath("value[].upperItem").description("상위 항목 카테고리"),
                                fieldWithPath("value[].createdAt").description("생성 날짜"),
                                fieldWithPath("value[].createdBy").description("생성자"),
                                fieldWithPath("value[].modifiedAt").description("수정 날짜"),
                                fieldWithPath("value[].modifiedBy").description("수정자")
                        )));

        verify(productService, times(1)).getProductListByKeyword(keyword);
    }
}
