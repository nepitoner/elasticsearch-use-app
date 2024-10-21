package org.example.mapper;

import org.example.document.ProductDoc;
import org.example.document.ProductSkuDoc;
import org.example.model.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {
    ProductDoc mapProductEntityToProductDoc(ProductEntity productEntity);

    List<ProductDoc> mapProductEntitiesToProductDocs(List<ProductEntity> productEntities);

    ProductDoc mapProductSkuToProductDoc(ProductSkuDoc productSkuDoc);

    List<ProductDoc> mapProductSkuDocsToProductDocs(List<ProductSkuDoc> productSkuDoc);
}
