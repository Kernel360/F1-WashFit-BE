package com.kernel360.mypage.controller;

import com.kernel360.member.code.MemberBusinessCode;
import com.kernel360.member.dto.MemberDto;
import com.kernel360.member.dto.MemberInfo;
import com.kernel360.member.service.MemberService;
import com.kernel360.product.service.ProductService;
import com.kernel360.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@Slf4j
@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {
    private final MemberService memberService;
    private final ProductService productService;

    @GetMapping("/member")
    <T> ResponseEntity<ApiResponse<MemberDto>> myInfo(RequestEntity<T> request) {
        MemberDto dto = memberService.findMemberByToken(request);

        return ApiResponse.toResponseEntity(MemberBusinessCode.SUCCESS_FIND_REQUEST_MEMBER, dto);
    }

    @GetMapping("/car")
    <T> ResponseEntity<ApiResponse<Map<String, Object>>> myCar(RequestEntity<T> request) {
        Map<String, Object> carInfo = memberService.getCarInfo(request);

        return ApiResponse.toResponseEntity(MemberBusinessCode.SUCCESS_FIND_CAR_INFO_IN_MEMBER, carInfo);
    }


    @DeleteMapping("/member")
    <T> ResponseEntity<ApiResponse<T>> memberDelete(@RequestBody MemberDto memberDto) {
        memberService.deleteMember(memberDto.id());
        log.info("{} 회원 탈퇴 처리 완료", memberDto.id());

        return ApiResponse.toResponseEntity(MemberBusinessCode.SUCCESS_REQUEST_DELETE_MEMBER);
    }


    @PostMapping("/member")
    <T> ResponseEntity<ApiResponse<T>> changePassword(@RequestBody MemberInfo memberInfo) {
        memberService.changePassword(memberInfo);
        log.info("{} 회원 비밀번호 수정 처리 완료", memberInfo.id() );

        return ApiResponse.toResponseEntity(MemberBusinessCode.SUCCESS_REQUEST_CHANGE_PASSWORD_MEMBER);
    }


    @PutMapping("/member")
    <T> ResponseEntity<ApiResponse<T>> updateMember(@RequestBody MemberDto memberDto) {
        memberService.updateMember(memberDto);

        return ApiResponse.toResponseEntity(MemberBusinessCode.SUCCESS_REQUEST_UPDATE_MEMBER);

    }
}
