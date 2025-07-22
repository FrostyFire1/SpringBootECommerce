package com.frosty.SpringBootECommerce.repository;

import com.frosty.SpringBootECommerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {}
