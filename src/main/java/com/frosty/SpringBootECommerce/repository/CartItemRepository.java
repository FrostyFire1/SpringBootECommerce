package com.frosty.SpringBootECommerce.repository;

import com.frosty.SpringBootECommerce.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByProduct_Id_AndCart_Id(Long productId, Long cartId);
}
