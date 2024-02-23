package com.kernel360.member.service;

public interface EmailSender {
    void sendMail(String to, String subject, String content);
}
