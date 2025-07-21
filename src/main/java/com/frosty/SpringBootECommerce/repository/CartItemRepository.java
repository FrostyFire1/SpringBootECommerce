package com.frosty.SpringBootECommerce.repository;

import com.frosty.SpringBootECommerce.model.CartItem;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
  Optional<CartItem> findByProduct_Id_AndCart_Id(Long productId, Long cartId);
}
