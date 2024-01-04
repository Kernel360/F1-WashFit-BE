package com.kernel360.member.service;

import com.kernel360.member.dto.MemberDto;
import com.kernel360.member.entity.Member;
import com.kernel360.member.repository.MemberRepository;
import com.kernel360.utils.ConvertSHA256;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("회원가입_테스트")
    void 회원가입_로직_테스트(){
        /** given **/
        MemberDto requestDto = MemberDto.of("testID", "gunsight777@naver.com", "testPassword");
        //Member member = Member.builder().id(memberDto.id()).password(memberDto.password()).email(memberDto.email()).build();
        Member member = new Member(requestDto.id(),requestDto.email(), requestDto.password());
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
        String convert = ConvertSHA256.convertToSHA256(original);

        /** then **/
        assertEquals(expect,convert);
    }

}