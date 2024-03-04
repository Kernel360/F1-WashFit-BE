package com.kernel360.main.dto;


import com.kernel360.product.entity.Product;

public record RecommendProductsDto(
        Long productNo,
        String imageSource,
        String alt,
        String productName,
        String item
) {
    public static RecommendProductsDto of(
            Long productNo,
            String imageSource,
            String alt,
            String productName,
            String item
    ) {
        return new RecommendProductsDto(
                productNo,
                imageSource,
                alt,
                productName,
                item
        );
    }

    public static RecommendProductsDto from(Product entity) {

        return new RecommendProductsDto(
                entity.getProductNo(),
                "src/main/resources/static/suggestSample.png",
//                FixMe:: entity.getImage() 같은걸로 변경해야 함
                "제품 이미지",
                entity.getProductName(),
                entity.getItem()
        );
    }

//    public static ProductDto from(RecommendProductsDto){
//
//        return new ProductDto(
//
//        )
}

//}


