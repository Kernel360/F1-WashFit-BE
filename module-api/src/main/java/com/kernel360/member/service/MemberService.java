package com.kernel360.member.service;

import com.kernel360.member.entity.Member;
import com.kernel360.member.repository.MemberRepository;
import com.kernel360.utils.ConvertSHA256;
import org.springframework.stereotype.Service;

import java.sql.SQLDataException;

import static javax.crypto.Cipher.SECRET_KEY;

@Service
public class MemberService {
    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
//
//    /** 가입 로직 **/
//    public boolean joinMember(MemberDTO){
//        String encodePassword;
//        boolean result = false;
//        try {
//            encodePassword = ConvertSHA256.convertToSHA256(MemberDto.getPassword());
//        }
//        catch (RuntimeException noSearch){
//            log.error("JoinMember :: no search param by " + noSearch.getStackTrace());
//        }
//
//
//        try {
//            // DTO -> Entity 변환
//            Member MemberEntity = new Member();
//            memberRepository.save(MemberEntity);
//            result = true;
//            //success message
//        }catch (SQLDataException sql){
//            log.error("JoinMember :: save sql error by " + sql.getStackTrace());
//        }
//
//        //jwt 키생성
//
//        //사용자 정보 jwt 키 db save
//
//        //토큰 return
//
//        return result;
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
}
