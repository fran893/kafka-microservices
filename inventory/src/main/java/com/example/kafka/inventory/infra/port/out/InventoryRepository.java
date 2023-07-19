package com.example.kafka.inventory.infra.port.out;

import com.example.kafka.inventory.infra.adapter.out.persistance.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<InventoryEntity, Integer> {

    InventoryEntity findByProductId(int productId);

}
