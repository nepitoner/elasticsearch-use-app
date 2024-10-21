package org.example.utils;

import org.example.document.ProductDoc;
import org.example.document.ProductSkuDoc;
import org.example.document.SkuDoc;
import org.example.model.ProductEntity;
import org.example.model.SkuEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public final class TestUtils {
    private TestUtils() {
    }
    public static final List<ProductDoc> productDocs = List.of(ProductDoc.builder()
            .price(100)
            .category("Devices")
            .active(true)
            .productName("Wireless mouse")
            .build());

    public static final List<SkuDoc> skuDocs = List.of(SkuDoc.builder().availableQuantity(100).build());

    public static final List<ProductSkuDoc> productSkuDocs = List.of(ProductSkuDoc.builder()
            .active(true)
            .productName("Wireless mouse")
            .price(100)
            .category("Devices")
            .availableQuantity(100)
            .build());

    public static ProductEntity getProductEntity(UUID productId) {
        return ProductEntity.builder()
                .productId(productId)
                .active(true)
                .productName("Wireless mouse")
                .price(100)
                .category("Devices")
                .startDate(LocalDateTime.of(2024, 10, 19, 21, 23))
                .build();
    }

    public static SkuEntity getSkuEntity(int skuId, UUID productId) {
        return SkuEntity.builder()
                .skuId(skuId)
                .availableQuantity(100)
                .dateAdded(LocalDateTime.of(2024, 10, 19, 21, 23))
                .productEntity(ProductEntity.builder()
                        .productId(productId)
                        .active(true)
                        .productName("Wireless mouse")
                        .price(100)
                        .category("Devices")
                        .startDate(LocalDateTime.of(2024, 10, 19, 21, 23))
                        .build())
                .build();
    }
}
