package org.example.service;

import org.example.document.ProductDoc;
import org.example.document.ProductSkuDoc;
import org.example.document.SkuDoc;

import java.io.IOException;
import java.util.List;

public interface ElasticsearchService {
    void indexProductAndSku(List<ProductDoc> productDocs, List<SkuDoc> skus) throws IOException;
    void createIndex(String name) throws IOException;

    List<ProductSkuDoc> search(String index, String field, String value) throws IOException;
}
