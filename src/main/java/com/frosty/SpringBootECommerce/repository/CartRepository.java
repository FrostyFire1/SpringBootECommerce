package com.frosty.SpringBootECommerce.repository;

import com.frosty.SpringBootECommerce.model.Cart;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser_Email(String userEmail);
}
