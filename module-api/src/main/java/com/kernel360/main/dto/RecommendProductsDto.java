package com.kernel360.main.dto;


import com.kernel360.product.entity.Product;

public record RecommendProductsDto(
        Long id,
        String image,
        String alt,
        String productName
) {
    public static RecommendProductsDto of(
            Long id,
            String image,
            String alt,
            String productName
    ) {
        return new RecommendProductsDto(
                id,
                image,
                alt,
                productName
        );
    }

    public static RecommendProductsDto from(Product entity) {

        return new RecommendProductsDto(
                entity.getProductNo(),
                "src/main/resources/static/suggestSample.png",
//                FixMe:: entity.getImage() 같은걸로 변경해야 함
                "제품 이미지",
                entity.getProductName());
    }

//    public static ProductDto from(RecommendProductsDto){
//
//        return new ProductDto(
//
//        )
}

//}


