package com.kernel360.member.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kernel360.member.dto.KakaoUserDto;
import com.kernel360.member.enumset.AgeForKakao;
import com.kernel360.member.enumset.GenderForKakao;
import com.kernel360.washzone.dto.KakaoMapDto;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class KakaoRequest {

    public KakaoUserDto getKakaoUserByToken(String accessToken) {

        String url = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer "+accessToken);
        headers.set("charset", "utf-8");

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

        System.err.println("response :: " + responseEntity);

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> kakaoResponse = new HashMap<>();

        try {
            kakaoResponse = mapper.readValue(responseEntity.getBody(), HashMap.class);
        }catch (Exception e){
            System.err.println("Exception : " + e);
        }
        Map<String, Object> kakaoAccount = mapper.convertValue(kakaoResponse.get("kakao_account"), HashMap.class);

        KakaoUserDto dto = KakaoUserDto.of(kakaoResponse.get("id").toString(),kakaoAccount.get("email").toString());

        return dto;
    }


}
