package com.kernel360.modulebatch.violatedproduct.client;

import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
public class ViolatedProductListClient {

    @Value("${external.ecolife-api.path}")
    private String BASE_PATH;

    @Value("${external.ecolife-api.service-key}")
    private String AUTH_KEY;

    private final RestClient restClient;

    public ViolatedProductListClient() {
        this.restClient = RestClient.builder()
                                    .build();
    }

    public String getXmlResponse(String productArmCode, Integer pageNumber) {

        return restClient.post().uri(buildUri(productArmCode, pageNumber))
                         .accept(MediaType.APPLICATION_XML)
                         .acceptCharset(StandardCharsets.UTF_8)
                         .retrieve()
                         .onStatus(HttpStatusCode::is4xxClientError,
                                 ((request, response) -> {
                                     log.error("[ERROR] :: 4XX 에러 발생"
                                             + response.getStatusText());
                                     throw new RuntimeException(
                                             response.getStatusCode()
                                                     + response.getHeaders()
                                                               .toString());
                                 }))
                         .onStatus(HttpStatusCode::is5xxServerError,
                                 (request, response) -> {
                                     log.error("[ERROR] :: 5XX 에러 발생"
                                             + response.getStatusText());
                                     throw new RuntimeException(
                                             response.getStatusCode()
                                                     + response.getHeaders()
                                                               .toString());
                                 })
                         .onStatus(HttpStatusCode::is2xxSuccessful,
                                 ((request, response) -> log.info("[INFO] :: api 요청 성공"
                                         + response.getBody())))
                         .body(String.class);
    }

    public String buildUri(String productArmCode, Integer pageNumber) {

        return UriComponentsBuilder.fromHttpUrl(BASE_PATH)
                                   .queryParam("AuthKey", AUTH_KEY)
                                   .queryParam("ServiceName", "violtProductList")
                                   .queryParam("prdtarmCd", productArmCode)
                                   .queryParam("PageCount", "20")
                                   .queryParam("PageNum", String.valueOf(pageNumber))
                                   .build()
                                   .toUriString();
    }

}
