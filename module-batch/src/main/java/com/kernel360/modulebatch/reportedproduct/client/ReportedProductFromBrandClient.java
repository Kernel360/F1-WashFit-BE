package com.kernel360.modulebatch.reportedproduct.client;

import com.kernel360.brand.entity.Brand;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
public class ReportedProductFromBrandClient{

    private static final String AUTH_KEY = System.getenv("API_AUTH_KEY");

    private final RestClient restClient;


    public ReportedProductFromBrandClient() {
        this.restClient = RestClient.builder()
                                    .build();
    }

    public String getXmlResponse(Brand brand, int pageNumber) {

        return restClient.post().uri(buildUri(brand, pageNumber))
                         .accept(MediaType.APPLICATION_XML)
                         .acceptCharset(StandardCharsets.UTF_8)
                         .retrieve()
                         .onStatus(HttpStatusCode::is4xxClientError,
                                 ((request, response) -> {
                                     log.debug("[ERROR] :: 4XX 에러 발생"
                                             + response.getStatusText());
                                     throw new RuntimeException(
                                             response.getStatusCode()
                                                     + response.getHeaders()
                                                               .toString());
                                 }))
                         .onStatus(HttpStatusCode::is5xxServerError,
                                 (request, response) -> {
                                     log.debug("[ERROR] :: 5XX 에러 발생"
                                             + response.getStatusText());
                                     throw new RuntimeException(
                                             response.getStatusCode()
                                                     + response.getHeaders()
                                                               .toString());
                                 })
                         .onStatus(HttpStatusCode::is2xxSuccessful,
                                 ((request, response) -> log.info("[INFO] :: api 요청 성공 ->"
                                         + response.getBody())))
                         .body(String.class);
    }


    public String buildUri(Brand brand, int pageNumber) {
        return UriComponentsBuilder.fromHttpUrl("https://ecolife.me.go.kr/openapi/ServiceSvl")
                                   .queryParam("AuthKey", AUTH_KEY)
                                   .queryParam("ServiceName", "slfsfcfst02List")
                                   .queryParam("PageCount", "20")
                                   .queryParam("PageNum", String.valueOf(pageNumber))
                                   .queryParam("compNm", brand.getCompanyName())
                                   .build()
                                   .toUriString();
    }
}
