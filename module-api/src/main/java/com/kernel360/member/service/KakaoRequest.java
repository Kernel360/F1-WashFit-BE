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

    public String getKakaoTokenByAuthcode(String authorizeCode) {

        String url = "https://kauth.kakao.com/oauth/token";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Accept", "application/json");
        headers.set("charset", "utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "416fb028180bc7dfd9412cfd7be160bc");
        params.add("redirect_uri", "http://10.230.120.21:8080");
        params.add("client_secret", "c5F0SbaddaJVUsj5e6nmNd7ExnuZO5mN");
        params.add("code", authorizeCode);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, request, String.class);

        System.err.println(" access_token >>>>>>>>> " + responseEntity.getBody());

        return responseEntity.getBody();
    }


    public KakaoUserDto getKakaoUserByToken(String accessToken) {

        String url = "https://kapi.kakao.com/v2/user/me";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer "+accessToken);
        headers.set("charset", "utf-8");

        RestTemplate restTemplate = new RestTemplate();

        System.err.println("URL :: " + url);
        System.err.println("headers :: " + headers);

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> kakaoResponse = new HashMap<>();

        try {
            kakaoResponse = mapper.readValue(responseEntity.getBody(), HashMap.class);
        }catch (Exception e){
            System.err.println("Exception : " + e);
        }
        Map<String, Object> kakaoAccount = mapper.convertValue(kakaoResponse.get("kakao_account"), HashMap.class);

        KakaoUserDto dto = KakaoUserDto.of(
                                            kakaoResponse.get("id").toString(),
                                            kakaoAccount.get("email").toString(),
                                            AgeForKakao.valueOf(kakaoAccount.get("age_range").toString()).ordinal(),
                                            GenderForKakao.valueOf(kakaoAccount.get("gender").toString()).ordinal()
                                            );

        System.out.println("Response Body : " + dto);

        return dto;
    }


}
