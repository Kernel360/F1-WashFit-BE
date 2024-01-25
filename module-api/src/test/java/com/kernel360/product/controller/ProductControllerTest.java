package com.kernel360.product.controller;

import com.kernel360.common.ControllerTest;
import com.kernel360.product.dto.ProductDto;
import com.kernel360.product.entity.Product;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
        List<Product> products = fixtureMonkey.giveMeBuilder(Product.class)
                .sampleList(5);

        List<ProductDto> expectedDtos = products.stream()
                .map(ProductDto::from)
                .collect(Collectors.toList());

        //when
        when(productService.getProductList()).thenReturn(expectedDtos);

        // then
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.value", hasSize(5)));

        verify(productService, times(1)).getProductList();
    }

    @Test
    void 제품아이디로_제품조회요청이왔을때_200응답과_리스폰스가_잘반환되는지() throws Exception {
        // given
        Product mockProduct = fixtureMonkey.giveMeBuilder(Product.class)
                .set("productId", 1L)
                .sample();

        when(productService.getProductById(1L)).thenReturn(ProductDto.from(mockProduct));

        // when & then
        mockMvc.perform(get("/product/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
//                .andExpect(jsonPath("$.value.productName", is(mockProduct.getProductName())))
//                .andExpect(jsonPath("$.value.barcode", is(mockProduct.getBarcode())))
//                .andExpect(jsonPath("$.value.image", is(mockProduct.getImage())))
//                .andExpect(jsonPath("$.value.reportNumber", is(mockProduct.getReportNumber())))
//                .andExpect(jsonPath("$.value.productType", is(mockProduct.getProductType())))
//                .andExpect(jsonPath("$.value.companyName", is(mockProduct.getCompanyName())))
//                .andExpect(jsonPath("$.value.manufactureNation", is(mockProduct.getManufactureNation())))
//                .andExpect(jsonPath("$.value.safetyStatus", is(mockProduct.getSafetyStatus().toString())))
//                .andExpect(jsonPath("$.value.issuedDate", is(mockProduct.getIssuedDate().toString())))
//                .andExpect(jsonPath("$.value.viewCount", is(mockProduct.getViewCount())))
//                .andExpect(jsonPath("$.value.safetyInspectionStandard", is(mockProduct.getSafetyInspectionStandard())))
//                .andExpect(jsonPath("$.value.upperItem", is(mockProduct.getUpperItem())))
//                .andExpect(jsonPath("$.value.item", is(mockProduct.getItem())))
//                .andExpect(jsonPath("$.value.propose", is(mockProduct.getPropose())))
//                .andExpect(jsonPath("$.value.weight", is(mockProduct.getWeight())))
//                .andExpect(jsonPath("$.value.usage", is(mockProduct.getUsage())))
//                .andExpect(jsonPath("$.value.usagePrecaution", is(mockProduct.getUsagePrecaution())))
//                .andExpect(jsonPath("$.value.firstAid", is(mockProduct.getFirstAid())))
//                .andExpect(jsonPath("$.value.mainSubstance", is(mockProduct.getMainSubstance())))
//                .andExpect(jsonPath("$.value.allergicSubstance", is(mockProduct.getAllergicSubstance())))
//                .andExpect(jsonPath("$.value.otherSubstance", is(mockProduct.getOtherSubstance())))
//                .andExpect(jsonPath("$.value.preservative", is(mockProduct.getPreservative())))
//                .andExpect(jsonPath("$.value.surfactant", is(mockProduct.getSurfactant())))
//                .andExpect(jsonPath("$.value.fluorescentWhitening", is(mockProduct.getFluorescentWhitening())))
//                .andExpect(jsonPath("$.value.manufactureType", is(mockProduct.getManufactureType())))
//                .andExpect(jsonPath("$.value.manufactureMethod", is(mockProduct.getManufactureMethod())))
//                .andExpect(jsonPath("$.value.declareNo", is(mockProduct.getReportNumber())))
//                .andExpect(jsonPath("$.value.safetyStatus", is(mockProduct.getSafetyStatus())));

        verify(productService).getProductById(1L);

    }

    @Test
    void 키워드로_검색했을때_200코드와_리스폰스가_잘반환되는지() throws Exception {
        // given
        List<ProductDto> products = new ArrayList<>();
        String keyword = "sample";
        for (int i = 0; i < 5; i++) {
            ProductDto productDto = fixtureMonkey.giveMeBuilder(ProductDto.class)
                    .set("productName", keyword + i)
                    .sample();
            products.add(productDto);
        }

        //when
        when(productService.getProductListByKeyword(keyword)).thenReturn(products);

        //then
        mockMvc.perform(get("/products/search").param("keyword", keyword))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value", hasSize(5)));

        verify(productService, times(1)).getProductListByKeyword(keyword);

    }

}