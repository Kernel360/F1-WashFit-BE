package com.kernel360.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kernel360.member.dto.MemberDto;
import com.kernel360.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDate;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private MemberService memberService;


    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                                      .addFilter(new CharacterEncodingFilter("UTF-8", true))
                                      .alwaysDo(print())
                                      .build();
    }

    @Test
    @DisplayName("회원가입요청")
    void 회원가입요청() throws Exception {

        /** given 목데이터 세팅 **/
        MemberDto memberDto = MemberDto.of("testID", "gunsight777@naver.com", "testPassword");

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

        MemberDto memberDto = MemberDto.of("testID", "testPassword");
        MemberDto memberInfo = new MemberDto(1L,
                                            "test01",
                                            "kernel360@kernel360.com",
                                            "",
                                            null,
                                            null,
                                            LocalDate.now(),
                                            "test01",
                                            null,
                                            null,
                                            "dummyToken"
            );
        ObjectMapper objectMapper = new ObjectMapper();
        String param = objectMapper.writeValueAsString(memberDto);

        given(memberService.login(memberDto)).willReturn(memberInfo);

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


}