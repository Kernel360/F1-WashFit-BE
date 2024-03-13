package com.kernel360.member.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kernel360.exception.BusinessException;
import com.kernel360.member.code.MemberErrorCode;
import com.kernel360.member.dto.KakaoUserDto;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
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
        headers.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");


        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> kakaoResponse;

        try {
            kakaoResponse = mapper.readValue(responseEntity.getBody(), HashMap.class);
        }catch (Exception e){
            throw new BusinessException(MemberErrorCode.FAILED_REQUEST_LOGIN_FOR_KAKAO);
        }
        Map<String, Object> kakaoAccount = mapper.convertValue(kakaoResponse.get("kakao_account"), HashMap.class);

        return KakaoUserDto.of(kakaoResponse.get("id").toString(),kakaoAccount.get("email").toString());

    }


}
