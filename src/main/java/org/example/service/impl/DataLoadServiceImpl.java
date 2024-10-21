package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.document.ProductDoc;
import org.example.document.SkuDoc;
import org.example.mapper.ProductMapper;
import org.example.mapper.SkuMapper;
import org.example.model.ProductEntity;
import org.example.model.SkuEntity;
import org.example.repository.ProductRepository;
import org.example.repository.SkuRepository;
import org.example.service.DataLoadService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataLoadServiceImpl implements DataLoadService {
    private final ProductRepository productRepository;

    private final SkuRepository skuRepository;

    private final ProductMapper productMapper;

    private final SkuMapper skuMapper;

    @Override
    public List<ProductDoc> loadDataActiveProducts(boolean active) {
        List<ProductEntity> activeProductEntities = productRepository.findByActive(active);
        return productMapper.mapProductEntitiesToProductDocs(activeProductEntities);
    }

    @Override
    public List<SkuDoc> loadDataSkuAvailableQuantity(int availableQuantity) {
        List<SkuEntity> skusQuantity = skuRepository.findSkuEntitiesByAvailableQuantity(availableQuantity);
        return skuMapper.mapSkusEntitiesToSkuDocs(skusQuantity);
    }
}
