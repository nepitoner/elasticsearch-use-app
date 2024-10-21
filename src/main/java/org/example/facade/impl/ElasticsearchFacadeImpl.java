package org.example.facade.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.document.ProductDoc;
import org.example.document.ProductSkuDoc;
import org.example.document.SkuDoc;
import org.example.facade.ElasticsearchFacade;
import org.example.mapper.ProductMapper;
import org.example.service.DataLoadService;
import org.example.service.ElasticsearchService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class ElasticsearchFacadeImpl implements ElasticsearchFacade {
    private final DataLoadService dataLoadService;

    private final ElasticsearchService elasticsearchService;

    private final ProductMapper productMapper;

    private boolean isIndexing = false;

    @Override
    public synchronized String indexProductsByActivityAndSkusByAvailableQuantity(boolean active, int availableQuantity) {
        if (isIndexing) {
            log.error("Data indexing is already in progress");
        }
        isIndexing = true;

        List<ProductDoc> productDocs = dataLoadService.loadDataActiveProducts(active);
        List<SkuDoc> skuDocs = dataLoadService.loadDataSkuAvailableQuantity(availableQuantity);

        try {
            elasticsearchService.indexProductAndSku(productDocs, skuDocs);
            return "Successfully indexed";
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            isIndexing = false;
        }
    }

    @Override
    public List<ProductDoc> searchForProductsByParameters(String field, String value) throws IOException {
        List<ProductSkuDoc> productSkuDocs = elasticsearchService.search("products_skus_activity_quantity", field, value);
        return productMapper.mapProductSkuDocsToProductDocs(productSkuDocs);
    }
}
