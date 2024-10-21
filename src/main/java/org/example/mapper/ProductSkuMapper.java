package org.example.mapper;

import org.example.document.ProductDoc;
import org.example.document.ProductSkuDoc;
import org.example.document.SkuDoc;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductSkuMapper {
    @Mapping(source = "productDoc.productId", target = "productId")
    ProductSkuDoc mapProductAndSkuToProductSku(ProductDoc productDoc, SkuDoc skuDoc);
}
