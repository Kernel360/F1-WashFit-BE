package com.kernel360.member.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.kernel360.carinfo.entity.CarInfo;
import com.kernel360.exception.BusinessException;
import com.kernel360.member.code.MemberBusinessCode;
import com.kernel360.member.code.MemberErrorCode;
import com.kernel360.member.dto.CarInfoDto;
import com.kernel360.member.dto.FindMemberIdFromEmailDto;
import com.kernel360.member.dto.MemberDto;
import com.kernel360.member.dto.WashInfoDto;
import com.kernel360.member.service.FindService;
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

    private final FindService findService;

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

    @PostMapping("/find/memberId")
    public ResponseEntity<ApiResponse<Object>> sendMemberIdByEmail(@RequestBody FindMemberIdFromEmailDto dto) {
        String memberId = memberService.findByEmail(dto.email()).id();

        if (memberId.isEmpty()) {
            throw new BusinessException(MemberErrorCode.FAILED_FIND_MEMBER_INFO);
        }

        findService.sendMemberId(dto.email(), memberId);

        return ApiResponse.toResponseEntity(
                MemberBusinessCode.SUCCESS_REQUEST_FIND_MEMBER_ID); // 아이디는 이메일로 보내고, 외부 노출 X
    }

    @GetMapping("/login/forKakao")
    public ResponseEntity<ApiResponse<MemberDto>> loginForKakao(@RequestHeader("Authorization") String accessToken) {

        MemberDto member = memberService.loginForKakao(accessToken);

        return ApiResponse.toResponseEntity(SUCCESS_REQUEST_LOGIN_MEMBER, member);
    }
}
