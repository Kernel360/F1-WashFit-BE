package com.kernel360.utils;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

@Slf4j
@NoArgsConstructor
public class ConvertSHA256 {

    public static String convertToSHA256(String word) {

        return DigestUtils.sha256Hex(word);
    }

}

