//package com.kernel360.member.service;
//
//import com.kernel360.member.dto.MemberDto;
//import com.kernel360.member.entity.Member;
//import com.kernel360.member.repository.MemberRepository;
//import com.kernel360.utils.ConvertSHA256;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class MemberService {
//
//    final private MemberRepository memberRepository;
//    /** 가입 로직 **/
//    public void joinMember(MemberDto requestDto){
//
//        String encodePassword = ConvertSHA256.convertToSHA256(requestDto.password());
//
//        Member entity = getNewJoinMemberEntity(requestDto,encodePassword);
//
//        memberRepository.save(entity);
//    }

//    private Member getNewJoinMemberEntity (MemberDto dto, String password){
//        return Member.createJoinMember(dto.id(),dto.email(),password);
//    }

//
//    public void login(){
//
//        //토큰 검증
//
//        //MemberLoginDTO
////        - 회원정보 검증 (findAllByIdAndPassword
//
////                - 로그인 정보 반출 (return MemberLoginDTO + 세션/토큰)
//
//        //토큰 시간 체크 후 갱신
//
//        //회원정보, 토큰 리턴
//
//        String jwtToken = token.replace("Bearer ", "");
//
//        Claims claims = Jwts.parserBuilder()
//                .setSigningKey(SECRET_KEY)
//                .build()
//                .parseClaimsJws(jwtToken)
//                .getBody();
//
//        String username = claims.getSubject();
//        // 사용자 정보를 기반으로 처리
//    }
//
//    public void findID(){
////        to findIdByEmailDTO
////        - 정보 검증 (이메일)
////                - 이메일 발신
//    }
//
//    public void requestResetPassword(){
////        resetPasswordDTO(ID, EMAIL)
////                - 재설정용 임시 키 생성 (UUID)
////                - 키 반출 (return uuid)
////        - 패스워드 재설정 resetPasswordByAuthKey
//
//
//    }
//
//    public void resetPassword(){
//        //resetPasswordByAuthKey
////        resetPasswordDTO(newPassword, authKey)
////                - 패스워드 재설정 resetPasswordByAuthKey
//
//    }
//}
