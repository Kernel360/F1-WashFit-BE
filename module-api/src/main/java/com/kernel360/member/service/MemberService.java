package com.kernel360.member.service;

import com.kernel360.auth.service.AuthService;
import com.kernel360.carinfo.entity.CarInfo;
import com.kernel360.carinfo.repository.CarInfoRepository;
import com.kernel360.commoncode.service.CommonCodeService;
import com.kernel360.exception.BusinessException;
import com.kernel360.member.code.MemberErrorCode;
import com.kernel360.member.dto.CarInfoDto;
import com.kernel360.member.dto.MemberDto;
import com.kernel360.member.dto.MemberInfo;
import com.kernel360.member.dto.WashInfoDto;
import com.kernel360.member.entity.Member;
import com.kernel360.member.enumset.Age;
import com.kernel360.member.enumset.Gender;
import com.kernel360.member.repository.MemberRepository;
import com.kernel360.utils.ConvertSHA256;
import com.kernel360.utils.JWT;
import com.kernel360.washinfo.entity.WashInfo;
import com.kernel360.washinfo.repository.WashInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;
import java.util.Objects;


@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final JWT jwt;
    private final AuthService authService;
    private final MemberRepository memberRepository;
    private final CommonCodeService commonCodeService;
    private final CarInfoRepository carInfoRepository;
    private final WashInfoRepository washInfoRepository;

    @Transactional
    public void joinMember(MemberDto requestDto) {
        Member entity = getNewJoinMemberEntity(requestDto);
        if (entity == null) {   throw new BusinessException(MemberErrorCode.FAILED_GENERATE_JOIN_MEMBER_INFO);  }

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

    @Transactional
    public MemberDto login(MemberDto loginDto) {
        Member loginEntity = newReqeustLoginEntity(loginDto);
        if (Objects.isNull(loginEntity)) { throw new BusinessException(MemberErrorCode.FAILED_GENERATE_LOGIN_REQUEST_INFO);    }

        Member memberEntity = memberRepository.findOneByIdAndPassword(loginEntity.getId(), loginEntity.getPassword());
        if (Objects.isNull(memberEntity)) { throw new BusinessException(MemberErrorCode.FAILED_REQUEST_LOGIN);  }

        String loginToken = jwt.generateToken(memberEntity.getId());

        authService.saveAuthByMember(memberEntity.getMemberNo(), ConvertSHA256.convertToSHA256(loginToken));

        return MemberDto.login(memberEntity, loginToken);
    }

    private Member newReqeustLoginEntity(MemberDto loginDto) {
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

    public MemberDto findMemberByToken(String token) {
        String id = JWT.ownerId(token);

        return MemberDto.from(memberRepository.findOneById(id));
    }

    public CarInfo findCarInfoByToken(@RequestHeader("Authorization") String authToken) {
        MemberDto memberDto = findMemberByToken(authToken);

        return memberDto.toEntity().getCarInfo();
    }

    @Transactional
    public void deleteMember(String id) {
        Member member = memberRepository.findOneById(id);

        memberRepository.delete(member);
    }

    @Transactional
    public void deleteMemberByToken(String token) {
        final String id = JWT.ownerId(token);
        Member member = memberRepository.findOneById(id);
        //Fixme :: 멤버 탈퇴시, Deleted Table을 만들고, 데이터를 백업한후, 삭제하는 방식이나, MemberTable에 삭제여부를 표시하는 방식으로 리팩토링 필요
        memberRepository.delete(member);
        log.info("{} 회원 탈퇴 처리 완료", id);
    }

    @Transactional
    public void changePassword(String password, String token) {
        String id = JWT.ownerId(token);
        Member member = memberRepository.findOneById(id);

        member.updatePassword(password);
        log.info("{} 회원의 비밀번호가 변경되었습니다.", id);
    }

    @Transactional
    public void updateMember(MemberInfo memberInfo) {   memberRepository.save(memberInfo.toEntity());   }

    @Transactional(readOnly = true)
    public Map<String, Object> getCarInfo(String token) {
        String id = JWT.ownerId(token);
        Member member = memberRepository.findOneById(id);
        CarInfoDto carInfoDto = CarInfoDto.from(member.getCarInfo());

        return Map.of(
                "car_info", carInfoDto,
                "segment_options", commonCodeService.getCodes("segment"),
                "carType_options", commonCodeService.getCodes("cartype"),
                "color_options", commonCodeService.getCodes("color"),
                "driving_options", commonCodeService.getCodes("driving"),
                "parking_options", commonCodeService.getCodes("parking")
        );
    }

    @Transactional
    public void saveWashInfo(WashInfoDto washInfoDto, String token) {
        String id = JWT.ownerId(token);
        Member member = memberRepository.findOneById(id);
        WashInfo washInfo = WashInfo.of(washInfoDto.washNo(), washInfoDto.washCount(), washInfoDto.monthlyExpense(), washInfoDto.interest());
        washInfo.settingMember(member);
        member.updateWashInfo(washInfo);

        washInfoRepository.save(washInfo);
    }

    @Transactional
    public void saveCarInfo(CarInfoDto carInfoDto, String token) {
        String id = JWT.ownerId(token);
        Member member = memberRepository.findOneById(id);
        CarInfo carInfo = CarInfo.of(carInfoDto.carType(), carInfoDto.carSize(), carInfoDto.carColor(), carInfoDto.drivingEnv(), carInfoDto.parkingEnv());
        carInfo.settingMember(member);
        member.updateCarInfo(carInfo);

        carInfoRepository.save(carInfo);
    }

    public MemberDto findByEmail(String email) {
        Member member = memberRepository.findOneByEmail(email);
        if (member == null) {   throw new BusinessException(MemberErrorCode.FAILED_FIND_MEMBER_INFO);   }

        return MemberDto.from(member);
    }

    public MemberDto findByMemberId(String memberId) {
        Member member = memberRepository.findOneById(memberId);
        if (member == null) {
            throw new BusinessException(MemberErrorCode.FAILED_FIND_MEMBER_INFO);
        }

        return MemberDto.from(member);
    }
    @Transactional
    public void resetPasswordByMemberId(String memberId, String newPassword) {
        Member member = memberRepository.findOneById(memberId);
        if (member == null) {
            throw new BusinessException(MemberErrorCode.FAILED_FIND_MEMBER_INFO);
        }
        member.updatePassword(ConvertSHA256.convertToSHA256(newPassword));
//        memberRepository.save(member);
    }

}
