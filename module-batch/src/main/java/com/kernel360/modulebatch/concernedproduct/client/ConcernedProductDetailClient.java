package com.kernel360.modulebatch.concernedproduct.client;

import com.kernel360.ecolife.entity.ConcernedProduct;
import jakarta.annotation.PostConstruct;
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
public class ConcernedProductDetailClient {

    @Value("${external.ecolife-api.path}")
    private String BASE_PATH;

    @Value("${external.ecolife-api.service-key}")
    private String AUTH_KEY;

    private final RestClient restClient;

    public ConcernedProductDetailClient() {
        this.restClient = RestClient.builder()
                                    .build();
        log.info("ConcernedProductDetailClient initialized with BASE_PATH: " + BASE_PATH);
    }
    @PostConstruct
    public void postConstruct() {
        log.info("ConcernedProductDetailClient postConstruct with BASE_PATH: " + BASE_PATH);
    }

    public String getXmlResponse(ConcernedProduct concernedProduct) {

        return restClient.get().uri(buildUri(concernedProduct))
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
                                 ((request, response) -> log.info("[INFO] :: 2XX API 요청 성공"
                                         + response.getBody())))
                         .body(String.class);
    }

    public String buildUri(ConcernedProduct concernedProduct) {

        return UriComponentsBuilder.fromHttpUrl(BASE_PATH)
                                   .queryParam("AuthKey", AUTH_KEY)
                                   .queryParam("ServiceName", "slfsfcfst01Detail")
                                   .queryParam("prdtNo", concernedProduct.getProductNo())
                                   .toUriString();
    }
}
