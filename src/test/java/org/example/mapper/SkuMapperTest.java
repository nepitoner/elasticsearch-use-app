package org.example.mapper;

import org.example.document.SkuDoc;
import org.example.model.SkuEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.example.utils.TestUtils.getSkuEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class SkuMapperTest {
    @InjectMocks
    SkuMapperImpl mapper;

    private int skuId;

    private UUID productId;

    @BeforeEach
    public void setUp() {
        skuId = 1;
        productId = UUID.randomUUID();
    }

    @Test
    @DisplayName("Test mapping sku entity to sku document")
    void mapSkuEntityToSkuDocTest() {
        SkuEntity skuEntity = getSkuEntity(skuId, productId);

        SkuDoc skuDoc = mapper.mapSkuEntityToSkuDoc(skuEntity);

        assertNotNull(skuDoc);
        assertEquals(skuDoc.getSkuId(), skuEntity.getSkuId());
        assertEquals(skuDoc.getAvailableQuantity(), skuEntity.getAvailableQuantity());
        assertEquals(skuDoc.getProductId(), skuEntity.getProductEntity().getProductId());
    }

    @Test
    @DisplayName("Test mapping sku entities to sku documents")
    void mapSkuEntitiesToSkuDocsTest() {
        List<SkuEntity> skuEntities = List.of(getSkuEntity(skuId, productId));

        List<SkuDoc> skuDocs = mapper.mapSkusEntitiesToSkuDocs(skuEntities);

        assertNotNull(skuDocs);
        assertEquals(skuDocs.get(0).getSkuId(), skuEntities.get(0).getSkuId());
        assertEquals(skuDocs.get(0).getAvailableQuantity(), skuEntities.get(0).getAvailableQuantity());
        assertEquals(skuDocs.get(0).getProductId(), skuEntities.get(0).getProductEntity().getProductId());
    }
}
