package com.kernel360.member.controller;

import com.kernel360.member.service.MemberService;
import com.kernel360.utils.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member/")
public class MemberController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private JWT jwt;


//    /** 가입 **/
//    @PostMapping("/join")
//    public ResponseEntity<String> joinMember (@RequestParam MemberRequest){
//
//        if(memberService.joinMember(MemberRequest){
//            //JWT 토큰 발급
//            jwt.generateToken(MemberRequest.getId);
//        }
//
//        return new ResponseEntity<>(null, HttpStatus.CREATED);
//    }
//    /** 로그인 **/
//    @PostMapping("/login")
//    public ResponseEntity<String> login (@RequestParam loginDTO){
//
//        memberService.login();
//
//        return new ResponseEntity<>(null,HttpStatus.OK);
//    }
//    /** 마이페이지 조회 **/
//    @GetMapping("/info")
//    public ResponseEntity<String> memberInfo (@RequestParam additionMemberDTO){
//        return new ResponseEntity<>(null,HttpStatus.OK);
//    }
//
//    /** 마이페이지 수정 -- ???? **/
//    @PatchMapping("/info")
//    public ResponseEntity<String> memberInfo (@RequestParam additionMemberDTO){
//        return new ResponseEntity<>(null,HttpStatus.OK);
//    }
//
//    /** 아이디 찾기 **/
//    @PostMapping("/find-id")
//    public ResponseEntity<String> findMemberById (@RequestParam memberDTO){
//        return new ResponseEntity<>(null,HttpStatus.OK);
//    }
//
//    /** 비밀번호 찾기 **/
//    @PostMapping("/find-password")
//    public ResponseEntity<String> findMemberByPassword (@RequestParam memberDTO){
//        return new ResponseEntity<>(null,HttpStatus.OK);
//    }
//
//    /** 비밀번호 변경 **/
//    @PutMapping("/change-password")
//    public ResponseEntity<String> changePassword (@RequestParam memberDTO){
//        return new ResponseEntity<>(null,HttpStatus.OK);
//    }
}
