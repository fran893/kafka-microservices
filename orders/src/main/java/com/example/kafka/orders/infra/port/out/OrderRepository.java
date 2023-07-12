package com.example.kafka.orders.infra.port.out;

import com.example.kafka.orders.infra.adapter.out.persistance.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
