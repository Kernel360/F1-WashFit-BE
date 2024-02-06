//package com.kernel360.utils.jasypt;
//
//import org.jasypt.encryption.StringEncryptor;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class JasyptEncryptor implements CommandLineRunner {
//    private final StringEncryptor stringEncryptor;
//
//    public JasyptEncryptor(@Qualifier("washpediaEncryptorBean") StringEncryptor stringEncryptor) {
//        this.stringEncryptor = stringEncryptor;
//    }
//
//    public static void main(String[] args) {
//        SpringApplication.run(JasyptEncryptor.class, args);
//    }
//
//    @Override
//    public void run(String... args) {
//        String url = "암호화 할 문자열을 입력하세요";
//        String encryptedUrl = stringEncryptor.encrypt(url);
//
//        System.out.println("[Original] : " + url);
//        System.out.println("[Encrypted] : " + "ENC(" + encryptedUrl + ")");
//        System.out.println("[Decrypted] : " + stringEncryptor.decrypt(encryptedUrl));
//    }
//}