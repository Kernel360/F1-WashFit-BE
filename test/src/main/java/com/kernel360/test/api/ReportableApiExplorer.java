package com.kernel360.test.api;

import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RequiredArgsConstructor
public class ReportableApiExplorer {

    public static void main(String[] args) {
        RestClient restClient = RestClient.builder().build();

        String result = restClient.get().uri(buildUrl())
                                  .accept(MediaType.APPLICATION_XML)
                                  .acceptCharset(StandardCharsets.UTF_8)
                                  .retrieve()
                                  .body(String.class);
        log.info("응답 : {}", result);

    }


    private static String buildUrl() {
        return UriComponentsBuilder.fromHttpUrl("https://ecolife.me.go.kr/openapi/ServiceSvl")
                                   .queryParam("AuthKey", "F985H62E9X1Q9X93Z81969N12L98SID5")
                                   .queryParam("ServiceName", "slfsfcfst02List")
                                   .queryParam("PageCount", "20")
                                   .queryParam("PageNum", "18560")
                                   .toUriString();
    }
}

