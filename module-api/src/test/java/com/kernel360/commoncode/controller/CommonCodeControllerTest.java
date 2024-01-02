package com.kernel360.commoncode.controller;

import com.kernel360.commoncode.service.CommonCodeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(CommonCodeController.class)
class CommonCodeControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext context;

    @MockBean
    private CommonCodeService commonCodeService;

    @BeforeEach
    public void setup(){
        this.mvc = MockMvcBuilders.webAppContextSetup(context)
                                                            .addFilter(new CharacterEncodingFilter("UTF-8",true))
                                                            .alwaysDo(print())
                                                            .build();
    }

    @Test
    @DisplayName("getCommonCode :: 공통코드 조회 요청")
    public void 상위코드명을_인수로_받는_공통코드_목록_조회() throws Exception {
        String pathVariable = "color";

        mvc.perform(MockMvcRequestBuilders.get("/commoncode/{codeName}", pathVariable))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andReturn();
    }
}