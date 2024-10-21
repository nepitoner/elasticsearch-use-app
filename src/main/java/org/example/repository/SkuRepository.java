package org.example.repository;

import org.example.model.SkuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkuRepository extends JpaRepository<SkuEntity, Integer> {
    List<SkuEntity> findSkuEntitiesByAvailableQuantity(int availableQuantity);
}
