package com.frosty.SpringBootECommerce.repository;

import com.frosty.SpringBootECommerce.model.Cart;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser_Email(String userEmail);

    @Query("SELECT c FROM carts c JOIN FETCH c.cartItems ci JOIN FETCH ci.product p WHERE p.id = ?1")
    List<Cart> findByProduct_Id(Long productId);
}
