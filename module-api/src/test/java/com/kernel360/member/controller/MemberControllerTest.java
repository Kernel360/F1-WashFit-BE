package com.kernel360.member.controller;

import static com.kernel360.common.utils.RestDocumentUtils.getDocumentRequest;
import static com.kernel360.common.utils.RestDocumentUtils.getDocumentResponse;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kernel360.common.ControllerTest;
import com.kernel360.member.dto.MemberCredentialDto;
import com.kernel360.member.dto.MemberDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


class MemberControllerTest extends ControllerTest {

    @Test
    @DisplayName("회원가입요청")
    void 회원가입요청() throws Exception {

        /** given 목데이터 세팅 **/
        MemberDto memberDto = MemberDto.of("testID", "gunsight777@naver.com", "testPassword", "MALE", "30");

        ObjectMapper objectMapper = new ObjectMapper();
        String param = objectMapper.writeValueAsString(memberDto);

        /** then **/
        mockMvc.perform(MockMvcRequestBuilders.post("/member/join") //이 다음 restful
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(param))
               .andExpect(MockMvcResultMatchers.status().isCreated()) //기대 결과 상태값
               .andReturn();
    }

    @Test
    @DisplayName("아이디_비밀번호를_받아_로그인_성공시_200반환")
    void 로그인() throws Exception {
        // given
        MemberDto memberDto = MemberDto.of("testID", "testPassword");
        MemberDto memberInfo = new MemberDto(1L,
                "test01",
                "kernel360@kernel360.com",
                "",
                null,
                null,
                LocalDateTime.now(),
                "test01",
                null,
                null,
                "dummyToken"
        );
        MockHttpServletRequest request = new MockHttpServletRequest();

        // when
        ObjectMapper objectMapper = new ObjectMapper();
        String param = objectMapper.writeValueAsString(memberDto);

        given(memberService.login(memberDto, request)).willReturn(memberInfo);

        /** then **/
        mockMvc.perform(MockMvcRequestBuilders.post("/member/login")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(param))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andReturn();

    }

    @Test
    @DisplayName("회원가입시_아이디_중복_검사하여_결과_및_200반환")
    void 회원가입_아이디_중복_검사() throws Exception {

        /** given 목데이터 세팅 **/
        String id = "test01";
        String message = "This is a test message.";

        /** then **/
        mockMvc.perform(MockMvcRequestBuilders.get("/member/duplicatedCheckId/{id}", id)
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(message))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andReturn();
    }

    @Test
    @DisplayName("회원가입시_이메일_중복_검사하여_결과_및_200반환")
    void 회원가입_이메일_중복_검사() throws Exception {

        /** given 목데이터 세팅 **/
        String email = "kernel360@kernel360.com";
        String message = "This is a test message.";

        /** then **/
        mockMvc.perform(MockMvcRequestBuilders.get("/member/duplicatedCheckEmail/{email}", email)
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(message))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andReturn();
    }


    @Test
    @DisplayName("이메일을 입력 받아 아이디 찾기 이메일 발송")
    void 아이디_찾기_이메일_발송_검사() throws Exception {
        /** given 목데이터 세팅 **/
        MemberCredentialDto dto = MemberCredentialDto.of(null, "kernel360@gmail.com", null, null);
        MemberDto memberDto = MemberDto.of("testMemberId", "testPassword001");

        given(memberService.findByEmail(dto.email())).willReturn(memberDto);

        ObjectMapper objectMapper = new ObjectMapper();
        String dtoAsString = objectMapper.writeValueAsString(dto);

        /** then **/
        mockMvc.perform(MockMvcRequestBuilders.post("/member/find-memberId")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(dtoAsString))
               .andExpect(MockMvcResultMatchers.status().isOk()).andDo(document(
                       "member/find-memberId", getDocumentRequest(), getDocumentResponse(),
                       requestFields(
                               fieldWithPath("email").type(JsonFieldType.STRING).description("회원가입시 입력한 이메일"),
                               fieldWithPath("token").type(JsonFieldType.NULL).description("비밀번호 재설정 UUID 토큰(사용하지 않음)"),
                               fieldWithPath("id").type(JsonFieldType.NULL).description("회원 아이디(사용하지 않음)"),
                               fieldWithPath("password").type(JsonFieldType.NULL).description("변경할 비밀번호(사용하지 않음)")
                       ),
                       responseFields(
                               fieldWithPath("status").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"),
                               fieldWithPath("code").type(JsonFieldType.STRING).description("비즈니스 코드"),
                               fieldWithPath("message").type(JsonFieldType.STRING).description("상세 메시지"),
                               fieldWithPath("value").type(JsonFieldType.NULL).description("JSON BODY 데이터")
                       )));
    }

