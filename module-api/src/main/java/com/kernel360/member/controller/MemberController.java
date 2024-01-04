package com.kernel360.member.controller;

import ch.qos.logback.core.model.Model;
import com.kernel360.member.dto.MemberDto;
import com.kernel360.member.service.MemberService;
import com.kernel360.utils.JWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member/")
public class MemberController {

    private final MemberService memberService;

    /** 가입 **/
    @PostMapping("/join")
    public ResponseEntity<Model> joinMember (@ModelAttribute MemberDto joinRequestDto){

        try{
            memberService.joinMember(joinRequestDto);
        }catch (IllegalArgumentException args){
            log.error("가입에러발생", args);
        }

        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }


}
