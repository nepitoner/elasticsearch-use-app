package org.example.facade;

import org.example.document.ProductDoc;

import java.io.IOException;
import java.util.List;

public interface ElasticsearchFacade {
    String indexProductsByActivityAndSkusByAvailableQuantity(boolean active, int availableQuantity);

    List<ProductDoc> searchForProductsByParameters(String field, String value) throws IOException;

}
