package com.kernel360.modulebatch.reportedproduct.client;

import com.kernel360.ecolife.entity.ReportedProduct;
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
public class ReportedProductDetailClient implements ApiClient<ReportedProduct> {

    @Value("${external.ecolife-api.path}")
    private String BASE_PATH;

    @Value("${external.ecolife-api.service-key}")
    private String AUTH_KEY;

    private final RestClient restClient;

    public ReportedProductDetailClient() {
        this.restClient = RestClient.builder()
                                    .build();
    }

    @Override
    public String getXmlResponse(ReportedProduct reportedProduct) {

        return restClient.get().uri(buildUri(reportedProduct))
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
                                 ((request, response) -> log.debug("[INFO] :: 2XX API 요청 성공"
                                         + response.getBody())))
                         .body(String.class);
    }

    @Override
    public String buildUri(ReportedProduct reportedProduct) {

        return UriComponentsBuilder.fromHttpUrl(BASE_PATH)
                                   .queryParam("AuthKey", AUTH_KEY)
                                   .queryParam("ServiceName", "slfsfcfst02Detail")
                                   .queryParam("mstId", reportedProduct.getId().getProductMasterId())
                                   .queryParam("estNo", reportedProduct.getId().getEstNumber())
                                   .toUriString();
    }

}
