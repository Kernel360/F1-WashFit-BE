package com.kernel360.modulebatch.product.job;

import com.kernel360.brand.entity.Brand;
import com.kernel360.ecolife.entity.ReportedProduct;
import com.kernel360.ecolife.repository.ReportedProductRepository;
import com.kernel360.modulebatch.product.dto.ProductDto;
import com.kernel360.product.entity.Product;
import com.kernel360.product.entity.SafetyStatus;
import com.kernel360.product.repository.ProductRepository;
import jakarta.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.ResourceAccessException;

@Slf4j
@Configuration
@RequiredArgsConstructor
@ComponentScan("com.kernel360.product")
public class ImportProductFromReportedProductJobConfig {

    private final ProductRepository productRepository;

    private final ReportedProductRepository reportedProductRepository;

    private final EntityManagerFactory emf;

    @Bean
    public Job importProductFromReportedProductJob(JobRepository jobRepository,
                                                   @Qualifier("importProductFromReportedProductStep") Step importProductFromReportedProductStep) {
        log.info("Import Product from ReportedProduct by Brand Job Build Configuration");

        return new JobBuilder("ImportProductFromReportedProductJob", jobRepository)
                .start(importProductFromReportedProductStep)
                .incrementer(new RunIdIncrementer())
                .listener(new ImportProductFromReportedProductListener())
                .build();
    }

    @Bean
    @JobScope
    public Step importProductFromReportedProductStep(JobRepository jobRepository,
                                                     PlatformTransactionManager transactionManager) throws Exception {
        log.info("Import Product from ReportedProduct by Brand Step Build Configuration");

        return new StepBuilder("ImportProductFromReportedProductStep", jobRepository)
                .<Brand, List<Product>>chunk(1, transactionManager)
                .reader(brandReader())
                .processor(reportedProductToProductListProcessor())
                .writer(productListWriter())
                .faultTolerant()
                .retryLimit(2)
                .retry(ResourceAccessException.class)
                .skipLimit(10)
                .skip(DataIntegrityViolationException.class)
                .build();
    }

    /**
     * @return Brand 를 읽어오는 JpaPagingReader
     */
    @Bean
    @StepScope
    public JpaPagingItemReader<Brand> brandReader() throws Exception {
        JpaPagingItemReader<Brand> itemReader = new JpaPagingItemReader<>();
        itemReader.setPageSize(50);
        itemReader.setEntityManagerFactory(emf);
        itemReader.setQueryString("select b from Brand b");
        itemReader.setName("jpaPagingBrandReader");
        itemReader.afterPropertiesSet();

        return itemReader;
    }

    @Bean
    @StepScope
    public ItemProcessor<Brand, List<Product>> reportedProductToProductListProcessor() throws Exception {
        return brand -> {
            //-- ReportedProduct 테이블에서 브랜드명과 제조사명으로 제품 검색 --//
            List<ReportedProduct> reportedProductList = reportedProductRepository
                    .findByBrandNameAndCompanyName(
                            "%" + brand.getCompanyName().replaceAll(" ", "%") + "%",
                            "%" + brand.getBrandName().replaceAll(" ", "%") + "%"
                    );
            List<ProductDto> productDtoList = reportedProductList.stream()
                                                                 .filter(rp -> rp.getInspectedOrganization() != null)
                                                                 .map(rp -> ProductDto.of(rp.getProductName(),
                                                                         null,
                                                                         null,
                                                                         "취하".equals(rp.getRenewType())
                                                                                 ? SafetyStatus.CONCERN
                                                                                 : SafetyStatus.SAFE,
                                                                         0,
                                                                         rp.getCompanyName(),
                                                                         rp.getSafetyReportNumber(),
                                                                         rp.getProductType(),
                                                                         LocalDate.from(rp.getIssuedDate()),
                                                                         rp.getSafeStandard(), rp.getUpperItem(),
                                                                         rp.getItem(),
                                                                         rp.getProductPropose(), rp.getWeightAndBulk(),
                                                                         rp.getUseMethod(),
                                                                         rp.getUsageAttentionReport(),
                                                                         rp.getFirstAid(), rp.getMainSubstance(),
                                                                         rp.getAllergicSubstance(),
                                                                         rp.getOtherSubstance(), rp.getPreservative(),
                                                                         rp.getSurfactant(),
                                                                         rp.getFluorescentWhiteningAgent(),
                                                                         rp.getManufacture(), rp.getManufactureMethod(),
                                                                         rp.getManufactureNation(), brand))
                                                                 .collect(Collectors.toList());

            return convertToProductList(productDtoList);
        };
    }


