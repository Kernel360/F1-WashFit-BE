package com.kernel360.modulebatch.violatedproduct.job.infra;

import com.kernel360.ecolife.entity.ViolatedProduct;
import com.kernel360.modulebatch.violatedproduct.client.ViolatedProductDetailClient;
import com.kernel360.modulebatch.violatedproduct.dto.ViolatedProductDetailDto;
import com.kernel360.modulebatch.violatedproduct.dto.ViolatedProductDto;
import com.kernel360.modulebatch.violatedproduct.service.ViolatedProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FetchViolatedProductDetailItemProcessor implements ItemProcessor<ViolatedProduct, ViolatedProduct> {
    private final ViolatedProductService service;
    private final ViolatedProductDetailClient client;

    @Override
    public ViolatedProduct process(ViolatedProduct item) throws Exception {
        if (item.getEtcInfo() != null) {
            return null;
        }
        String response = fetchXmlResponse(item);
        ViolatedProductDetailDto detailDto = service.deserializeXml2DetailDto(response).violatedProductDetailDtoList()
                                                    .get(0);

//        ViolatedProductDto productDto = ViolatedProductDto.of(item.getProductArm(), item.getActionedDate(),
//                item.getProductName(), item.getCompanyName(), item.getId().getProductArmCode(),
//                item.getProductArmCodeName(), item.getOriginInstitute(),
//                item.getId().getProductMasterNo(), item.getModelName()
//        );

        return ViolatedProductDetailDto.toEntity(detailDto);

    }


    private String fetchXmlResponse(ViolatedProduct violatedProduct) {
        log.info("Fetch item Id = {}", violatedProduct);
        String xmlResponse = client.getXmlResponse(violatedProduct.getId());
        log.info("Response accepted : {}", xmlResponse);

        return removeInvalidXmlCharacters(xmlResponse);
    }

    public String removeInvalidXmlCharacters(
            String xmlString) { // XML 1.0 Specification 을 준수하는 ASCII printable characters (REPLACEMENT_CHARACTER)
        String pattern = "[^\t\r\n -\uD7FF\uE000-\uFFFD\ud800\udc00-\udbff\udfff]";

        return xmlString.replaceAll(pattern, "");
    }
}
