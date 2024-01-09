package com.kernel360.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConvertSHA256 {

    public static String convertToSHA256(String word) {
        return DigestUtils.sha256Hex(word);
    }
}

