package com.kernel360.commoncode.controller;

import com.kernel360.common.ControllerTest;
import com.kernel360.commoncode.dto.CommonCodeDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.BDDMockito.given;

class CommonCodeControllerTest extends ControllerTest {

    @Test
    @DisplayName("getCommonCode :: 공통코드 조회 요청")
    void 상위코드명을_인수로_받는_공통코드_목록_조회() throws Exception {

        String pathVariable = "color";
        Long codeNo = 1L;
        String codeName = "테스트용 코드";
        Integer upperNo = null;
        String upperName = null;
        Integer sortOrder = 1;
        Boolean isUsed = true;
        String description = "테스트용 코드값";
        LocalDate createdAt = LocalDate.now();
        String createdBy = "admin";
        LocalDate modifiedAt = null;
        String modifiedBy = null;

        CommonCodeDto item = CommonCodeDto.of(codeNo,codeName,upperNo,upperName,sortOrder,isUsed,description,createdAt,createdBy,modifiedAt,modifiedBy);

        given(commonCodeServiceImpl.getCodes(pathVariable)).willReturn(Collections.singletonList(item));

        mockMvc.perform(MockMvcRequestBuilders.get("/commoncode/{codeName}", pathVariable))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andReturn();
    }
}