    /**
     * entity 로 변환할 dto 에 해당하는 제품이 있는지 검사하기 위해 product 테이블에서 제품을 검색한 이후, 없으면 새 제품 추가 있으면 업데이트
     */
    private List<Product> convertToProductList(List<ProductDto> productDtoList) {
        List<Product> productList = new ArrayList<>();

        for (ProductDto productDto : productDtoList) {
            Optional<Product> foundProduct = productRepository.findProductByProductNameAndReportNumber(
                    productDto.productName(), productDto.companyName());

            boolean foundInList = productList.stream().anyMatch(
                    product -> product.getProductName().equals(productDto.productName()) &&
                            product.getCompanyName().equals(productDto.companyName()));

            if (foundInList) { // 현재 처리할 데이터에 이미 추가가 되어 있다면
                continue;
            }

            if (foundProduct.isEmpty()) {
                Product newProduct = generateNewProduct(productDto);
                productList.add(newProduct);
                continue;
            }

            if (productDto.issuedDate().isAfter(foundProduct.get().getIssuedDate())) { // 저장된 데이터보다 최신의 제품 데이터라면
                foundProduct.get().updateDetail(productDto.barcode(),
                        productDto.imageSource(),
                        productDto.reportNumber(),
                        String.valueOf(productDto.safetyStatus()),
                        productDto.issuedDate(),
                        productDto.safetyInspectionStandard(),
                        productDto.upperItem(),
                        productDto.item(),
                        productDto.propose(),
                        productDto.weight(),
                        productDto.usage(),
                        productDto.usagePrecaution(),
                        productDto.firstAid(),
                        productDto.mainSubstance(),
                        productDto.allergicSubstance(),
                        productDto.otherSubstance(),
                        productDto.preservative(),
                        productDto.surfactant(),
                        productDto.fluorescentWhitening(),
                        productDto.manufactureType(),
                        productDto.manufactureMethod(),
                        getNation(productDto),
                        productDto.brand());
            }
        }

        return productList;
    }

    private static Product generateNewProduct(ProductDto productDto) {
        return Product.of(productDto.productName(), productDto.barcode(),
                productDto.imageSource(), productDto.reportNumber(), String.valueOf(productDto.safetyStatus()),
                productDto.viewCount(), getCompanyNameWithoutSlash(productDto.companyName()), productDto.productType(),
                productDto.issuedDate(), productDto.safetyInspectionStandard(), productDto.upperItem(),
                productDto.item(), productDto.propose(), productDto.weight(), productDto.usage(),
                productDto.usagePrecaution(), productDto.firstAid(), productDto.mainSubstance(),
                productDto.allergicSubstance(), productDto.otherSubstance(), productDto.preservative(),
                productDto.surfactant(), productDto.fluorescentWhitening(), productDto.manufactureType(),
                productDto.manufactureMethod(), getNation(productDto), productDto.brand());
    }

    public static String getCompanyNameWithoutSlash(String companyName) {
        int index = companyName.indexOf("/");
        if (index != -1) {
            return companyName.substring(0, index);
        }
        return companyName;

    }

    private static String getNation(ProductDto productDto) {
        String nation;
        if (productDto.manufactureType().equals("수입")) {
            nation = productDto.brand().getNationName();
        } else {
            nation = "대한민국";
        }
        return nation;
    }

    /**
     * List 를 저장하기 위한 JpaListWriter. 컴포지트 패턴과 유사하게 구현함.
     */
    private JpaProductListWriter<Product> productListWriter() {
        JpaItemWriter<Product> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(emf);

        return new JpaProductListWriter<>(writer);
    }

//-- Execution Listener --//

    public static class ImportProductFromReportedProductListener implements JobExecutionListener {
        @Override
        public void beforeJob(JobExecution jobExecution) {
            log.info("{} starts", jobExecution.getJobInstance().getJobName());
        }

        @Override
        public void afterJob(JobExecution jobExecution) {
            log.info("{} ends", jobExecution.getJobInstance().getJobName());
        }
    }

}

