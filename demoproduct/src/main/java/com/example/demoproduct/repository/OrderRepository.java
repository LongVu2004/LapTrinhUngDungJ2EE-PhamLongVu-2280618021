package com.example.demoproduct.repository;

import com.example.demoproduct.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
