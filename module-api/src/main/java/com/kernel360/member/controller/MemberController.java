package com.kernel360.member.controller;


import com.kernel360.member.dto.MemberDto;
import com.kernel360.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<String> joinMember(@RequestBody MemberDto joinRequestDto) {

        try {
            memberService.joinMember(joinRequestDto);
        } catch (IllegalArgumentException args) {
            log.error("가입에러발생", args);
        }

        return new ResponseEntity<>("", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<MemberDto> login(@RequestBody MemberDto loginDto) {

        MemberDto memberInfo = memberService.login(loginDto);

        return new ResponseEntity<>(memberInfo, HttpStatus.OK);
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
