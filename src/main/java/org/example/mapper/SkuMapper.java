package org.example.mapper;

import org.example.document.SkuDoc;
import org.example.model.SkuEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SkuMapper {
    @Mapping(target = "productId", source = "skuEntity.productEntity.productId")
    SkuDoc mapSkuEntityToSkuDoc(SkuEntity skuEntity);

    List<SkuDoc> mapSkusEntitiesToSkuDocs(List<SkuEntity> skuEntities);
}
