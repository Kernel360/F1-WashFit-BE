package com.kernel360.commoncode.controller;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.kernel360.common.ControllerTest;
import com.kernel360.commoncode.dto.CommonCodeDto;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.kernel360.common.utils.RestDocumentUtils.getDocumentRequest;
import static com.kernel360.common.utils.RestDocumentUtils.getDocumentResponse;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        result.andExpect(status().isOk())
              .andDo(document(
                      "commoncode/get-common-codes",
                      getDocumentRequest(),
                      getDocumentResponse(),
                      resource(ResourceSnippetParameters
                              .builder()
                              .tag("CommonCode API")
                              .summary("공통코드 조회")
                              .pathParameters(
                                      parameterWithName("codeName").description("코드명")
                              )
                              .responseFields(
                                      fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태"),
                                      fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
                                      fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                      fieldWithPath("value").type(JsonFieldType.ARRAY).description("응답 데이터 리스트"),
                                      fieldWithPath("value[].codeNo").type(JsonFieldType.NUMBER).description("코드번호"),
                                      fieldWithPath("value[].codeName").type(JsonFieldType.STRING).description("코드명"),
                                      fieldWithPath("value[].upperNo").type(JsonFieldType.NUMBER).description("상위 코드번호").optional(),
                                      fieldWithPath("value[].upperName").type(JsonFieldType.STRING).description("상위 코드명").optional(),
                                      fieldWithPath("value[].sortOrder").type(JsonFieldType.NUMBER).description("정렬순서"),
                                      fieldWithPath("value[].isUsed").type(JsonFieldType.BOOLEAN).description("사용여부"),
                                      fieldWithPath("value[].description").type(JsonFieldType.STRING).description("설명"),
                                      fieldWithPath("value[].createdAt").type(JsonFieldType.STRING).description("생성일시"),
                                      fieldWithPath("value[].createdBy").type(JsonFieldType.STRING).description("생성자"),
                                      fieldWithPath("value[].modifiedAt").type(JsonFieldType.STRING).description("수정일시").optional(),
                                      fieldWithPath("value[].modifiedBy").type(JsonFieldType.STRING).description("수정자").optional()
                              )
                              .requestSchema(Schema.schema("공통코드 조회"))
                              .responseSchema(Schema.schema("공통코드 조회"))
                              .build()
                      )));
    }
}