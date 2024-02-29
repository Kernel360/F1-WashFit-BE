package com.kernel360.mypage.controller;

import com.kernel360.exception.BusinessException;
import com.kernel360.member.code.MemberBusinessCode;
import com.kernel360.member.code.MemberErrorCode;
import com.kernel360.member.dto.MemberDto;
import com.kernel360.member.dto.MemberInfo;
import com.kernel360.member.dto.PasswordDto;
import com.kernel360.member.dto.WashInfoDto;
import com.kernel360.member.service.MemberService;
import com.kernel360.product.service.ProductService;
import com.kernel360.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    ResponseEntity<ApiResponse<MemberDto>> myInfo(@RequestHeader("Authorization") String authToken) {
        MemberDto dto = memberService.findMemberByToken(authToken);

        return ApiResponse.toResponseEntity(MemberBusinessCode.SUCCESS_FIND_REQUEST_MEMBER, dto);
    }

    @GetMapping("/car")
    ResponseEntity<ApiResponse<Map<String, Object>>> myCar(@RequestHeader("Authorization") String authToken) {
        Map<String, Object> carInfo = memberService.getCarInfo(authToken);

        return ApiResponse.toResponseEntity(MemberBusinessCode.SUCCESS_FIND_CAR_INFO_IN_MEMBER, carInfo);
    }

    @GetMapping("/wash")
    ResponseEntity<ApiResponse<WashInfoDto>> myWash(@RequestHeader("Authorization") String authToken) {
        WashInfoDto washInfoDto = memberService.getWashInfo(authToken);

        return ApiResponse.toResponseEntity(MemberBusinessCode.SUCCESS_FIND_WASH_INFO_IN_MEMBER, washInfoDto);
    }

    @DeleteMapping("/member")
    ResponseEntity<ApiResponse<Void>> memberDelete(@RequestHeader("Authorization") String authToken) {
        memberService.deleteMemberByToken(authToken);

        return ApiResponse.toResponseEntity(MemberBusinessCode.SUCCESS_REQUEST_DELETE_MEMBER);
    }

    @PostMapping("/member")
    ResponseEntity<ApiResponse<String>> changePassword(@RequestBody String password, @RequestHeader("Authorization") String authToken) {
        memberService.changePassword(password, authToken);

        return ApiResponse.toResponseEntity(MemberBusinessCode.SUCCESS_VALIDATE_PASSWORD_MEMBER);
    }

    @GetMapping("/member/validate")
    boolean validatePassword(@RequestBody PasswordDto password, @RequestHeader("Authorization") String authToken) {

        return memberService.validatePassword(password.password(), authToken);
    }

    @PatchMapping("/member")
    <T> ResponseEntity<ApiResponse<T>> updateMember(@RequestBody MemberInfo memberInfo,
                                                    @RequestHeader("Authorization") String authToken) {
        memberService.updateMember(memberInfo, authToken);

        return ApiResponse.toResponseEntity(MemberBusinessCode.SUCCESS_REQUEST_UPDATE_MEMBER);
    }


}
