//package com.kernel360.mypage.controller;
//
//import com.kernel360.common.ControllerTest;
//import com.kernel360.member.dto.CarInfoDto;
//import com.kernel360.member.dto.MemberDto;
//import com.kernel360.member.dto.MemberInfo;
//import com.navercorp.fixturemonkey.FixtureMonkey;
//import com.navercorp.fixturemonkey.api.introspector.*;
//import net.jqwik.api.Arbitraries;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.MediaType;
//
//import java.util.Arrays;
//import java.util.Map;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
//import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
//import static org.springframework.restdocs.payload.PayloadDocumentation.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//class MyPageControllerTest extends ControllerTest {
//
//    private FixtureMonkey fixtureMonkey;
//
//    @BeforeEach
//    void 준비() {
//        fixtureMonkey = FixtureMonkey.builder()
//                .objectIntrospector(new FailoverIntrospector(
//                        Arrays.asList(
//                                BuilderArbitraryIntrospector.INSTANCE,
//                                FieldReflectionArbitraryIntrospector.INSTANCE,
//                                ConstructorPropertiesArbitraryIntrospector.INSTANCE,
//                                BeanArbitraryIntrospector.INSTANCE
//                        )
//                ))
//                .build();
//    }
//
//    @Test
//    void 회원정보_조회_요청시_200응답과_정보가_잘_반환되는지() throws Exception {
//        // given
//        String authToken = "Bearer test-token";
//        MemberDto expectedDto = fixtureMonkey.giveMeBuilder(MemberDto.class)
//                .set("jwtToken", "Bearer test-token")
//                .setNotNull("memberNo").setNotNull("id").setNotNull("email")
//                .setNotNull("password").setNotNull("gender").setNotNull("age")
//                .setNotNull("createdAt").setNotNull("createdBy").setNotNull("modifiedAt").setNotNull("modifiedBy")
//                .sample();
//
//
//        when(memberService.findMemberByToken(eq(authToken))).thenReturn(expectedDto);
//
//        // when & then
//        mockMvc.perform(get("/mypage/member")
//                        .header("Authorization", authToken))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.status").value(200))
//                .andExpect(jsonPath("$.code").value("BMC003"))
//                .andExpect(jsonPath("$.message").value("회원 조회 성공"))
//                .andExpect(jsonPath("$.value.memberNo").value(expectedDto.memberNo()))
//                .andExpect(jsonPath("$.value.id").value(expectedDto.id()))
//                .andExpect(jsonPath("$.value.email").value(expectedDto.email()))
//                .andExpect(jsonPath("$.value.gender").value(expectedDto.gender()))
//                .andExpect(jsonPath("$.value.age").value(expectedDto.age()))
//                .andExpect(jsonPath("$.value.createdAt").value(expectedDto.createdAt().toString()))
//                .andExpect(jsonPath("$.value.createdBy").value(expectedDto.createdBy()))
//                .andExpect(jsonPath("$.value.modifiedAt").value(expectedDto.modifiedAt().toString()))
//                .andExpect(jsonPath("$.value.modifiedBy").value(expectedDto.modifiedBy()))
//                .andDo(document("mypage-member/get-member-info",
//                        responseFields(
//                                fieldWithPath("status").description("상태 코드"),
//                                fieldWithPath("code").description("비즈니스 코드"),
//                                fieldWithPath("message").description("응답 메세지"),
//                                fieldWithPath("value.memberNo").description("회원 번호"),
//                                fieldWithPath("value.id").description("회원 아이디"),
//                                fieldWithPath("value.email").description("이메일"),
//                                fieldWithPath("value.password").ignored(),
//                                fieldWithPath("value.gender").description("성별"),
//                                fieldWithPath("value.age").description("나이"),
//                                fieldWithPath("value.createdAt").description("생성 날짜"),
//                                fieldWithPath("value.createdBy").description("생성자"),
//                                fieldWithPath("value.modifiedAt").description("수정 날짜"),
//                                fieldWithPath("value.modifiedBy").description("수정자"),
//                                fieldWithPath("value.jwtToken").ignored()
//                        )));
//
//        verify(memberService).findMemberByToken(authToken);
//    }
//
//    @Test
//    void 차량정보_조회_요청시_200응답과_정보가_잘_반환되는지() throws Exception {
//        // given
//        String authToken = "Bearer test-token";
//        CarInfoDto carInfoDto = fixtureMonkey.giveMeBuilder(CarInfoDto.class)
//                .set("carType", Arbitraries.integers().between(11, 14))
//                .set("carSize", Arbitraries.integers().between(16, 19))
//                .set("carColor", Arbitraries.integers().between(21, 28))
//                .set("drivingEnv", Arbitraries.integers().between(36, 38))
//                .set("parkingEnv", Arbitraries.integers().between(40, 42))
//                .sample();
//
//        Map<String, Object> carInfo = Map.of(
//                "car_info", carInfoDto,
//                "segment_options", commonCodeService.getCodes("segment"),
//                "carType_options", commonCodeService.getCodes("cartype"),
//                "color_options", commonCodeService.getCodes("color"),
//                "driving_options", commonCodeService.getCodes("driving"),
//                "parking_options", commonCodeService.getCodes("parking")
//        );
//
//        when(memberService.getCarInfo(authToken)).thenReturn(carInfo);
//
//        // when & then
//        mockMvc.perform(get("/mypage/car")
//                        .header("Authorization", authToken))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.status").value(200))
//                .andExpect(jsonPath("$.code").value("BMC004"))
//                .andExpect(jsonPath("$.message").value("차량정보 조회 성공"))
//                .andExpect(jsonPath("$.value.car_info.carType").isNumber())
//                .andExpect(jsonPath("$.value.car_info.carSize").isNumber())
//                .andExpect(jsonPath("$.value.car_info.carColor").isNumber())
//                .andExpect(jsonPath("$.value.car_info.drivingEnv").isNumber())
//                .andExpect(jsonPath("$.value.car_info.parkingEnv").isNumber())
//                .andDo(document("mypage-car/get-car-info",
//                        responseFields(
//                                fieldWithPath("status").description("상태 코드"),
//                                fieldWithPath("code").description("비즈니스 코드"),
//                                fieldWithPath("message").description("응답 메세지"),
//                                fieldWithPath("value.car_info.carType").description("차량 유형 코드"),
//                                fieldWithPath("value.car_info.carSize").description("차량 크기 코드"),
//                                fieldWithPath("value.car_info.carColor").description("차량 색상 코드"),
//                                fieldWithPath("value.car_info.drivingEnv").description("주행 환경 코드"),
//                                fieldWithPath("value.car_info.parkingEnv").description("주차 환경 코드"),
//                                subsectionWithPath("value.segment_options").description("차량 세그먼트 옵션 목록"),
//                                subsectionWithPath("value.carType_options").description("차량 유형 옵션 목록"),
//                                subsectionWithPath("value.color_options").description("차량 색상 옵션 목록"),
//                                subsectionWithPath("value.driving_options").description("주행 환경 옵션 목록"),
//                                subsectionWithPath("value.parking_options").description("주차 환경 옵션 목록")
//                        )
//                ));
//    }
//
//    @Test
//    void 회원_탈퇴_요청시_200응답과_탈퇴_성공_메시지가_반환되는지() throws Exception {
//        // given
//        String authToken = "Bearer test-token";
//        doNothing().when(memberService).deleteMemberByToken(authToken);
//
//        // when & then
//        mockMvc.perform(delete("/mypage/member")
//                        .header("Authorization", authToken))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.status").value(200))
//                .andExpect(jsonPath("$.code").value("BMC005"))
//                .andExpect(jsonPath("$.message").value("회원이 탈퇴 되었습니다."))
//                .andDo(print())
//                .andDo(document("mypage-member/delete-member",
//                        requestHeaders(
//                                headerWithName("Authorization").description("Bearer 토큰")
//                        ),
//                        responseFields(
//                                fieldWithPath("status").description("상태 코드"),
//                                fieldWithPath("code").description("비즈니스 코드"),
//                                fieldWithPath("message").description("응답 메시지"),
//                                fieldWithPath("value").description("응답 값").optional()
//                        )
//                ));
//
//        verify(memberService).deleteMemberByToken(authToken);
//    }
//
//    @Test
//    void 비밀번호_확인_요청시_200응답과_확인_성공_메시지가_반환되는지() throws Exception {
//        // given
//        String authToken = "Bearer test-token";
//        String password = "password123";
//        doNothing().when(memberService).changePassword(password, authToken);
//
//        // when & then
//        mockMvc.perform(post("/mypage/member")
//                        .content(password)
//                        .contentType(MediaType.TEXT_PLAIN)
//                        .header("Authorization", authToken))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.status").value(200))
//                .andExpect(jsonPath("$.code").value("BMC008"))
//                .andExpect(jsonPath("$.message").value("비밀번호가 확인 되었습니다."))
//                .andDo(print())
//                .andDo(document("mypage-member/validate-password",
//                        requestHeaders(
//                                headerWithName("Authorization").description("Bearer 인증 토큰"),
//                                headerWithName("Content-Type").description("콘텐츠 타입")
//                        ),
//                        responseFields(
//                                fieldWithPath("status").description("상태 코드"),
//                                fieldWithPath("code").description("비즈니스 코드"),
//                                fieldWithPath("message").description("응답 메시지"),
//                                fieldWithPath("value").description("응답 값").optional()
//                        )
//                ));
//
//        verify(memberService).changePassword(password, authToken);
//    }
//
//    @Test
//    void WhenRequestingAMemberInformationUpdate_ThenA200ResponseAndUpdateSuccessMessageAreReturned() throws Exception {
//        // given
//        MemberInfo memberInfo = fixtureMonkey.giveMeBuilder(MemberInfo.class).sample();
//        doNothing().when(memberService).updateMember(memberInfo);
//
//        // when & then
//        mockMvc.perform(put("/mypage/member")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(memberInfo)))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.status").value(200))
//                .andExpect(jsonPath("$.code").value("BMC006"))
//                .andExpect(jsonPath("$.message").value("회원정보가 변경 되었습니다."))
//                .andDo(document("mypage-member/update-member",
//                        requestHeaders(
//                                headerWithName("Content-Type").description("Content-Type")
//                        ),
//                        requestFields(
//                                fieldWithPath("id").description("Member ID"),
//                                fieldWithPath("email").description("Member Email"),
//                                fieldWithPath("gender").description("Member Gender"),
//                                fieldWithPath("age").description("Member Age")
//                        ),
//                        responseFields(
//                                fieldWithPath("status").description("Status Code"),
//                                fieldWithPath("code").description("Business Code"),
//                                fieldWithPath("message").description("Response message"),
//                                fieldWithPath("value").description("Response value").optional()
//                        )
//                ));
//
//        verify(memberService).updateMember(any(MemberInfo.class));
//    }
//}