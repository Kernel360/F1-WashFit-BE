package com.kernel360.member.service;

import com.kernel360.auth.entity.Auth;
import com.kernel360.auth.repository.AuthRepository;
import com.kernel360.carinfo.entity.CarInfo;
import com.kernel360.commoncode.service.CommonCodeServiceImpl;
import com.kernel360.exception.BusinessException;
import com.kernel360.member.code.MemberErrorCode;
import com.kernel360.member.dto.MemberDto;
import com.kernel360.member.dto.MemberInfo;
import com.kernel360.member.entity.Member;
import com.kernel360.member.enumset.Age;
import com.kernel360.member.enumset.Gender;
import com.kernel360.member.repository.MemberRepository;
import com.kernel360.utils.ConvertSHA256;
import com.kernel360.utils.JWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final JWT jwt;
    private final AuthRepository authRepository;
    private final MemberRepository memberRepository;
    private final CommonCodeServiceImpl commonCodeServiceImpl;


    /**
     * 가입
     **/
    @Transactional
    public void joinMember(MemberDto requestDto) {

        Member entity = getNewJoinMemberEntity(requestDto);
        if (entity == null) {
            throw new BusinessException(MemberErrorCode.FAILED_GENERATE_JOIN_MEMBER_INFO);
        }

        memberRepository.save(entity);
    }

    protected Member getNewJoinMemberEntity(MemberDto requestDto) {

        String encodePassword = ConvertSHA256.convertToSHA256(requestDto.password());
        int genderOrdinal;
        int ageOrdinal;

        try {
            genderOrdinal = Gender.valueOf(requestDto.gender()).ordinal();
            ageOrdinal = Age.valueOf(requestDto.age()).ordinal();
        } catch (Exception e) {
            throw new BusinessException(MemberErrorCode.FAILED_NOT_MAPPING_ENUM_VALUEOF);
        }

        return Member.createJoinMember(requestDto.id(), requestDto.email(), encodePassword, genderOrdinal, ageOrdinal);
    }

    /**
     * 로그인
     **/
    @Transactional
    public MemberDto login(MemberDto loginDto) {

        Member loginEntity = getReqeustLoginEntity(loginDto);
        if (loginEntity == null) {
            throw new BusinessException(MemberErrorCode.FAILED_GENERATE_LOGIN_REQUEST_INFO);
        }

        Member memberEntity = memberRepository.findOneByIdAndPassword(loginEntity.getId(), loginEntity.getPassword());
        if (memberEntity == null) {
            throw new BusinessException(MemberErrorCode.FAILED_REQUEST_LOGIN);
        }

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

    public Auth modifyAuthJwt(Auth modifyAuth, String encryptToken) {
        modifyAuth.updateJwt(encryptToken);

        return modifyAuth;
    }

    protected Auth createAuthJwt(Long memberNo, String encryptToken) {

        return Auth.of(null, memberNo, encryptToken, null);
    }


    private Member getReqeustLoginEntity(MemberDto loginDto) {
        String encodePassword = ConvertSHA256.convertToSHA256(loginDto.password());

        return Member.loginMember(loginDto.id(), encodePassword);
    }

    @Transactional(readOnly = true)
    public boolean idDuplicationCheck(String id) {
        Member member = memberRepository.findOneById(id);

        return member != null;
    }

    @Transactional(readOnly = true)
    public boolean emailDuplicationCheck(String email) {
        Member member = memberRepository.findOneByEmail(email);

        return member != null;
    }

    public Auth findOneAuthByJwt(String encryptToken) {
        return authRepository.findOneByJwtToken(encryptToken);
    }

    public void reissuanceJwt(Auth storedAuthInfo) {
        authRepository.save(storedAuthInfo);
    }


    public <T> MemberDto findMemberByToken(RequestEntity<T> request) {
        String token = request.getHeaders().getFirst("Authorization");
        String id = JWT.ownerId(token);

        return MemberDto.from(memberRepository.findOneById(id));
    }

    public <T> CarInfo findCarInfoByToken(RequestEntity<T> request) {
        MemberDto memberDto = findMemberByToken(request);

        return memberDto.toEntity().getCarInfo();
    }

    @Transactional
    public void deleteMember(String id) {
        Member member = memberRepository.findOneById(id);

        memberRepository.delete(member);
    }


    @Transactional
    public void changePassword(MemberInfo memberinfo) {
        Member member = memberRepository.findOneById(memberinfo.id());

        memberRepository.save(Member.of(member.getMemberNo(), member.getId(),
                member.getEmail(), memberinfo.password(),
                member.getGender(), member.getAge()
        ));
    }

    @Transactional
    public void updateMember(MemberDto memberDto) {
        Member member =
                Member.of(memberDto.memberNo(), memberDto.id(), memberDto.email(), memberDto.password(),
                        Integer.parseInt(memberDto.gender()), Integer.parseInt(memberDto.age()));

        memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public <T> Map<String, Object> getCarInfo(RequestEntity<T> request) {
        //        CarInfo carInfo = memberService.findCarInfoByToken(request);
        //FIXME :: CarInfo Data 없어서 에러발생 주석 처리해둠

        return Map.of(
//                "car_info", carInfo,
                "segment_options", commonCodeServiceImpl.getCodes("segment"),
                "carType_options", commonCodeServiceImpl.getCodes("cartype"),
                "color_options", commonCodeServiceImpl.getCodes("color"),
                "driving_options", commonCodeServiceImpl.getCodes("driving"),
                "parking_options", commonCodeServiceImpl.getCodes("parking")
        );
    }
}
