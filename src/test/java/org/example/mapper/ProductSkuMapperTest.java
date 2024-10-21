package org.example.mapper;

import org.example.document.ProductSkuDoc;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.utils.TestUtils.productDocs;
import static org.example.utils.TestUtils.productSkuDocs;
import static org.example.utils.TestUtils.skuDocs;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class ProductSkuMapperTest {
    @InjectMocks
    ProductSkuMapperImpl mapper;

    @Test
    @DisplayName("Test mapping product and sku to product sku document")
    void mapProductAndSkuToProductSkuTest() {
        ProductSkuDoc productSkuDoc = mapper.
                mapProductAndSkuToProductSku(productDocs.get(0), skuDocs.get(0));

        assertNotNull(productSkuDoc);
        assertThat(productSkuDoc).usingRecursiveComparison().isEqualTo(productSkuDocs.get(0));
    }
}
