package org.example.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.indices.ExistsRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.document.ProductDoc;
import org.example.document.ProductSkuDoc;
import org.example.document.SkuDoc;
import org.example.mapper.ProductSkuMapper;
import org.example.service.ElasticsearchService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ElasticsearchServiceImpl implements ElasticsearchService {
    private final ProductSkuMapper mapper;

    private final ElasticsearchClient elasticsearchClient;
    
    @Override
    public void indexProductAndSku(List<ProductDoc> productDocs, List<SkuDoc> skuDocs) throws IOException {
        List<ProductSkuDoc> productSkuDocs = createProductSkuList(productDocs, skuDocs);

        String indexName = "products_skus_activity_quantity";

        if (!elasticsearchClient.indices().exists(ExistsRequest.of(e -> e.index(indexName))).value()) {
            createIndex(indexName);
        }

        BulkRequest.Builder br = new BulkRequest.Builder();

        for (ProductSkuDoc productSkuDoc : productSkuDocs) {
            br.operations(
                op -> op.index(
                    idx -> idx.index(indexName).id(productSkuDoc.getProductName())
                            .document(productSkuDoc)
                )
            );
        }

        BulkResponse response = elasticsearchClient.bulk(br.build());
        logErrors(response);
    }

    @Override
    public void createIndex(String name) throws IOException {
        elasticsearchClient.indices().create(
                c -> c.index(name)
        );
        log.info("Index {} created", name);
    }

    @Override
    public List<ProductSkuDoc> search(String index, String field, String value) throws IOException {
        SearchResponse<ProductSkuDoc> response = elasticsearchClient.search(
                s -> s.index(index).query(
                        q -> q.match(
                                t -> t.field(field).query(value)
                        )
                ),
                ProductSkuDoc.class
        );
        List<ProductSkuDoc> productSkuDocs = new ArrayList<>();

        for (Hit<ProductSkuDoc> hit : response.hits().hits()) {
            productSkuDocs.add(hit.source());
            assert hit.source() != null;
            log.info("Found product {}, score {}", hit.source().getProductId(), hit.score());
        }
        return productSkuDocs;
    }

    private void logErrors(BulkResponse response) {
        if (response.errors()) {
            log.error("Errors during indexing");
            for (BulkResponseItem item : response.items()) {
                if (item.error() != null) {
                    log.error(item.error().reason());
                }
            }
        }
    }

    private List<ProductSkuDoc> createProductSkuList(List<ProductDoc> productDocs, List<SkuDoc> skuDocs) {
        List<ProductSkuDoc> productSkusDocs = new ArrayList<>();
        for(ProductDoc productDoc : productDocs) {
            for (SkuDoc skuDoc : skuDocs) {
                if (skuDoc.getProductId() == productDoc.getProductId()) {
                    productSkusDocs.add(mapper.mapProductAndSkuToProductSku(productDoc, skuDoc));
                }
            }
        }
        return productSkusDocs;
    }
}
