package com.example.kafka.inventory.application;

import com.example.kafka.inventory.infra.adapter.out.persistance.IMapper;
import com.example.kafka.inventory.infra.adapter.out.persistance.InventoryEntity;
import com.example.kafka.inventory.infra.port.in.InventoryPort;
import com.example.kafka.inventory.infra.port.out.InventoryRepository;
import com.kafka.example.events.domain.Inventory;
import com.kafka.example.events.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class InventoryService implements InventoryPort {

    private InventoryRepository inventoryRepository;
    private IMapper<Inventory, InventoryEntity> mapper;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository, IMapper<Inventory, InventoryEntity> mapper) {
        this.inventoryRepository = inventoryRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Inventory> getInventories() {
        List<Inventory> inventories = new ArrayList<>();

        inventoryRepository.findAll().forEach(inventoryEntity -> inventories.add(mapper.entityToDomain(inventoryEntity)));

        return inventories;
    }

    @Override
    public Inventory getInventory(int productId) {
        return mapper.entityToDomain(inventoryRepository.findByProductId(productId));
    }

    @Override
    public Inventory updateInventory(Order order) {
        log.info("Updating inventory for productId: {}", order.getProductId());
        Inventory inventory = getInventory(order.getProductId());

        //hardcoded: At the moment the orders only contain one quantity per product, for testing purpose
        inventory.updateInventory(1);

        inventoryRepository.save(mapper.domainToEntity(inventory));

        return inventory;
    }
}
