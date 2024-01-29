package com.kernel360.mypage.controller;

import com.kernel360.member.code.MemberBusinessCode;
import com.kernel360.member.dto.MemberDto;
import com.kernel360.member.service.MemberService;
import com.kernel360.product.service.ProductService;
import com.kernel360.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

        return ApiResponse.toResponseEntity(MemberBusinessCode.SUCCESS_REQUEST_DELETE_MEMBER);
    }


    @PostMapping("/member")
    <T> ResponseEntity<ApiResponse<T>> changePassword(@RequestBody MemberDto memberDto) {
        MemberDto dto = MemberDto.of(memberDto.id(), memberDto.password());
        memberService.changePassword(dto);

        return ApiResponse.toResponseEntity(MemberBusinessCode.SUCCESS_REQUEST_CHANGE_PASSWORD_MEMBER);
    }


    @PutMapping("/member")
    <T> ResponseEntity<ApiResponse<T>> updateMember(@RequestBody MemberDto memberDto) {
        memberService.updateMember(memberDto);

        return ApiResponse.toResponseEntity(MemberBusinessCode.SUCCESS_REQUEST_UPDATE_MEMBER);

    }
}
