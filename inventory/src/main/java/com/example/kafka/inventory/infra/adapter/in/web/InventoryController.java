package com.example.kafka.inventory.infra.adapter.in.web;

import com.example.kafka.inventory.infra.port.in.InventoryPort;
import com.kafka.example.events.domain.Inventory;
import com.kafka.example.events.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1")
public class InventoryController {

    private InventoryPort inventoryPort;

    @Autowired
    public InventoryController(InventoryPort inventoryPort) {
        this.inventoryPort = inventoryPort;
    }

    @GetMapping(value = "/inventories")
    public ResponseEntity<List<Inventory>> getInventories() {
        return new ResponseEntity<>(inventoryPort.getInventories(), HttpStatus.OK);
    }

    @GetMapping(value = "/inventories/{product-id}")
    public ResponseEntity<Inventory> getInventory(@PathVariable(name = "product-id") int productId){
        Inventory inventory = inventoryPort.getInventory(productId);
        if(inventory != null) {
            return new ResponseEntity<>(inventory, HttpStatus.OK);
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping(value = "/inventory")
    public ResponseEntity<Inventory> updateInventory(@RequestBody Order order) {
        Inventory updatedInventory = inventoryPort.updateInventory(order);

        return new ResponseEntity<>(updatedInventory, HttpStatus.OK);
    }

}
