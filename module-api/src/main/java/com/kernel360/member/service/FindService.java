package com.kernel360.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindService implements EmailSender {

    private final JavaMailSender mailSender;

    public void sendMemberId(String email, String memberId) {
        new Thread(() -> mailSender.send(mimeMessage -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject("Wash-Fit 아이디/패스워드 찾기");
            String textContent = "가입하신 아이디는 " + "'" + memberId + "' 입니다.";
            mimeMessageHelper.setText(textContent);
        })).start();
    }
}
