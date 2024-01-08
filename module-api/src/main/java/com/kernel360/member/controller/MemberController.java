package com.kernel360.member.controller;


import com.kernel360.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    @PostMapping("/join")
    public String signup(@RequestBody MemberDto memberDto ) {
        return "member/signup";
    }
}
