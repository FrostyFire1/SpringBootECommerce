package com.frosty.SpringBootECommerce.repository;

import com.frosty.SpringBootECommerce.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {}
