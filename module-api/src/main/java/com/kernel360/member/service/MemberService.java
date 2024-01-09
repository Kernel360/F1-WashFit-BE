package com.kernel360.member.service;

import com.kernel360.auth.entity.Auth;
import com.kernel360.auth.repository.AuthRepository;
import com.kernel360.member.dto.MemberDto;
import com.kernel360.member.entity.Member;
import com.kernel360.member.repository.MemberRepository;
import com.kernel360.utils.ConvertSHA256;
import com.kernel360.utils.JWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final JWT jwt;
    private final ConvertSHA256 convertSHA256;
    private final AuthRepository authRepository;
    private final MemberRepository memberRepository;

    /** 가입 **/
    public void joinMember(MemberDto requestDto){

        Member entity = getNewJoinMemberEntity(requestDto);

        memberRepository.save(entity);
    }

    protected Member getNewJoinMemberEntity(MemberDto requestDto){

        String encodePassword = convertSHA256.convertToSHA256(requestDto.password());

        return Member.createJoinMember(requestDto.id(), requestDto.email(), encodePassword);
    }

    /** 로그인 **/
    public MemberDto login(MemberDto loginDto) {

        Member loginEntity = getReqeustLoginEntity(loginDto);
        Member memberEntity = memberRepository.findAllByIdAndPassword(loginEntity.getId(), loginEntity.getPassword());
        String token = printJwtToken(memberEntity);

        MemberDto memberInfo = MemberDto.login(memberEntity, token);

        return memberInfo;
    }

    private Member getReqeustLoginEntity(MemberDto loginDto) {
        String encodePassword = convertSHA256.convertToSHA256(loginDto.password());
        return Member.loginMember(loginDto.id(),encodePassword );
    }

    /** 토큰 발급, 이전 토큰에 따른 update or insert **/
    public String printJwtToken(Member memberEntity){

        String token = jwt.generateToken(memberEntity.getId());
        String encryptToken = convertSHA256.convertToSHA256(token);
        Auth authJwt = authRepository.findOneByMemberNo(memberEntity.getMemberNo());

        if (authJwt == null) {
            authJwt = createAuthJwt(null, memberEntity.getMemberNo(),encryptToken);
        } else {
            authJwt.updateJwt(encryptToken);
        }
        authRepository.save(authJwt);


        return token;
    }

    protected Auth createAuthJwt(Integer authNo, Integer memberNo, String encryptToken) {

        return Auth.of(null, memberNo, encryptToken, null);
    }

}
