package com.kernel360.modulebatch.client;

import com.kernel360.ecolife.entity.ReportedProduct;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
public class ReportedProductDetailClient {

    private static final String AUTH_KEY = System.getenv("API_AUTH_KEY");

    private final RestClient restClient;

    public ReportedProductDetailClient() {
        this.restClient = RestClient.builder()
                                    .build();
    }


    public String getXmlResponse(ReportedProduct reportedProduct) {

        return restClient.get().uri(buildUrl(reportedProduct))
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
                                 ((request, response) -> log.info("[INFO] :: 2XX API 요청 성공"
                                         + response.getBody())))
                         .body(String.class);
    }

    public String buildUrl(ReportedProduct reportedProduct) {

        return UriComponentsBuilder.fromHttpUrl("https://ecolife.me.go.kr/openapi/ServiceSvl")
                                   .queryParam("AuthKey", AUTH_KEY)
                                   .queryParam("ServiceName", "slfsfcfst02Detail")
                                   .queryParam("mstId", reportedProduct.getId().getProductMasterId())
                                   .queryParam("estNo", reportedProduct.getId().getEstNumber())
                                   .toUriString();
    }

}
