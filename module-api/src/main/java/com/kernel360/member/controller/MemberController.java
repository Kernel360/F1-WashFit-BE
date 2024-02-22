package com.kernel360.member.controller;


import com.kernel360.carinfo.entity.CarInfo;
import com.kernel360.member.code.MemberBusinessCode;
import com.kernel360.member.dto.CarInfoDto;
import com.kernel360.member.dto.MemberCredentialDto;
import com.kernel360.member.dto.MemberDto;
import com.kernel360.member.dto.WashInfoDto;
import com.kernel360.member.service.FindCredentialService;
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

    private final FindCredentialService findCredentialService;

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
    public boolean duplicatedCheckId(@PathVariable String id) {

        return memberService.idDuplicationCheck(id);
    }

    @GetMapping("/duplicatedCheckEmail/{email}")
    public boolean duplicatedCheckEmail(@PathVariable String email) {

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

    @PostMapping("/find-memberId")
    public ResponseEntity<ApiResponse<Object>> sendMemberIdByEmail(@RequestBody MemberCredentialDto credentialDto) {
        String memberId = memberService.findByEmail(credentialDto.email()).id();

        findCredentialService.sendMemberId(credentialDto.email(), memberId);

        return ApiResponse.toResponseEntity(MemberBusinessCode.SUCCESS_REQUEST_FIND_MEMBER_ID);
    }

    @PostMapping("/find-password")
    public ResponseEntity<ApiResponse<Object>> sendPasswordResetUriByEmail(@RequestBody MemberCredentialDto dto) {
        //--입력받은 아이디를 데이터베이스에 조회, 없으면 예외 발생--/
        MemberDto memberDto = memberService.findByMemberId(dto.memberId());
        //--유효성이 검증된 아이디에 대해서 만료시간이 있는 비밀번호 초기화 (호스트 + UUID) 링크 생성 --//
        String resetUri = findCredentialService.generatePasswordResetUri( memberDto);
        //-- 가입시 입력한 이메일로 비밀번호 초기화 이메일 발송 --//
        findCredentialService.sendPasswordResetUri(resetUri, memberDto);

        return ApiResponse.toResponseEntity(MemberBusinessCode.SUCCESS_REQUEST_SEND_RESET_PASSWORD_EMAIL);
    }

    @GetMapping("/reset-password")
    public ResponseEntity<ApiResponse<Object>> getPasswordResetPage(@RequestParam String token) {
        findCredentialService.getData(token);

        return ApiResponse.toResponseEntity(MemberBusinessCode.SUCCESS_REQUEST_RESET_PASSWORD_PAGE, token);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<Object>> resetPassword(@RequestBody MemberCredentialDto credentialDto) {
        String authKey = findCredentialService.resetPassword(credentialDto);
        findCredentialService.getAndExpireData(authKey);

        return ApiResponse.toResponseEntity(MemberBusinessCode.SUCCESS_REQUEST_RESET_PASSWORD);
    }
}
