package org.example.mapper;

import org.example.document.ProductDoc;
import org.example.model.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.utils.TestUtils.getProductEntity;
import static org.example.utils.TestUtils.productSkuDocs;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class ProductMapperTest {
    @InjectMocks
    ProductMapperImpl mapper;

    private UUID productId;

    @BeforeEach
    public void setUp() {
        productId = UUID.randomUUID();
    }

    @Test
    @DisplayName("Test mapping product entity to product document")
    void mapProductEntityToProductDocTest() {
        ProductEntity productEntity = getProductEntity(productId);

        ProductDoc productDoc = mapper.mapProductEntityToProductDoc(productEntity);

        assertNotNull(productDoc);
        assertThat(productDoc).usingRecursiveComparison().isEqualTo(productEntity);
    }

    @Test
    @DisplayName("Test mapping product entities to product documents")
    void mapProductEntitiesToProductDocsTest() {
        List<ProductEntity> productEntities = List.of(getProductEntity(productId));

        List<ProductDoc> productDocs = mapper.mapProductEntitiesToProductDocs(productEntities);

        assertNotNull(productDocs);
        assertThat(productDocs).usingRecursiveComparison().isEqualTo(productEntities);
    }

    @Test
    @DisplayName("Test mapping product sku document to product document")
    void mapProductSkuDocToProductDocTest() {
        ProductDoc productDoc = mapper.mapProductSkuToProductDoc(productSkuDocs.get(0));

        assertNotNull(productDoc);
        assertThat(productDoc).usingRecursiveComparison().isEqualTo(productSkuDocs.get(0));
    }

    @Test
    @DisplayName("Test mapping product sku documents to product documents")
    void mapProductSkuDocsToProductDocsTest() {
        List<ProductDoc> productDocs = mapper.mapProductSkuDocsToProductDocs(productSkuDocs);

        assertNotNull(productDocs);
        assertThat(productDocs).usingRecursiveComparison().isEqualTo(productSkuDocs);
    }
}