    @Test
    @DisplayName("회원 아이디를 입력받아 비밀번호 재설정 이메일 발송에 성공한다")
    void 회원_아이디로_비밀번호_재설정_이메일_발송_검사() throws Exception {
        // given
        MemberCredentialDto credentialDto = MemberCredentialDto.of(null, null, "kernel360", null);
        MemberDto memberDto = MemberDto.of("testMemberId", "testPassword001");

        given(memberService.findByMemberId(credentialDto.memberId())).willReturn(memberDto);
        given(findCredentialService.generatePasswordResetPageUri(memberDto)).willReturn("테스트 URI");

        ObjectMapper objectMapper = new ObjectMapper();
        String dtoAsString = objectMapper.writeValueAsString(credentialDto);

        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/member/find-password")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(dtoAsString))
               .andExpect(MockMvcResultMatchers.status().isOk()).andDo(document(
                       "member/find-password", getDocumentRequest(), getDocumentResponse(),
                       requestFields(
                               fieldWithPath("email").type(JsonFieldType.NULL).description("회원가입시 입력한 이메일(사용하지 않음)"),
                               fieldWithPath("token").type(JsonFieldType.NULL).description("비밀번호 재설정 UUID 토큰(사용하지 않음)"),
                               fieldWithPath("id").type(JsonFieldType.STRING).description("회원 아이디"),
                               fieldWithPath("password").type(JsonFieldType.NULL).description("변경할 비밀번호(사용하지 않음)")
                       ),
                       responseFields(
                               fieldWithPath("status").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"),
                               fieldWithPath("code").type(JsonFieldType.STRING).description("비즈니스 코드"),
                               fieldWithPath("message").type(JsonFieldType.STRING).description("상세 메시지"),
                               fieldWithPath("value").type(JsonFieldType.NULL).description("JSON BODY 데이터")
                       )));
    }

    @Test
    @DisplayName("재설정할 비밀번호와 재설정 토큰을 입력받아 비밀번호 재설정에 성공한다")
    void 비밀번호_재설정_검사() throws Exception{
        MemberCredentialDto credentialDto = MemberCredentialDto.of("testToken", null, null, "resetPassword");
        given(findCredentialService.resetPassword(credentialDto)).willReturn("testToken");

        ObjectMapper objectMapper = new ObjectMapper();
        String dtoAsString = objectMapper.writeValueAsString(credentialDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/member/reset-password")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(dtoAsString))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andDo(document(
                       "member/post-reset-password", getDocumentRequest(), getDocumentResponse(),
                       requestFields(
                               fieldWithPath("token").type(JsonFieldType.STRING).description("비밀번호 재설정 UUID 토큰"),
                               fieldWithPath("email").type(JsonFieldType.NULL).description("회원가입시 입력한 이메일(사용하지 않음)"),
                               fieldWithPath("id").type(JsonFieldType.NULL).description("회원 아이디(사용하지 않음)"),
                               fieldWithPath("password").type(JsonFieldType.STRING).description("변경할 비밀번호")
                       ),
                       responseFields(
                               fieldWithPath("status").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"),
                               fieldWithPath("code").type(JsonFieldType.STRING).description("비즈니스 코드"),
                               fieldWithPath("message").type(JsonFieldType.STRING).description("상세 메시지"),
                               fieldWithPath("value").type(JsonFieldType.NULL).description("JSON BODY 데이터")
                       )
               ));
    }
}

