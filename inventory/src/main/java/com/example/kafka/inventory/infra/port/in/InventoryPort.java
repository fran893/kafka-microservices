package com.example.kafka.inventory.infra.port.in;

import com.kafka.example.events.domain.Inventory;
import com.kafka.example.events.domain.Order;

import java.util.List;

public interface InventoryPort {

    List<Inventory> getInventories();
    Inventory getInventory(int productId);
    Inventory updateInventory(Order order);

}
