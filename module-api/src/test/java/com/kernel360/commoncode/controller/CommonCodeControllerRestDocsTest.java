package com.kernel360.commoncode.controller;

import static com.kernel360.common.utils.RestDocumentUtils.getDocumentRequest;
import static com.kernel360.common.utils.RestDocumentUtils.getDocumentResponse;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.beneathPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kernel360.common.ControllerTest;
import com.kernel360.commoncode.dto.CommonCodeDto;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

// FIXME :: 삭제 예정 파일입니다
class CommonCodeControllerRestDocsTest extends ControllerTest {

    @Test
    void commmonCodeSearch() throws Exception {
        //given
        List<CommonCodeDto> responseList = Arrays.asList(
                CommonCodeDto.of(
                        11L,
                        "Sedan",
                        10L,
                        "cartype",
                        1,
                        true,
                        "세단",
                        LocalDateTime.now(),
                        "admin",
                        null,
                        null),
                CommonCodeDto.of(
                        12L,
                        "Hatchback",
                        10L,
                        "cartype",
                        2,
                        true,
                        "해치백",
                        LocalDateTime.now(),
                        "admin",
                        null,
                        null)
        );

        String pathVariable = "color";
        given(commonCodeService.getCodes(pathVariable)).willReturn(responseList);


        //when
        ResultActions result = this.mockMvc.perform(
                get("/commoncode/{codeName}", pathVariable));


        //then
        //pathParameters, pathParameters, requestFields, responseFields는 필요 시 각각 작성
        result.andExpect(status().isOk())
              .andDo(document(
                      "commoncode/get-common-codes",
                      getDocumentRequest(),
                      getDocumentResponse(),
                      pathParameters(
                              parameterWithName("codeName").description("코드명")
                      ),
//                      queryParameters(
//                              parameterWithName("size").description("size").optional(),
//                              parameterWithName("page").description("page").optional()
//                      ),
//                      requestFields(
//                              fieldWithPath("codeName").type(JsonFieldType.STRING).description("코드명"),
//                              fieldWithPath("upperName").type(JsonFieldType.STRING).description("상위 코드명").optional()
//                      ),
//                      responseFields(
//                              fieldWithPath("codeNo").type(JsonFieldType.NUMBER).description("코드번호"),
//                      )
                      responseFields(beneathPath("value").withSubsectionId("value"),
                              fieldWithPath("codeNo").type(JsonFieldType.NUMBER).description("코드번호"),
                              fieldWithPath("codeName").type(JsonFieldType.STRING).description("코드명"),
                              fieldWithPath("upperNo").type(JsonFieldType.NUMBER).description("상위 코드번호").optional(),
                              fieldWithPath("upperName").type(JsonFieldType.STRING).description("상위 코드명").optional(),
                              fieldWithPath("sortOrder").type(JsonFieldType.NUMBER).description("정렬순서"),
                              fieldWithPath("isUsed").type(JsonFieldType.BOOLEAN).description("사용여부"),
                              fieldWithPath("description").type(JsonFieldType.STRING).description("설명"),
                              fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성일시"),
                              fieldWithPath("createdBy").type(JsonFieldType.STRING).description("생성자"),
                              fieldWithPath("modifiedAt").type(JsonFieldType.STRING).description("수정일시").optional(),
                              fieldWithPath("modifiedBy").type(JsonFieldType.STRING).description("수정자").optional()
                      )
              ));
    }
}