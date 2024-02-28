package com.kernel360.auth.controller;

import static com.kernel360.common.utils.RestDocumentUtils.getDocumentRequest;
import static com.kernel360.common.utils.RestDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kernel360.auth.dto.AuthDto;
import com.kernel360.common.ControllerTest;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MvcResult;

@AutoConfigureWebMvc
class AuthControllerTest extends ControllerTest {
    @Test
    void 토큰_갱신요청이_왔을때_리스폰스로_상태코드201과_갱신토큰이_잘_보내지는지() throws Exception {
        String requestToken = "token";
        String resultToken = "reissuanceToken";
        AuthDto dto = new AuthDto(resultToken);

        when(acceptInterceptor.preHandle(any(), any(), any())).thenReturn(true);
        when(authService.generateTokenAndSaveAuth(any())).thenReturn(resultToken);

        try (MockedStatic<AuthDto> mocked = Mockito.mockStatic(AuthDto.class)) {
            mocked.when(() -> AuthDto.of(resultToken)).thenReturn(dto);

            MvcResult actions = mockMvc.perform(get("/auth/reissuanceJWT")
                                               .header("Authorization", requestToken))
                                       .andExpect(status().isCreated())
                                       .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                       .andExpect(jsonPath("$.status").value(201))
                                       .andExpect(jsonPath("$.code").value("BAC001"))
                                       .andExpect(jsonPath("$.message").value("JWT 토큰 재발급 성공"))
                                       .andDo(document("auth/reissuanceJWT",
                                               getDocumentRequest(),
                                               getDocumentResponse(),
                                               responseFields(
                                                       fieldWithPath("status").description("상태 코드"),
                                                       fieldWithPath("message").description("응답 메시지"),
                                                       fieldWithPath("code").description("비즈니스 코드"),
                                                       fieldWithPath("value.jwtToken").type(JsonFieldType.STRING)
                                                                                      .description("JWT 토큰")
                                               )))
                                       .andReturn();
        }
    }
}