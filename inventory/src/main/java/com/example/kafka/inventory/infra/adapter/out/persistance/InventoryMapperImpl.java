package com.example.kafka.inventory.infra.adapter.out.persistance;

import com.kafka.example.events.domain.Inventory;
import org.springframework.stereotype.Component;

@Component
public class InventoryMapperImpl implements IMapper<Inventory, InventoryEntity> {

    @Override
    public Inventory entityToDomain(InventoryEntity inventoryEntity) {
        Inventory inventory = new Inventory();

        inventory.setId(inventoryEntity.getId());
        inventory.setQuantity(inventoryEntity.getQuantity());
        inventory.setProductId(inventoryEntity.getProductId());

        return inventory;
    }

    @Override
    public InventoryEntity domainToEntity(Inventory inventory) {
        InventoryEntity inventoryEntity = new InventoryEntity();

        inventoryEntity.setId(inventory.getId());
        inventoryEntity.setQuantity(inventory.getQuantity());
        inventoryEntity.setProductId(inventory.getProductId());

        return inventoryEntity;
    }
}
