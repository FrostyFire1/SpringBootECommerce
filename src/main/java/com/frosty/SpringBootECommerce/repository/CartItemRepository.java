package com.frosty.SpringBootECommerce.repository;

import com.frosty.SpringBootECommerce.model.CartItem;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByProduct_Id_AndCart_Id(Long productId, Long cartId);

    @Modifying
    @Query("DELETE FROM cart_items ci WHERE ci.cart.id = ?1 and ci.product.id = ?2")
    void deleteByCart_IdAndProduct_Id(Long cartId, Long productId);
}
