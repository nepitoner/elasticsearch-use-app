package org.example.service;

import org.example.document.ProductDoc;
import org.example.document.SkuDoc;
import org.example.mapper.ProductMapper;
import org.example.mapper.SkuMapper;
import org.example.model.ProductEntity;
import org.example.model.SkuEntity;
import org.example.repository.ProductRepository;
import org.example.repository.SkuRepository;
import org.example.service.impl.DataLoadServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.example.utils.TestUtils.getProductEntity;
import static org.example.utils.TestUtils.getSkuEntity;
import static org.example.utils.TestUtils.productDocs;
import static org.example.utils.TestUtils.skuDocs;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DataLoadServiceTest {
    @Mock
    ProductRepository productRepository;

    @Mock
    SkuRepository skuRepository;

    @Mock
    ProductMapper productMapper;

    @Mock
    SkuMapper skuMapper;

    @InjectMocks
    DataLoadServiceImpl dataLoadService;

    private UUID productId;

    @BeforeEach
    void setUp() {
        productId = UUID.randomUUID();
    }

    @Test
    @DisplayName("Test loading data for active products")
    void loadDataActiveProductsTest() {
        boolean active = true;
        List<ProductEntity> activeProductEntities = List.of(getProductEntity(productId));
        when(productRepository.findByActive(active)).thenReturn(activeProductEntities);
        when(productMapper.mapProductEntitiesToProductDocs(activeProductEntities)).thenReturn(productDocs);

        List<ProductDoc> productDocList = dataLoadService.loadDataActiveProducts(active);
        System.out.println(productDocList);

        assertNotNull(productDocList);
        assertEquals(activeProductEntities.get(0).getProductName(), productDocList.get(0).getProductName());
        assertEquals(activeProductEntities.get(0).getCategory(), productDocList.get(0).getCategory());
        assertEquals(activeProductEntities.get(0).getDescription(), productDocList.get(0).getDescription());
        assertEquals(activeProductEntities.get(0).getPrice(), productDocList.get(0).getPrice());
    }

    @Test
    @DisplayName("Test loading data for available quantity")
    void loadDataSkuAvailableQuantityTest() {
        int availableQuantity = 100;
        List<SkuEntity> skuEntities = List.of(getSkuEntity(1, productId));
        when(skuRepository.findSkuEntitiesByAvailableQuantity(availableQuantity)).thenReturn(skuEntities);
        when(skuMapper.mapSkusEntitiesToSkuDocs(skuEntities)).thenReturn(skuDocs);

        List<SkuDoc> skuDocList = dataLoadService.loadDataSkuAvailableQuantity(availableQuantity);

        assertNotNull(skuDocList );
        assertEquals(availableQuantity, skuDocList.get(0).getAvailableQuantity());
    }
}
