package com.kernel360.utils;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class ConvertSHA256 {
    public static String convertToSHA256(String word) {
        String encodePassword = "";
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(word.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : encodedHash) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            encodePassword = hexString.toString();
        } catch (NoSuchAlgorithmException algorithm) {
            log.error("JoinMember :: not found algorithm by " + algorithm.getStackTrace());
        }

        return encodePassword;
    }
}
