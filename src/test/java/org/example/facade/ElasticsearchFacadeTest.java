package org.example.facade;

import org.example.document.ProductDoc;
import org.example.facade.impl.ElasticsearchFacadeImpl;
import org.example.mapper.ProductMapper;
import org.example.service.DataLoadService;
import org.example.service.ElasticsearchService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.example.utils.TestUtils.productDocs;
import static org.example.utils.TestUtils.productSkuDocs;
import static org.example.utils.TestUtils.skuDocs;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ElasticsearchFacadeTest {
    @Mock
    DataLoadService dataLoadService;

    @Mock
    ElasticsearchService elasticsearchService;

    @Mock
    ProductMapper productMapper;

    @InjectMocks
    ElasticsearchFacadeImpl facade;

    @Test
    @DisplayName("Test indexing products and skus by activity and available quantity")
    void indexProductsByActivityAndSkusByAvailableQuantityTest() throws IOException {
        boolean active = true;
        int availableQuantity = 100;
        when(dataLoadService.loadDataActiveProducts(active)).thenReturn(productDocs);
        when(dataLoadService.loadDataSkuAvailableQuantity(availableQuantity)).thenReturn(skuDocs);
        doNothing().when(elasticsearchService).indexProductAndSku(productDocs, skuDocs);

        String result = facade.indexProductsByActivityAndSkusByAvailableQuantity(active, availableQuantity);

        Assertions.assertEquals(result, "Successfully indexed");
        verify(dataLoadService, times(1)).loadDataActiveProducts(active);
        verify(dataLoadService, times(1)).loadDataSkuAvailableQuantity(availableQuantity);
        verify(elasticsearchService, times(1)).indexProductAndSku(productDocs, skuDocs);
    }

    @Test
    @DisplayName("Test searching for products by available quantity")
    void searchForProductsByParametersTest() throws IOException {
        String field = "availableQuantity";
        String value = "100";
        String indexName = "products_skus_activity_quantity";
        when(elasticsearchService.search(indexName, field, value))
                .thenReturn(productSkuDocs);
        when(productMapper.mapProductSkuDocsToProductDocs(productSkuDocs)).thenReturn(productDocs);

        List<ProductDoc> result = facade.searchForProductsByParameters(field, value);

        Assertions.assertEquals(result, productDocs);
        verify(elasticsearchService, times(1)).search(indexName, field, value);
    }
}
