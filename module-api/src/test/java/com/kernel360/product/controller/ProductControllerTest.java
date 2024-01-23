package com.kernel360.product.controller;

import com.kernel360.product.dto.ProductDto;
import com.kernel360.product.entity.Product;
import com.kernel360.product.repository.ProductRepository;
import com.kernel360.product.service.ProductService;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.*;
import net.jqwik.api.Arbitraries;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    private MockMvc mockMvc;
    private FixtureMonkey fixtureMonkey;
    @MockBean
    private ProductService productService;
    @MockBean
    private ProductRepository productRepository;

    @BeforeEach
    void 준비(WebApplicationContext webApplicationContext) {
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

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
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
                .andExpect(jsonPath("$", hasSize(5)));

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
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productName", is(mockProduct.getProductName())))
                .andExpect(jsonPath("$.barcode", is(mockProduct.getBarcode())))
                .andExpect(jsonPath("$.image", is(mockProduct.getImage())))
                .andExpect(jsonPath("$.reportNumber", is(mockProduct.getReportNumber())))
                .andExpect(jsonPath("$.productType", is(mockProduct.getProductType())))
                .andExpect(jsonPath("$.companyName", is(mockProduct.getCompanyName())))
                .andExpect(jsonPath("$.manufactureNation", is(mockProduct.getManufactureNation())))
                .andExpect(jsonPath("$.safetyStatus", is(mockProduct.getSafetyStatus().toString())))
                .andExpect(jsonPath("$.issuedDate", is(mockProduct.getIssuedDate().toString())))
                .andExpect(jsonPath("$.viewCount", is(mockProduct.getViewCount())))
                .andExpect(jsonPath("$.safetyInspectionStandard", is(mockProduct.getSafetyInspectionStandard())))
                .andExpect(jsonPath("$.upperItem", is(mockProduct.getUpperItem())))
                .andExpect(jsonPath("$.item", is(mockProduct.getItem())))
                .andExpect(jsonPath("$.propose", is(mockProduct.getPropose())))
                .andExpect(jsonPath("$.weight", is(mockProduct.getWeight())))
                .andExpect(jsonPath("$.usage", is(mockProduct.getUsage())))
                .andExpect(jsonPath("$.usagePrecaution", is(mockProduct.getUsagePrecaution())))
                .andExpect(jsonPath("$.firstAid", is(mockProduct.getFirstAid())))
                .andExpect(jsonPath("$.mainSubstance", is(mockProduct.getMainSubstance())))
                .andExpect(jsonPath("$.allergicSubstance", is(mockProduct.getAllergicSubstance())))
                .andExpect(jsonPath("$.otherSubstance", is(mockProduct.getOtherSubstance())))
                .andExpect(jsonPath("$.preservative", is(mockProduct.getPreservative())))
                .andExpect(jsonPath("$.surfactant", is(mockProduct.getSurfactant())))
                .andExpect(jsonPath("$.fluorescentWhitening", is(mockProduct.getFluorescentWhitening())))
                .andExpect(jsonPath("$.manufactureType", is(mockProduct.getManufactureType())))
                .andExpect(jsonPath("$.manufactureMethod", is(mockProduct.getManufactureMethod())));

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
                .andExpect(jsonPath("$", hasSize(5)));

        verify(productService, times(1)).getProductListByKeyword(keyword);

    }

}