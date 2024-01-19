package com.kernel360.member.controller;


import com.kernel360.member.dto.MemberDto;
import com.kernel360.member.service.MemberService;
import com.kernel360.response.ApiResponse;
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
    public ResponseEntity<?> joinMember(@RequestBody MemberDto joinRequestDto) {

        memberService.joinMember(joinRequestDto);

        return ApiResponse.toResponseEntity(SUCCESS_REQUEST_JOIN_MEMBER_CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberDto loginDto) {

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


}
