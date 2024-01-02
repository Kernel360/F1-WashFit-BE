package com.kernel360.test.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
public class ApprovalApiDetailExplorer {
    public static void main(String[] args) throws IOException {

        RestClient restClient = RestClient.builder()
                                          .baseUrl("https://ecolife.me.go.kr")
                                          .messageConverters(convertors -> convertors.add(
                                                  new MappingJackson2XmlHttpMessageConverter()))
                                          .build();

        String result = restClient.get()
                                  .uri(buildUrl( "NNL_22421C0AC"))
                                  .retrieve()
                                  .body(String.class);
//** 엔티티로 매핑 **//
//        restClient.get()
//                  .uri(uriBuilder -> uriBuilder.path("/openapi/ServiceSvl")
//                                               .queryParam("AuthKey", "F985H62E9X1Q9X93Z81969N12L98SID5")
//                                               .queryParam("ServiceName", "slfsfcfst03Detail")
//                                               .queryParam("prdtNo", "NNL_22421C0AC")
//                                               .build())
//                  .retrieve()
//                  .toEntity();

        log.info("응답 내용: {}", result);

    }

    private static String buildUrl(String productNumber) {
        return UriComponentsBuilder.fromHttpUrl("https://ecolife.me.go.kr/openapi/ServiceSvl")
                                   .queryParam("AuthKey", "F985H62E9X1Q9X93Z81969N12L98SID5")
                                   .queryParam("ServiceName", "slfsfcfst03Detail")
                                   .queryParam("prdtNo", productNumber)
                                   .toUriString();
    }
}
