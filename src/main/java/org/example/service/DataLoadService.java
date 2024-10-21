package org.example.service;

import org.example.document.ProductDoc;
import org.example.document.SkuDoc;

import java.time.LocalDateTime;
import java.util.List;

public interface DataLoadService {
    List<ProductDoc> loadDataActiveProducts(boolean active);

    List<SkuDoc> loadDataSkuAvailableQuantity(int availableQuantity);
}
