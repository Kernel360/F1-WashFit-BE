package com.kernel360.member.service;

import com.kernel360.auth.entity.Auth;
import com.kernel360.auth.repository.AuthRepository;
import com.kernel360.member.dto.MemberDto;
import com.kernel360.member.entity.Member;
import com.kernel360.member.repository.MemberRepository;
import com.kernel360.utils.ConvertSHA256;
import com.kernel360.utils.JWT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private JWT jwt;

    @Mock
    private AuthRepository authRepository;

    @InjectMocks
    private MemberService memberService;

    @Mock
    private ConvertSHA256 convertSHA256;

    @BeforeEach
    public void init() {

        convertSHA256 = mock(ConvertSHA256.class);
    }

    @Test
    @DisplayName("회원가입_테스트")
    void 회원가입_로직_테스트(){

        /** given **/
        MemberDto requestDto = MemberDto.of("testID", "gunsight777@naver.com", "testPassword");
        Member member = memberService.getNewJoinMemberEntity(requestDto);

        /** when **/
        memberRepository.save(member);

        /** then **/
        verify(memberRepository).save(member);
    }

    @Test
    @DisplayName("암호화_메서드_테스트")
    void 암호화_로직_테스트(){

        /** given **/
        String original = "this_is_test_text!";
        String expect = "c4ea44dbb286170b5caa17b03ae978a874cdb6c6751ed11a2518acb5dc84e86e";

        /** when **/
        String convert = convertSHA256.convertToSHA256(original);

        /** then **/
        assertEquals(expect,convert);
    }

    @Test
    @DisplayName("로그인시_회원정보조회_후_토큰발급이_제대로_수행되는지_테스트한다.")
    void 로그인_테스트(){

        /** given **/
        MemberDto loginDto = MemberDto.of("test03","1234qwer");
        Member mockLoginEntity = Member.loginMember(loginDto.id(), loginDto.password());
        Member mockEntity = Member.of(502,loginDto.id(),"test03@naver.com","0eb9de69892882d54516e03e30098354a2e39cea36adab275b6300c737c942fd",null,null);
        String mockToken = "dummy_token";

        /** stub **/
        when(memberRepository.findAllByIdAndPassword(any(), any())).thenReturn(mockEntity);

        /** when **/
        Member loginEntity = mockLoginEntity;
        Member memberEntity = memberRepository.findAllByIdAndPassword(loginEntity.getId(), loginEntity.getPassword());
        String token = mockToken;
        MemberDto memberInfo = MemberDto.login(memberEntity, token);

        /** then , given-stub의 대한 리팩터링이 필요한 것으로 판단 됨. **/
        assertNotNull(memberInfo,"로그인을 하면 회원정보를 가지고 있어야 함.");
        assertEquals("dummy_token",token);
        assertEquals(502,memberEntity.getMemberNo());
        assertNull(memberInfo.password(),"비밀번호는 빈값이어야함.");
    }

    @Test
    @DisplayName("로그인시_토큰발급_및_토큰정보_저장을_테스트한다.")
    void 토큰_발급_저장_테스트(){

        /** given **/
        Member memberEntity = Member.of(502,"test03",null,null,null,null);
        String mockToken = "mockToken";
        String convertToken = "convert_mock_token";
        Auth auth = Auth.jwt(null,502, mockToken);

        /** stub **/
        when(jwt.generateToken(anyString())).thenReturn(mockToken);
        //when(convertSHA256.convertToSHA256(anyString())).thenReturn(convertToken);
        when(authRepository.findOneByMemberNo(anyInt())).thenReturn(auth);
        //when(this.createAuthJwt(anyInt(),any())).thenReturn(auth);

        /** when **/
        String token = jwt.generateToken(memberEntity.getId()); // 토큰 생성은 mockToken으로 잘 가져옴.
        String encryptToken = convertSHA256.convertToSHA256(token); // mock 객체로 세팅을 했지만 얘는 왜 실제 로직을 타는지는 몰?루 line:113
        Auth authJwt = authRepository.findOneByMemberNo(memberEntity.getMemberNo());

        if(authJwt == null) {
            authJwt = memberService.createAuthJwt(null, memberEntity.getMemberNo(), encryptToken);
        }else{
            authJwt = memberService.createAuthJwt(authJwt.getAuthNo(),authJwt.getMemberNo(),encryptToken);
        }
        authRepository.save(authJwt);

        /** then , given - stub의 대한 리팩터링이 필요하다 판단 됨. **/
        verify(authRepository).save(authJwt);
        assertNotNull(token,"토큰 생성 결과 값은 null이 아니어야 함.");
    }

}
