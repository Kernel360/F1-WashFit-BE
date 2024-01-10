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

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final JWT jwt;
    private final AuthRepository authRepository;
    private final MemberRepository memberRepository;

    /**
     * 가입
     **/
    public void joinMember(MemberDto requestDto) {

        Member entity = getNewJoinMemberEntity(requestDto);

        memberRepository.save(entity);
    }

    protected Member getNewJoinMemberEntity(MemberDto requestDto) {

        String encodePassword = ConvertSHA256.convertToSHA256(requestDto.password());

        return Member.createJoinMember(requestDto.id(), requestDto.email(), encodePassword);
    }

    /**
     * 로그인
     **/
    public MemberDto login(MemberDto loginDto) {

        Member loginEntity = getReqeustLoginEntity(loginDto);
        Member memberEntity = memberRepository.findOneByIdAndPassword(loginEntity.getId(), loginEntity.getPassword());
        String token = jwt.generateToken(memberEntity.getId());
        String encryptToken = ConvertSHA256.convertToSHA256(token);
        Auth authJwt = authRepository.findOneByMemberNo(memberEntity.getMemberNo());

        //결과 없으면 entity로 신규 생성
        authJwt = Optional.ofNullable(authJwt)
                          .map(modifyAuth -> modifyAuthJwt(modifyAuth, encryptToken))
                          .orElseGet(() -> createAuthJwt(memberEntity.getMemberNo(), encryptToken));

        authRepository.save(authJwt);

        return MemberDto.login(memberEntity, token);
    }

    protected Auth modifyAuthJwt(Auth modifyAuth, String encryptToken) {
        modifyAuth.updateJwt(encryptToken);

        return modifyAuth;
    }

    protected Auth createAuthJwt(Integer memberNo, String encryptToken) {

        return Auth.of(null, memberNo, encryptToken, null);
    }


    private Member getReqeustLoginEntity(MemberDto loginDto) {
        String encodePassword = ConvertSHA256.convertToSHA256(loginDto.password());

        return Member.loginMember(loginDto.id(), encodePassword);
    }


}
