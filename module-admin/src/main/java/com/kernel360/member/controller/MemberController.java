package com.kernel360.member.controller;

import com.kernel360.member.code.MemberBusinessCode;
import com.kernel360.member.dto.MemberResponse;
import com.kernel360.member.service.MemberService;
import com.kernel360.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("members")
    public ResponseEntity<ApiResponse<Page<MemberResponse>>> getMemberList(Pageable pageable) {
        Page<MemberResponse> members = memberService.getAllMembers(pageable);

        return ApiResponse.toResponseEntity(MemberBusinessCode.SUCCESS_REQUEST_ALL_MEMBER_LIST, members);
    }
}
