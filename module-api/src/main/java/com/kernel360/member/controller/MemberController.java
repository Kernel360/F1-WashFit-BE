package com.kernel360.member.controller;


import com.kernel360.carinfo.entity.CarInfo;
import com.kernel360.member.code.MemberBusinessCode;
import com.kernel360.member.dto.CarInfoDto;
import com.kernel360.member.dto.MemberDto;
import com.kernel360.member.dto.WashInfoDto;
import com.kernel360.member.service.MemberService;
import com.kernel360.response.ApiResponse;
import com.kernel360.washinfo.entity.WashInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.kernel360.member.code.MemberBusinessCode.SUCCESS_REQUEST_JOIN_MEMBER_CREATED;
import static com.kernel360.member.code.MemberBusinessCode.SUCCESS_REQUEST_LOGIN_MEMBER;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<ApiResponse<Object>> joinMember(@RequestBody MemberDto joinRequestDto) {

        memberService.joinMember(joinRequestDto);

        return ApiResponse.toResponseEntity(SUCCESS_REQUEST_JOIN_MEMBER_CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<MemberDto>> login(@RequestBody MemberDto loginDto) {

        MemberDto memberInfo = memberService.login(loginDto);

        //부가정보가 입력 되어있는가 > 차량정보, 세차정보, boolean (감싸서 보내든 말든 노상관)

        return ApiResponse.toResponseEntity(SUCCESS_REQUEST_LOGIN_MEMBER, memberInfo);
    }

    @GetMapping("/duplicatedCheckId/{id}")
    public boolean duplicatedCheckId (@PathVariable String id){

        return memberService.idDuplicationCheck(id);
    }

    @GetMapping("/duplicatedCheckEmail/{email}")
    public boolean duplicatedCheckEmail (@PathVariable String email){

        return memberService.emailDuplicationCheck(email);
    }

    @PostMapping("/testJwt")
    public String testJwt (){
        return "checked";
    }


    @PostMapping("/wash")
    public ResponseEntity<ApiResponse<WashInfo>> saveWashInfo(@RequestBody WashInfoDto washInfo, @RequestHeader("Authorization") String authToken){
        memberService.saveWashInfo(washInfo, authToken);

        return ApiResponse.toResponseEntity(MemberBusinessCode.SUCCESS_REQUEST_UPDATE_WASH_INFO_MEMBER);
    }

    @PostMapping("/car")
    public ResponseEntity<ApiResponse<CarInfo>> saveCarInfo(@RequestBody CarInfoDto carInfo, @RequestHeader("Authorization") String authToken){
        memberService.saveCarInfo(carInfo, authToken);

        return ApiResponse.toResponseEntity(MemberBusinessCode.SUCCESS_REQUEST_UPDATE_CAR_INFO_MEMBER);
    }


}
